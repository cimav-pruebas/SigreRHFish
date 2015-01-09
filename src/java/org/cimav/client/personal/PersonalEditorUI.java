/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cimav.client.personal;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.text.shared.Renderer;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.cimav.client.domain.BaseProvider;
import org.cimav.client.domain.Departamento;
import org.cimav.client.domain.DeptoDatabase;
import org.cimav.client.domain.EBanco;
import org.cimav.client.domain.EClinica;
import org.cimav.client.domain.Empleado;
import org.cimav.client.tools.DBEvent;
import org.cimav.client.tools.ProviderEvent;
import org.cimav.client.tools.ProviderMethod;
import org.cimav.client.tools.TypeResult;
import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.TextBox;
import org.gwtbootstrap3.client.ui.ValueListBox;
import org.gwtbootstrap3.client.ui.constants.Styles;
import org.gwtbootstrap3.extras.growl.client.ui.Growl;
import org.gwtbootstrap3.extras.growl.client.ui.GrowlHelper;
import org.gwtbootstrap3.extras.growl.client.ui.GrowlOptions;

/**
 *
 * @author juan.calderon
 */
public class PersonalEditorUI extends Composite {
    
    private static PersonalEditorUIUiBinder uiBinder = GWT.create(PersonalEditorUIUiBinder.class);
    
    interface PersonalEditorUIUiBinder extends UiBinder<Widget, PersonalEditorUI> {
    }
    
    @UiField FlexTable flexEditorPersonal;
    @UiField FlexTable flexEditorLaboral;
    
    @UiField Button saveBtn;
    
    // personal
    private final TextBox nombreTxtBox;
    private final TextBox paternoTxtBox;
    private final TextBox maternoTxtBox;
    private final TextBox rfcTxtBox;
    private final TextBox curpTxtBox;
    private final TextBox imssTxtBox;
    private final ValueListBox<EClinica> imssClinicaListBox;
    private final CreditoInputGroup creditoInputGroup;
    private final ValueListBox<EBanco> bancoListBox;
    private final TextBox cuentaBancoTxtBox;
    private final TextBox cuentaCimavTxtBox;
    // laboral
    // xmlns:chzn="urn:import:com.watopi.chosen.client.gwt"
    
    private final ValueListBox<Departamento> deptoChosen;
    
