/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cimav.client.personal;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import org.cimav.client.domain.Empleado;
import org.cimav.client.tools.ProviderEvent;
import org.cimav.client.tools.ProviderMethod;
import org.cimav.client.tools.TypeResult;
import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.TextBox;
import org.gwtbootstrap3.client.ui.constants.IconFlip;
import org.gwtbootstrap3.client.ui.constants.Styles;
import org.gwtbootstrap3.extras.growl.client.ui.Growl;
import org.gwtbootstrap3.extras.growl.client.ui.GrowlHelper;
import org.gwtbootstrap3.extras.growl.client.ui.GrowlOptions;

/**
 *
 * @author juan.calderon
 */
public class PersonalUI extends Composite {
    
    //private PersonalProvider personalProvider;
    
    private static final PersonalUIUiBinder uiBinder = GWT.create(PersonalUIUiBinder.class);
    
    interface PersonalUIUiBinder extends UiBinder<Widget, PersonalUI> {
    }
    
    @UiField public ScrollPanel scrollPanel;
    CellList<Empleado> cellList;
    
    @UiField TextBox searchTxt;
    @UiField Button reloadBtn;
    
    //@UiField PersonalEditorUI personalEditorUI;
    
    private final SingleSelectionModel<Empleado> selectionModel;
    
    public PersonalUI() {
        initWidget(uiBinder.createAndBindUi(this));
        
        //CellList.Resources cellListResources = GWT.create(CellList.Resources.class);
        CellList.Resources cellListResources = GWT.create(ICellListResources.class);
        selectionModel = new SingleSelectionModel<>();
        cellList = new CellList<>(new EmpleadoCell(selectionModel), cellListResources, PersonalProvider.get().getDataProvider());
        cellList.setKeyboardSelectionPolicy(HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.ENABLED);
        cellList.setSelectionModel(selectionModel);
        selectionModel.addSelectionChangeHandler(new SelectionHandler());
        cellList.setPageSize(600);  // máximo son 400 empleados. Al mostrarlos todos, no se requiere Pager.
        scrollPanel.add(cellList);
        
//                SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
//        pager = new SimplePager(SimplePager.TextLocation.CENTER, pagerResources, false, 0, true);
//        pager.setDisplay(dataGrid);

        
        /* Inyectarle style absolute al Abuelo para que funcione el scroll del cellList */
        Element divAbue = cellList.getElement().getParentElement().getParentElement();
        divAbue.getStyle().setPosition(Style.Position.ABSOLUTE);
        divAbue.getStyle().setTop(0, Style.Unit.PX);
        divAbue.getStyle().setLeft(0, Style.Unit.PX);
        divAbue.getStyle().setBottom(0, Style.Unit.PX);
        divAbue.getStyle().setRight(0, Style.Unit.PX);
        
        // Add the CellList to the adapter in the database.
        PersonalProvider.get().addDataDisplay(cellList);
        
       PersonalProvider.get().addMethodExecutedListener(new ProviderMethodExecutedListener());
        
        reloadBtn.setIconFlip(IconFlip.HORIZONTAL);
        reloadBtn.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                reloadAll();
            }
        });
        
        searchTxt.addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
                final String txtToSearch = searchTxt.getText();
                PersonalProvider.get().getDataProvider().setFilter(txtToSearch);
                String rows =PersonalProvider.get().getRowCountPropotional();
                reloadBtn.setText(rows);
            }
        });
        
        // orden inicial
        orderBy = PersonalProvider.ORDER_BY_NAME;
        
        /* Al arrancar, cargar a todos los empleados */
       // reloadAll();
    }

    private int orderBy;
    @UiHandler({"btnByName", "btnByCode", "btnByGrupo", "btnByNivel"})
    void handleClick(ClickEvent e) {
        orderBy = -1;
        if (e.getSource().toString().contains("Nombre")) {
            orderBy = PersonalProvider.ORDER_BY_NAME;
        } else if (e.getSource().toString().contains("Num")) {
            orderBy = PersonalProvider.ORDER_BY_CODE;
        } else if (e.getSource().toString().contains("Grp")) {
            orderBy = PersonalProvider.ORDER_BY_GRUPO;
        } else if (e.getSource().toString().contains("Niv")) {
            orderBy = PersonalProvider.ORDER_BY_NIVEL;
            
            //selectionModel.setSelected(new Empleado(), true);
        }
        this.orderBy();
    }
    
