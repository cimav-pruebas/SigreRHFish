/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cimav.client.departamentos;

import com.github.gwtbootstrap.client.ui.Alert;
import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.Collapse;
import com.github.gwtbootstrap.client.ui.CollapseTrigger;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.cimav.client.domain.Departamento;
import org.cimav.client.domain.DeptoDatabase;
import org.cimav.client.tools.InfoView;

/**
 *
 * @author juan.calderon
 */
public final class DepartamentoEditorUI extends Composite {

    private static DepartamentoEditorUIUiBinder uiBinder = GWT.create(DepartamentoEditorUIUiBinder.class);

    interface DepartamentoEditorUIUiBinder extends UiBinder<Widget, DepartamentoEditorUI> {
    }

    @UiField HTMLPanel mainPanel;
    @UiField FlexTable editor;
    @UiField Button btnGuardar;
    @UiField Button btnCancelar;

    @UiField Alert alertEditor;
    
    com.google.gwt.user.client.ui.TextBox txtCodigo;
    com.github.gwtbootstrap.client.ui.TextBox txtNombre;
//    com.github.gwtbootstrap.client.ui.TextArea txt3;

//    @UiHandler(value = {"txtCodigo", "txtNombre"})
//    public void onTextBoxBlur(BlurEvent e) {
//        //set dirty flag ...
//        System.out.println(">> " + e.getSource());
//    }
    

    public DepartamentoEditorUI() {
        initWidget(uiBinder.createAndBindUi(this));

// Create a table to layout the form options
        //FlexTable layout = new FlexTable();
        editor.setCellSpacing(6);
        FlexCellFormatter cellFormatter = editor.getFlexCellFormatter();

//        // Add a title to the form
//        layout.setHTML(0, 0, "Titulo Enorme que avance varios lugares.");
//        cellFormatter.setColSpan(0, 0, 2);
//        cellFormatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
        editor.getColumnFormatter().setWidth(0, "50px;");

        txtCodigo = new com.google.gwt.user.client.ui.TextBox();
        txtCodigo.setWidth("70px");
        txtCodigo.setVisibleLength(8);
        txtNombre = new com.github.gwtbootstrap.client.ui.TextBox();
        txtNombre.setWidth("500px");
        txtNombre.setVisibleLength(200);
//        txt3 = new com.github.gwtbootstrap.client.ui.TextArea();
//        txt3.setWidth("575px");

        // Add some standard form options
        // r, c, obj
        editor.setHTML(0, 0, "Código");
        editor.setWidget(1, 0, txtCodigo);
        editor.setHTML(0, 1, "Nombre");
        editor.setWidget(1, 1, txtNombre);
//        editor.setHTML(2, 0, "Descripción");
//        cellFormatter.setColSpan(2, 0, 2);
//        cellFormatter.setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_LEFT);
//        editor.setWidget(3, 0, txt3);
//        cellFormatter.setColSpan(3, 0, 2);

        // De inicio, poner en nulo
        this.setDepartamento(null);
        
        btnGuardar.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {

                // Copiar depto en edición
                Departamento inputDepto = new Departamento();
                inputDepto.setId(originDepto.getId());
                inputDepto.setCodigo(txtCodigo.getValue());
                inputDepto.setNombre(txtNombre.getValue());
                
                boolean isValid = validate(inputDepto);
                
                if (isValid) {
                    boolean isNew = inputDepto.getId() == null || inputDepto.getId() <= 0;
                    if (isNew) {
                        DeptoDatabase.get().addDepto(inputDepto);
                        InfoView.show("Insertado");
                        
                        setDepartamento(null); // Al no tener ID, tenemos que ponerlo en Nulo para evitar errores
                        
                    } else {
                        DeptoDatabase.get().updateDepto(inputDepto);
                        InfoView.show("Actualizado");
                    }
                } else {
                    InfoView.show("Invalido");
                }
            }
        });
        btnCancelar.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                // simplemente copiar/recuperar el originar
                setDepartamento(originDepto);
                
                InfoView.show("Cancelado");
                
            }
        });
        
        TextChange textChange = new TextChange();
        txtCodigo.addValueChangeHandler(textChange);
        txtNombre.addValueChangeHandler(textChange);
        
    }
    
    public final native Element getElementById(String elementId) /*-{
        return this.getElementById(elementId);
    }-*/;
    
    private Boolean validate(Departamento deptoToValidate) {
        
        //validation des données
        ValidatorFactory factory = Validation.byDefaultProvider().configure().buildValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Departamento>> violations = new HashSet<>();
        if (deptoToValidate != null) {
            violations = validator.validate(deptoToValidate);
        }
        // es valido si no hay constraints
        Boolean isValid = violations.isEmpty(); 
        // mostrar o no la alerta
        alertEditor.setVisible(!isValid);
        // poner msgs de constraints (si los hay)
        StringBuilder builder = new StringBuilder();
        builder.append("<ul>");
        for (ConstraintViolation constraintViolation : violations) {
            //TODO Detectar Elemento que falla la Validación para "decorarlo".
            builder.append("<li>").append(constraintViolation.getMessage()).append("</li>");
        }
        builder.append("</ul>");
        alertEditor.setHTML(builder.toString());
        return isValid;
    }
    
    private boolean isDirty;
    private boolean isNotNull;
    
    private class TextChange implements  ValueChangeHandler<String> {
        @Override
        public void onValueChange(ValueChangeEvent<String> event) {
            // al cambiar pasa a Dirty
            // TODO Falta hacer el Dirty al cambiar el contendio. Probablemente con JS, jQuery y/o Angular
            isDirty = true;
            updateWidgets();
        }
    }
    
    //TODO Falta notificar con etiqueta si es nuevo o edición
    
    //TODO Arquitectura: Que los catálogos se creen una sola vez; no cada vez que se accesan.
    
    // keep el depto original
    private Departamento originDepto;
    
    public void setDepartamento(Departamento departamento) {

        isDirty = false;
        isNotNull = departamento != null;
        
        originDepto = new Departamento();
        
        if (isNotNull) {
            originDepto.setId(departamento.getId());
            originDepto.setCodigo(departamento.getCodigo());
            originDepto.setNombre(departamento.getNombre());
            
            txtCodigo.setText(departamento.getCodigo());
            txtNombre.setText(departamento.getNombre());
            
        } else {
            txtCodigo.setText("");
            txtNombre.setText("");
        }

        validate(originDepto);
        
        updateWidgets();
    }
    
    private void updateWidgets() {
        txtCodigo.setEnabled(isNotNull);
        txtNombre.setEnabled(isNotNull);
        btnGuardar.setEnabled(isNotNull && isDirty);
        btnCancelar.setEnabled(isNotNull  && isDirty);
    }
    
}