    public PersonalEditorUI() {
        initWidget(uiBinder.createAndBindUi(this));

        FlexTable.FlexCellFormatter cellFormatter = flexEditorPersonal.getFlexCellFormatter();
        
        flexEditorPersonal.setCellSpacing(0);
        flexEditorPersonal.setCellPadding(0);
//        flexEditorPersonal.getColumnFormatter().setWidth(0, "50px;");

        nombreTxtBox = new TextBox();
        nombreTxtBox.setWidth("244px");
        paternoTxtBox = new TextBox();
        paternoTxtBox.setWidth("244px");
        maternoTxtBox = new TextBox();
        maternoTxtBox.setWidth("244px");
        
        rfcTxtBox = new TextBox();
        rfcTxtBox.setWidth("244px");
        curpTxtBox = new TextBox();
        curpTxtBox.setWidth("244px");

        imssTxtBox = new TextBox();
        imssTxtBox.setWidth("244px");
        imssClinicaListBox = new ValueListBox<>(new Renderer<EClinica>(){
            @Override
            public String render(EClinica object) {
                if (object == null) {
                    return "Nada";
                }
                return object.getNombre();
            }
            @Override
            public void render(EClinica object, Appendable appendable) throws IOException {
                String s = render(object);
                appendable.append(s);
            }
        });
        List<EClinica> clinicas = Arrays.asList(EClinica.values());
        imssClinicaListBox.setValue(EClinica.MORELOS); //default
        imssClinicaListBox.setAcceptableValues(clinicas);
        imssClinicaListBox.setWidth("244px");
        
        creditoInputGroup = new CreditoInputGroup();
        creditoInputGroup.setWidth("244px");
        bancoListBox = new ValueListBox<>(new Renderer<EBanco>() {
            @Override
            public String render(EBanco object) {
                if (object == null) {
                    return "None";
                }
                return object.getNombre();
            }
            @Override
            public void render(EBanco object, Appendable appendable) throws IOException {
                String s = render(object);
                appendable.append(s);
            }
        });
        List<EBanco> bancos = Arrays.asList(EBanco.values());
        bancoListBox.setValue(EBanco.BANORTE); //default
        bancoListBox.setAcceptableValues(bancos);
        bancoListBox.setWidth("244px");
        cuentaBancoTxtBox = new TextBox();
        cuentaBancoTxtBox.setWidth("244px");
        
        cuentaCimavTxtBox = new TextBox();
        cuentaCimavTxtBox.setWidth("244px");

        deptoChosen = new ValueListBox<>(new Renderer<Departamento>() {
            @Override
            public String render(Departamento object) {
                if (object == null) {
                    return "None";
                }
                return object.getNombre();
            }
            @Override
            public void render(Departamento object, Appendable appendable) throws IOException {
                String s = render(object);
                appendable.append(s);
            }
        });
//        List<Departamento> deptos = Arrays.asList(Departamento.values());
//        deptoChosen.setValue(EBanco.BANORTE); //default
//        deptoChosen.setAcceptableValues(deptos);
        deptoChosen.setWidth("244px");
        DeptoDatabase.get().addMethodExecutedListener(new RestMethodExecutedListener());
        DeptoDatabase.get().findAll();
        
        String htmlColSpc = "<span style='margin-right: 10px;'></span>";
        String htmlRowSpc = "<span style='margin-bottom: 10px; display: block;'></span>";
                
        // Add some standard form options
        // r, c, obj
        flexEditorPersonal.setWidget(0, 0, new HTML("<strong>Nombre(s)</strong>"));
        flexEditorPersonal.setWidget(0, 1, new HTML(htmlColSpc));
        flexEditorPersonal.setWidget(1, 0, nombreTxtBox);
        flexEditorPersonal.setHTML(0, 2, "A. Paterno");
        flexEditorPersonal.setWidget(0, 3, new HTML(htmlColSpc));
        flexEditorPersonal.setWidget(1, 2, paternoTxtBox);
        flexEditorPersonal.setHTML(0, 4, "A. Materno");
        flexEditorPersonal.setWidget(1, 4, maternoTxtBox);
        
        flexEditorPersonal.setWidget(2, 0, new HTML(htmlRowSpc));
        flexEditorPersonal.setHTML(3, 0, "RFC");
        flexEditorPersonal.setWidget(4, 0, rfcTxtBox);
        flexEditorPersonal.setHTML(3, 2, "CURP");
        flexEditorPersonal.setWidget(4, 2, curpTxtBox);
        
        flexEditorPersonal.setWidget(5, 0, new HTML(htmlRowSpc));
        flexEditorPersonal.setHTML(6, 0, "IMSS");
        flexEditorPersonal.setWidget(7, 0, imssTxtBox);
        flexEditorPersonal.setHTML(6, 2, "Clinica");
        flexEditorPersonal.setWidget(7, 2, imssClinicaListBox);
        
        flexEditorPersonal.setWidget(8, 0, new HTML(htmlRowSpc));
        flexEditorPersonal.setHTML(9, 0, "Crédito");
        flexEditorPersonal.setWidget(10, 0, creditoInputGroup);
        
        flexEditorPersonal.setWidget(11, 0, new HTML(htmlRowSpc));
        flexEditorPersonal.setHTML(12, 0, "Banco");
        flexEditorPersonal.setWidget(13, 0, bancoListBox);
        flexEditorPersonal.setHTML(12, 2, "Cuenta");
        flexEditorPersonal.setWidget(13, 2, cuentaBancoTxtBox);
        
        flexEditorPersonal.setWidget(14, 0, new HTML(htmlRowSpc));
        flexEditorPersonal.setHTML(15, 0, "Cuenta CIMAV");
        flexEditorPersonal.setWidget(16, 0, cuentaCimavTxtBox);
        
        flexEditorPersonal.setWidget(17, 0, new HTML(htmlRowSpc));
        flexEditorPersonal.setHTML(18, 0, "Departamento");
        flexEditorPersonal.setWidget(19, 0, deptoChosen);
        
//        editor.setHTML(2, 0, "Descripción");
//        cellFormatter.setColSpan(2, 0, 2);
//        cellFormatter.setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_LEFT);
//        editor.setWidget(3, 0, txt3);
//        cellFormatter.setColSpan(3, 0, 2);

        saveBtn.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                
                Empleado empleado = new Empleado();
                empleado.setNombre(nombreTxtBox.getText());
                empleado.setApellidoPaterno(paternoTxtBox.getText());
                empleado.setApellidoMaterno(maternoTxtBox.getText());
                empleado.setRfc(rfcTxtBox.getText());
                empleado.setUrlPhoto("http://cimav.edu.mx/foto/isai.arenas");
                
                empleado.setDepartamento(deptoChosen.getValue());
               // empleado.setClinica(imssClinicaListBox.getValue());

                
               PersonalProvider.get().add(empleado);
            }
        });
        
        PersonalProvider.get().addMethodExecutedListener(new ProviderMethodExecutedListener());
    }
    
    private class RestMethodExecutedListener implements DeptoDatabase.MethodExecutedListener {

        @Override
        public void onMethodExecuted(DBEvent dbEvent) {
            
            List<Departamento> deptos = (List<Departamento>) dbEvent.getResult();
            deptoChosen.setAcceptableValues(deptos);
            
        }
        
    }
    
    private class ProviderMethodExecutedListener implements PersonalProvider.MethodExecutedListener {

        @Override
        public void onMethodExecuted(ProviderEvent dbEvent) {
            if (ProviderMethod.CREATE.equals(dbEvent.getDbMethod())) {
                if (TypeResult.SUCCESS.equals(dbEvent.getDbTypeResult())) {
                    String msg = "Regisro Nuevo";
                    GrowlOptions go = GrowlHelper.getNewOptions();
                    go.setSuccessType();
                    go.setAllowDismiss(false);
                    Growl.growl("", msg, Styles.FADE + " " + Styles.FONT_AWESOME_BASE /*+ " " + IconType.SMILE_O.getCssName()*/, go);

                } else {
                    String msg = "Falló el regisgro nuevo";
                    GrowlOptions go = GrowlHelper.getNewOptions();
                    go.setDangerType();
                    go.setDelay(15000); // 15 segs
                    //go.setAllowDismiss(false);
                    Growl.growl("", msg, Styles.FONT_AWESOME_BASE /*+ " " + IconType.SMILE_O.getCssName()*/, go);
                }
            }
        }
    }
    
}