//    private PersonalProvider getProvider() {
//        if (personalProvider == null) {
//            personalProvider = new PersonalProvider();
//            personalProvider.addMethodExecutedListener(new ProviderMethodExecutedListener());
//        }
//        return personalProvider;
//    }
    
    private void orderBy() {
        PersonalProvider.get().order(this.orderBy);
    }
    
    private void reloadAll() {
        PersonalProvider.get().findAll();
        this.orderBy();
    }
    
    private class ProviderMethodExecutedListener implements PersonalProvider.MethodExecutedListener {

        @Override
        public void onMethodExecuted(ProviderEvent dbEvent) {
            if (ProviderMethod.FIND_ALL.equals(dbEvent.getDbMethod())) {
                String m = "" + PersonalProvider.get().getDataProvider().getDataDisplays().size() + "/" + PersonalProvider.get().getDataProvider().getList().size();
                reloadBtn.setText(m);

                if (TypeResult.SUCCESS.equals(dbEvent.getDbTypeResult())) {
                    String msg = "Registros: " + PersonalProvider.get().getDataProvider().getList().size();
                    GrowlOptions go = GrowlHelper.getNewOptions();
                    go.setSuccessType();
                    go.setAllowDismiss(false);
                    Growl.growl("", msg, Styles.FADE + " " + Styles.FONT_AWESOME_BASE /*+ " " + IconType.SMILE_O.getCssName()*/, go);

                } else {
                    String msg = "Falló la carga de registros";
                    GrowlOptions go = GrowlHelper.getNewOptions();
                    go.setDangerType();
                    go.setDelay(15000); // 15 segs
                    //go.setAllowDismiss(false);
                    Growl.growl("", msg, Styles.FONT_AWESOME_BASE /*+ " " + IconType.SMILE_O.getCssName()*/, go);
                }
            }
        }
    }
    
    private class EmpleadoCell extends AbstractCell<Empleado> {
        
        private SingleSelectionModel<Empleado> selectionModel;
        
        EmpleadoCell(SingleSelectionModel<Empleado> docSelectionModel) {
            this.selectionModel = docSelectionModel;
        }
        
        @Override
        public void render(Cell.Context context, Empleado value, SafeHtmlBuilder sb) {
            if (value == null) {
                return;
            }
            
            boolean isSelected = this.selectionModel != null && this.selectionModel.getSelectedObject() != null
                    && this.selectionModel.getSelectedObject().equals(value);

            // TODO reemplazar código a pie por PersonalItem
            
            String grupoStr = value.getGrupo() != null ? value.getGrupo().getCode() : "SIN_GRP";
            String deptoStr = value.getDepartamento() != null ? value.getDepartamento().getCodigo() : "SIN_DEPTO";
            String nivelStr = value.getNivel()!= null ? value.getNivel().getCode() : "SIN_NIVEL";

            String html =
                    "<table width='100%' cellspacing='0' cellpadding='0' style='cursor: pointer; text-align: left; vertical-align: middle; border-bottom:1px solid lightgray;'>\n" +
                    "  <tr>\n" +
                    "    <td width='4px' rowspan='6' style='height:auto; width: 5px; SELECTED_COLOR_REEMPLAZO'></td>\n" +
                    "    <td colspan='3' style='height:3px;'></td>\n" +
                    "  </tr>\n" +
                    "  <tr>\n" +
                    "    <td width='78px' rowspan='3' style='text-align: center;'><img src='URL_FOTO_REEMPLAZO' style='border:1px solid lightgray; margin-top: 3px; border-radius:50%; padding:2px;'></td>\n" +
                    "    <td colspan='2' style='vertical-align: bottom;'><h4 style='margin-top: 0px; margin-bottom: 0px;'>APELLIDOS_REEMPLAZO,</h4></td>\n" +
                    "  </tr>\n" +
                    "  <tr>\n" +
                    "    <td colspan='2' style='vertical-align: top;'><h5 style='margin-top: 0px; margin-bottom: 0px;'>NOMBRE_REEMPLAZO</h5></td>\n" +
                    "  </tr>\n" +
                    "  <tr>\n" +
                    "    <td  colspan='1'> " + 
                                " <code class='label-cyt-grp-niv'><span style='font-size: medium;' >CODE_REEMPLAZO</span></code> " +
                                " <code class=\"label-cyt-grp-niv\"><span >GRUPO_REEMPLAZO</span></code> " + 
                                " <code class=\"label-cyt-grp-niv\"><span >NIVEL_REEMPLAZO</span></code> " + 
                                " <code class=\"label-cyt-grp-niv\"><span >DEPTO_REEMPLAZO</span></code> " + 
                                " <code class=\"label-cyt-grp-niv\"><span >ID_REEMPLAZO</span></code> " + 
                    "    </td>\n" +
                    "    <td style='text-align: right;'><i class='fa fa-info-circle fa-lg' style='opacity: 0.5; padding-right: 5px;'></i></td>\n" +
                    "  </tr>\n" +
                    "  <tr>\n" +
                    "    <td style='text-align:center;' ></td>\n" +
                    "    <td> " + 
                    "    </td>\n" +
                    "    <td></td>\n" + 
                    "  </tr>\n" +
                    "  <tr>\n" +
                    "    <td colspan='3' style='height:3px;'></td>\n" +
                    "  </tr>\n" +
                    "</table>";
            
            if (isSelected) {
                html = html.replace("SELECTED_COLOR_REEMPLAZO", "background-color: #628cd5;");
            } else {
                html = html.replace("SELECTED_COLOR_REEMPLAZO", "background-color: #F8F8F8;");
            }
            
            html = html.replace("CODE_REEMPLAZO", value.getCode());
            html = html.replace("URL_FOTO_REEMPLAZO", value.getUrlPhoto());
            html = html.replace("APELLIDOS_REEMPLAZO", PersonalUI.ellipse(value.getApellidoPaterno(), 18) + " " + PersonalUI.ellipse(value.getApellidoMaterno(), 18));
            html = html.replace("NOMBRE_REEMPLAZO", value.getNombre());
            html = html.replaceAll("RFC_REEMPLAZO", value.getRfc());
            html = html.replace("GRUPO_REEMPLAZO", grupoStr);
            html = html.replace("NIVEL_REEMPLAZO", nivelStr);
            html = html.replace("DEPTO_REEMPLAZO", deptoStr);
            html = html.replace("ID_REEMPLAZO", value.getId().toString());
            
            sb.appendHtmlConstant(html);
        }
    }
    
