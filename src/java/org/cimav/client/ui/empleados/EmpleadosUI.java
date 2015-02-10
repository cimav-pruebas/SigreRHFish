/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cimav.client.ui.empleados;

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
import org.cimav.client.db.domain.Empleado;
import org.cimav.client.tools.ProviderEvent;
import org.cimav.client.tools.ProviderMethod;
import org.cimav.client.tools.TypeResult;
import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.TextBox;
import org.gwtbootstrap3.client.ui.constants.IconFlip;

/**
 *
 * @author juan.calderon
 */
public class EmpleadosUI extends Composite {

    private static final EmpleadosUIUiBinder uiBinder = GWT.create(EmpleadosUIUiBinder.class);

    interface EmpleadosUIUiBinder extends UiBinder<Widget, EmpleadosUI> {
    }

    @UiField
    public ScrollPanel scrollPanel;
    CellList<Empleado> cellList;

    @UiField
    TextBox searchTxt;
    @UiField
    Button reloadBtn;

    @UiField
    EmpleadosEditorUI empleadosEditorUI;

    private final SingleSelectionModel<Empleado> selectionModel;

    public EmpleadosUI() {
        initWidget(uiBinder.createAndBindUi(this));

        //CellList.Resources cellListResources = GWT.create(CellList.Resources.class);
        CellList.Resources cellListResources = GWT.create(ICellListResources.class);
        selectionModel = new SingleSelectionModel<>();
        cellList = new CellList<>(new EmpleadoCell(selectionModel), cellListResources, EmpleadosProvider.get().getDataProvider());
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
        EmpleadosProvider.get().addDataDisplay(cellList);

        EmpleadosProvider.get().addMethodExecutedListener(new ProviderMethodExecutedListener());

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
                
                EmpleadosUI.this.filtrar();
            }
        });

        // de inicio, el Editor no tiene seleccionando y por tanto no es visible
        this.showEditor(false);
        
        // orden inicial
        orderBy = EmpleadosProvider.ORDER_BY_NAME;
        // filtro inicial
        EmpleadosProvider.get().getDataProvider().setFilter("");

        /* Al arrancar, cargar a todos los empleados */
        reloadAll();
    }
    
    private void filtrar() {
        final String txtToSearch = searchTxt.getText();
        EmpleadosProvider.get().getDataProvider().setFilter(txtToSearch);
        
        String rows = EmpleadosProvider.get().getRowCountPropotional();
        reloadBtn.setText(rows);
        
        // si en la lista filtrada no aparece el seleccionado, deseleccionar
        Empleado empSel = selectionModel.getSelectedObject();
        if (empSel != null && !EmpleadosProvider.get().containsItem(empSel)) {
            selectionModel.setSelected(null, true);
        }
    }

    private int orderBy;

    @UiHandler({"btnByName", "btnByCode", "btnByGrupo", "btnByNivel"})
    void handleClick(ClickEvent e) {
        orderBy = -1;
        if (e.getSource().toString().contains("Nombre")) {
            orderBy = EmpleadosProvider.ORDER_BY_NAME;
        } else if (e.getSource().toString().contains("Num")) {
            orderBy = EmpleadosProvider.ORDER_BY_CODE;
        } else if (e.getSource().toString().contains("Grp")) {
            orderBy = EmpleadosProvider.ORDER_BY_GRUPO;
        } else if (e.getSource().toString().contains("Niv")) {
            orderBy = EmpleadosProvider.ORDER_BY_NIVEL;

            //selectionModel.setSelected(new Empleado(), true);
        }
        this.orderBy();
    }

    private void orderBy() {
        EmpleadosProvider.get().order(this.orderBy);
    }

    private void reloadAll() {
        EmpleadosProvider.get().findAll();
    }

    private class ProviderMethodExecutedListener implements EmpleadosProvider.MethodExecutedListener {

        @Override
        public void onMethodExecuted(ProviderEvent dbEvent) {
            if (ProviderMethod.FIND_ALL.equals(dbEvent.getDbMethod())) {
                EmpleadosUI.this.orderBy();

                EmpleadosUI.this.selectionModel.setSelected(null, true);
                
                EmpleadosUI.this.filtrar();
                
            } else if (ProviderMethod.CREATE.equals(dbEvent.getDbMethod())) {
                if (TypeResult.SUCCESS.equals(dbEvent.getDbTypeResult())) {
                    Empleado nuevoEmpleado = (Empleado) dbEvent.getResult();
                    selectionModel.setSelected(nuevoEmpleado, true);
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

            // TODO reemplazar código a pie por EmpleadosItem
            String es_null = "---";
            String grupoStr = value.getGrupo() != null ? value.getGrupo().getCode() : es_null;
            String deptoCodeStr = value.getDepartamento() != null ? value.getDepartamento().getCodigo() : es_null;
            String deptoNameStr = value.getDepartamento() != null ? value.getDepartamento().getNombre() : es_null;
            String nivelStr = value.getNivel() != null ? value.getNivel().getCode() : es_null;
            String sedeStr = value.getSede() != null ? value.getSede().getAbrev() : es_null;

            String html
                    = "<table width='100%' cellspacing='0' cellpadding='0' style='cursor: pointer; text-align: left; vertical-align: middle; border-bottom:1px solid lightgray;'>\n"
                    + "  <tr>\n"
                    + "    <td width='4px' rowspan='6' style='height:auto; width: 5px; SELECTED_COLOR_REEMPLAZO'></td>\n"
//                    + "    <td colspan='3' style='height:10px;'><span STYLE_INDICADOR_REEMPLAZO /></td>\n"
                    + "    <td colspan='3' style='height:10px;'></td>\n"
                    + "    <td width='4px' rowspan='6' style='height:auto; width: 5px; SELECTED_COLOR_REEMPLAZO'></td>\n"
                    + "  </tr>\n"
                    + "  <tr>\n"
                    + "    <td width='78px' rowspan='3' style='text-align: center;'><img src='URL_FOTO_REEMPLAZO' style='border:1px solid lightgray; margin-top: 3px; border-radius:50%; padding:2px;'></td>\n"
                    + "    <td colspan='2' style='vertical-align: bottom;'><h4 style='margin-top: 0px; margin-bottom: 0px; font-size: 17px;'>APELLIDOS_REEMPLAZO,</h4></td>\n"
                    + "  </tr>\n"
                    + "  <tr>\n"
                    + "    <td colspan='2' style='vertical-align: top;'><h5 style='margin-top: 0px; margin-bottom: 0px;'>NOMBRE_REEMPLAZO</h5></td>\n"
                    + "  </tr>\n"
                    + "  <tr>\n"
                    + "    <td  colspan='1'> "
//                    + " <code class='label-cyt-grp-niv'><span style='font-size: medium;' >CODE_REEMPLAZO</span></code> "
                    + " <code class='label-cyt-grp-niv'><span >CODE_REEMPLAZO</span></code> "
                    + " <code class=\"label-cyt-grp-niv\"><span >GRUPO_REEMPLAZO</span></code> "
                    + " <code class=\"label-cyt-grp-niv\"><span >NIVEL_REEMPLAZO</span></code> "
                    + " <code class=\"label-cyt-grp-niv\"><span >SEDE_REEMPLAZO</span></code> "
                    + " <code class=\"label-cyt-grp-niv\"><span >DEPTO_CODIGO_REEMPLAZO</span></code> "
//                    + " <code class=\"label-cyt-grp-niv\"><span >ID_REEMPLAZO</span></code> "
                    + "    </td>\n"
//                    + "    <td style='text-align: right;'><i class='fa fa-info-circle fa-lg' style='opacity: 0.5; padding-right: 5px;'></i></td>\n"
                    + "  </tr>\n"
                    + "  <tr>\n"
                    + "    <td style='text-align:center;' ></td>\n"
                    + "    <td>"
                    + " <code class=\"label-cyt-grp-niv\"><span >DEPTO_NAME_REEMPLAZO</span></code> "
                    + "    </td>\n"
                    + "    <td></td>\n"
                    + "  </tr>\n"
                    + "  <tr>\n"
                    + "    <td colspan='3' style='height:10px;'></td>\n"
                    + "  </tr>\n"
                    + "</table>";

            if (isSelected) {
                html = html.replace("SELECTED_COLOR_REEMPLAZO", "background-color: #628cd5;");
//                html = html.replace("STYLE_INDICADOR_REEMPLAZO", 
//                        "style = 'position: relative; float: right; top: 4px; width: 14px; height: 14px; border-top: 3px solid #628cd5;\n" +
//                                                                 "-moz-transform: rotate(45deg); -ms-transform: rotate(45deg); -webkit-transform: rotate(45deg);\n" +
//                                                                 "transform: rotate(45deg); overflow: hidden; right: 8px; border-right: 3px solid #628cd5;' ");
            } else {
                html = html.replace("SELECTED_COLOR_REEMPLAZO", "background-color: #F8F8F8;");
//                html = html.replace("STYLE_INDICADOR_REEMPLAZO", "");
            }
                       
            html = html.replace("CODE_REEMPLAZO", value.getCode());
            html = html.replace("URL_FOTO_REEMPLAZO", value.getUrlPhoto());
            html = html.replace("APELLIDOS_REEMPLAZO", EmpleadosUI.ellipse(value.getApellidoPaterno(), 18) + " " + EmpleadosUI.ellipse(value.getApellidoMaterno(), 18));
            html = html.replace("NOMBRE_REEMPLAZO", value.getNombre());
            html = html.replaceAll("RFC_REEMPLAZO", value.getRfc());
            html = html.replace("GRUPO_REEMPLAZO", grupoStr);
            html = html.replace("NIVEL_REEMPLAZO", nivelStr);
            html = html.replace("DEPTO_CODIGO_REEMPLAZO", deptoCodeStr);
            html = html.replace("DEPTO_NAME_REEMPLAZO", EmpleadosUI.ellipse(deptoNameStr, 32));
            html = html.replace("SEDE_REEMPLAZO", sedeStr);
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
     * Truncate a string and add an ellipsis ('...') to the end if it exceeds the specified length.
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

    private void showEditor(boolean show) {
        empleadosEditorUI.setVisible(show);
    }
    private class SelectionHandler implements SelectionChangeEvent.Handler {

        @Override
        public void onSelectionChange(SelectionChangeEvent event) {
            if (event.getSource() instanceof SingleSelectionModel) {
                SingleSelectionModel selModel = (SingleSelectionModel) event.getSource();
                Empleado empleadoSelected = (Empleado) selModel.getSelectedObject();
                System.out.println("Sel>>>> " + empleadoSelected);
                empleadosEditorUI.setBean(empleadoSelected);
                
                // Mostrar editor sólo cuando hay seleccionado
                EmpleadosUI.this.showEditor(empleadoSelected != null);
            }
        }
    }

}
