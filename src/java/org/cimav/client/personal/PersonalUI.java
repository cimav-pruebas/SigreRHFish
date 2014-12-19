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
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import org.cimav.client.domain.Empleado;
import org.cimav.client.tools.DBEvent;
import org.cimav.client.tools.DBMethod;
import org.cimav.client.tools.DBTypeResult;
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
    
    private static PersonalUIUiBinder uiBinder = GWT.create(PersonalUIUiBinder.class);
    
    interface PersonalUIUiBinder extends UiBinder<Widget, PersonalUI> {
    }
    
    @UiField public ScrollPanel scrollPanel;
    CellList<Empleado> cellList;
    
    @UiField TextBox searchTxt;
    @UiField Button reloadBtn;
    
    public PersonalUI() {
        initWidget(uiBinder.createAndBindUi(this));
        
        //CellList.Resources cellListResources = GWT.create(CellList.Resources.class);
        CellList.Resources cellListResources = GWT.create(ICellListResources.class);
        SingleSelectionModel<Empleado> selectionModel = new SingleSelectionModel<>();
        cellList = new CellList<>(new EmpleadoCell(selectionModel), cellListResources, PersonalDB.get().getDataProvider());
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
        PersonalDB.get().addDataDisplay(cellList);
        
        reloadBtn.setIconFlip(IconFlip.HORIZONTAL);
        reloadBtn.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                reloadBtn.setIconSpin(true);  
                PersonalDB.get().findAll();
            }
        });
        
        
        PersonalDB.get().addMethodExecutedListener(new PersonalDB.MethodExecutedListener() {
            @Override
            public void onMethodExecuted(DBEvent dbEvent) {
                if (DBMethod.FIND_ALL.equals(dbEvent.getDbMethod())) {
                    
                    reloadBtn.setIconSpin(false);
                    String m = "" + PersonalDB.get().getDataProvider().getDataDisplays().size() + "/" + PersonalDB.get().getDataProvider().getList().size();
                    reloadBtn.setText(m);
                    
                    if (DBTypeResult.SUCCESS.equals(dbEvent.getDbTypeResult())) {
                        String msg = "Registros: " + PersonalDB.get().getDataProvider().getList().size();
                        GrowlOptions go = GrowlHelper.getNewOptions();
                        go.setSuccessType();
                        go.setAllowDismiss(false);
                        Growl.growl("",msg,Styles.FADE + " " + Styles.FONT_AWESOME_BASE /*+ " " + IconType.SMILE_O.getCssName()*/, go);
                        
                    } else {
                        String msg = "Falló la carga de registros";
                        GrowlOptions go = GrowlHelper.getNewOptions();
                        go.setDangerType();
                        go.setDelay(15000); // 15 segs
                        //go.setAllowDismiss(false);
                        Growl.growl("",msg,Styles.FONT_AWESOME_BASE /*+ " " + IconType.SMILE_O.getCssName()*/, go);
                    }
                }
            }
        });
        
        searchTxt.addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
                final String txtToSearch = searchTxt.getText();
                PersonalDB.get().getDataProvider().setFilter(txtToSearch);
                String rows = PersonalDB.get().getRowCountPropotional();
                reloadBtn.setText(rows);
            }
        });
        
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
            
            String grupoStr = value.getGrupo() != null ? value.getGrupo().getCode() : "SIN_GRP";
            String deptoStr = value.getDepartamento() != null ? value.getDepartamento().getCodigo() : "SIN_DEPTO";
            String nivelStr = value.getNivel()!= null ? value.getNivel().getCode() : "SIN_NIVEL";
            
            String html =
                    "<table width='100%' cellspacing='0' cellpadding='0' style='text-align: left; vertical-align: middle; border-bottom:1px solid lightgray;'>\n" +
                    "  <tr>\n" +
                    "    <td width='4px' rowspan='6' style='height:auto; width: 5px; SELECTED_COLOR_REEMPLAZO'></td>\n" +
                    "    <td colspan='3' style='height:3px;'></td>\n" +
                    "  </tr>\n" +
                    "  <tr>\n" +
                    "    <td width='78px' rowspan='3' style='text-align: center;'><img src='URL_FOTO_REEMPLAZO' style='border:1px solid lightgray; margin-top: 3px;'></td>\n" +
                    "    <td colspan='2'><h4 style='margin-top: 0px; margin-bottom: 0px;'>APELLIDOS_REEMPLAZO</h4></td>\n" +
                    "  </tr>\n" +
                    "  <tr>\n" +
                    "    <td colspan='2'><h5 style='margin-top: 0px; margin-bottom: 0px;'>NOMBRE_REEMPLAZO</h5></td>\n" +
                    "  </tr>\n" +
                    "  <tr>\n" +
                    "    <td>RFC_REEMPLAZO</td>\n" +
                    "    <td></td>\n" +
                    "  </tr>\n" +
                    "  <tr>\n" +
                    "    <td style='text-align:center;' ><code class='label-cyt-grp-niv'><span style='font-size: medium;' >CODE_REEMPLAZO</span></code></td>\n" +
                    "    <td> " + 
                                " <code class=\"label-cyt-grp-niv\"><span >GRUPO_REEMPLAZO</span></code> " + 
                                " <code class=\"label-cyt-grp-niv\"><span >NIVEL_REEMPLAZO</span></code> " + 
                                " <code class=\"label-cyt-grp-niv\"><span >DEPTO_REEMPLAZO</span></code> " + 
                    "    </td>\n" +
                    "    <td style='text-align: right;'><i class=\"fa fa-info-circle fa-lg\" style='opacity: 0.5; padding-right: 5px;'></i></td>\n" +
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
            html = html.replace("APELLIDOS_REEMPLAZO", value.getApellidoPaterno() + " " + value.getApellidoMaterno());
            html = html.replace("NOMBRE_REEMPLAZO", value.getNombre());
            html = html.replaceAll("RFC_REEMPLAZO", value.getRfc());
            html = html.replace("GRUPO_REEMPLAZO", grupoStr);
            html = html.replace("NIVEL_REEMPLAZO", nivelStr);
            html = html.replace("DEPTO_REEMPLAZO", deptoStr);
            
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
//  /**
//   * Truncate a string and add an ellipsis ('...') to the end if it exceeds the
//   * specified length.
//   * 
//   * @param value the string to truncate
//   * @param len the maximum length to allow before truncating
//   * @return the converted text
//   */
//  public static String ellipse(String value, int len) {
//    if (value != null && value.length() > len) {
//      return value.substring(0, len - 3) + "...";
//    }
//    return value;
//  }

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