//    public static native String camelize(String str)/*-{
//            return (str.match(/\-/gi) ? str.toLowerCase().replace(/\-(\w)/gi, function(a, c){return c.toUpperCase();}) : str);
//    }-*/;    
//    
//    public static String capitalize(String value) {
//    return value == null ? value : value.substring(0, 1).toUpperCase() + value.substring(1).toLowerCase();
//  }
//
  /**
   * Truncate a string and add an ellipsis ('...') to the end if it exceeds the
   * specified length.
   * 
   * @param value the string to truncate
   * @param len the maximum length to allow before truncating
   * @return the converted text
   */
  public static String ellipse(String value, int len) {
    if (value != null && value.length() > len) {
      return value.substring(0, len - 3) + "...";
    }
    return value;
  }

    private class SelectionHandler implements SelectionChangeEvent.Handler {
        @Override
        public void onSelectionChange(SelectionChangeEvent event) {
            if (event.getSource() instanceof SingleSelectionModel) {
                SingleSelectionModel selModel = (SingleSelectionModel) event.getSource();
                if (selModel.getSelectedObject() == null) {
                    // Limpiar seleccion
                } else {
                    if (selModel.getSelectedObject() instanceof Empleado) {

                        Empleado empleado = (Empleado) selModel.getSelectedObject();
                        
                        System.out.println(">> " + empleado);
                    }
                }
            }
        }
    }

}
