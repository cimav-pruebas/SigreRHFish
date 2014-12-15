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
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SingleSelectionModel;
import org.cimav.client.domain.Empleado;
import org.cimav.client.tools.DBEvent;
import org.cimav.client.tools.DBMethod;
import org.cimav.client.tools.DBTypeResult;
import org.gwtbootstrap3.client.ui.constants.IconType;
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
    
    @UiField Button cargarBtn;
    @UiField Label totalRegistrosLbl;
    @UiField TextBox searchTxt;
    
    public PersonalUI() {
        initWidget(uiBinder.createAndBindUi(this));
        
        CellList.Resources cellListResources = GWT.create(CellList.Resources.class);
        cellList = new CellList<Empleado>(new EmpleadoCell(new SingleSelectionModel<Empleado>()), cellListResources, PersonalDB.get().getDataProvider());
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
        
        cargarBtn.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                totalRegistrosLbl.setText("Cargando registros...");
                PersonalDB.get().findAll();
            }
        });
        
        
        PersonalDB.get().addMethodExecutedListener(new PersonalDB.MethodExecutedListener() {
            @Override
            public void onMethodExecuted(DBEvent dbEvent) {
                if (DBMethod.FIND_ALL.equals(dbEvent.getDbMethod())) {
                    if (DBTypeResult.SUCCESS.equals(dbEvent.getDbTypeResult())) {
                        String msg = "Registros: " + PersonalDB.get().getDataProvider().getList().size();
                        totalRegistrosLbl.setText(msg);
                        
                        GrowlOptions go = GrowlHelper.getNewOptions();
                        go.setSuccessType();
                        go.setAllowDismiss(false);
                        Growl.growl("",msg,Styles.FADE + " " + Styles.FONT_AWESOME_BASE /*+ " " + IconType.SMILE_O.getCssName()*/, go);
                        
                        
                    } else {
                        String msg = "Falló la carga de registros";
                        totalRegistrosLbl.setText(msg);
                        
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
                totalRegistrosLbl.setText("Registros: " + PersonalDB.get().getRowCount());
            }
        });
        
    }
    
    private class EmpleadoCell extends AbstractCell<Empleado> {
        
        private SingleSelectionModel<Empleado> docSelectionModel;
        
        EmpleadoCell(SingleSelectionModel<Empleado> docSelectionModel) {
            this.docSelectionModel = docSelectionModel;
        }
        
        @Override
        public void render(Cell.Context context, Empleado value, SafeHtmlBuilder sb) {
            if (value == null) {
                return;
            }
            String grupoStr = value.getGrupo() != null ? value.getGrupo().getCode() : "SIN_GRP";
            String deptoStr = value.getDepartamento() != null ? value.getDepartamento().getCodigo() : "SIN_DEPTO";
            String nivelStr = value.getNivel()!= null ? value.getNivel().getCode() : "SIN_NIVEL";
            
            String html =
                    "<table class='XXXX' cellspacing='0' cellpadding='0'> " + 
                    "   <tr> " +
                    "       <td colspan='2' align='left'> <img src='" + value.getUrlPhoto() + "'> <span>" 
                        + value.getCode() + "</bR>"  
                        + value.getName() + "</bR>"  
                        + value.getRfc() + "</bR>"  
                        + nivelStr + "</bR>"  
                        + deptoStr + "</bR>"  
                        + grupoStr +  " </span> </td> " +
                    "   </tr> " +
                    "</table> ";
            
            sb.appendHtmlConstant(html);
        }
    }
    
}
