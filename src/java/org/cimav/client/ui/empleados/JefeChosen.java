/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cimav.client.ui.empleados;

import org.cimav.client.db.rest.EmpleadoREST;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.text.shared.Renderer;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import java.io.IOException;
import java.util.List;
import org.cimav.client.db.rest.BaseREST;
import org.cimav.client.db.domain.Empleado;
import org.cimav.client.tools.ProviderMethod;
import org.cimav.client.tools.RESTEvent;
import org.cimav.client.tools.TypeResult;
import org.gwtbootstrap3.client.ui.Image;
import org.gwtbootstrap3.client.ui.ValueListBox;
import org.gwtbootstrap3.extras.growl.client.ui.Growl;

/**
 *
 * @author juan.calderon
 */
public class JefeChosen extends Composite {
    
    private static JefeChosenUiBinder uiBinder = GWT.create(JefeChosenUiBinder.class);
    
    private final EmpleadoREST rest;
    
    @UiField Image fotoImg;
    @UiField HTML htmlDatos;
    @UiField FlowPanel comboWrap;
    private final ValueListBox<Empleado> chosen;
    
    interface JefeChosenUiBinder extends UiBinder<Widget, JefeChosen> {
    }
    
    public JefeChosen() {
        initWidget(uiBinder.createAndBindUi(this));
        
        chosen = new ValueListBox<>(new Renderer<Empleado>() {
            @Override
            public String render(Empleado object) {
                if (object == null) {
                    return "None";
                }
                return object.getName();
            }

            @Override
            public void render(Empleado object, Appendable appendable) throws IOException {
                if (object != null) {
                    String s = render(object);
                    appendable.append(s);
                }
            }
        });

        chosen.addValueChangeHandler(new ValueChangeHandler<Empleado>() {
            @Override
            public void onValueChange(ValueChangeEvent<Empleado> event) {
                
                String htmlStr = "&nbsp;";
                
                if (event != null && event.getValue() != null) {
                    Empleado emp = event.getValue();
                    fotoImg.setUrl(emp.getUrlPhoto());
//                    htmlStr = 
//                        " <code class='label-cyt-grp-niv'><span >" + emp.getCode() + "</span></code> " + 
//                        " <code class='label-cyt-grp-niv'><span >" + emp.getGrupo()+ "</span></code> " +
//                        " <code class='label-cyt-grp-niv'><span >" + emp.getNivel() + "</span></code> " +
//                        " <code class='label-cyt-grp-niv'><span >" + emp.getDepartamento() + "</span></code> " +
//                        " <code class='label-cyt-grp-niv'><span >" + emp.getId() + "</span></code> ";
                } else {
                    fotoImg.setUrl("http://cimav.edu.mx/foto/default");
                }
                
                htmlDatos.setHTML(htmlStr);
            }
        });
        
        comboWrap.add(chosen);
        
        rest = new EmpleadoREST();
        rest.addRESTExecutedListener(new BaseREST.RESTExecutedListener() {
            @Override
            public void onRESTExecuted(RESTEvent restEvent) {
                if (restEvent.getDbMethod().equals(ProviderMethod.FIND_ALL_BASE)) {
                    if (restEvent.getDbTypeResult().equals(TypeResult.SUCCESS)) {
                        
                        List<Empleado> jefes = (List<Empleado>) restEvent.getResult();
                        chosen.setAcceptableValues(jefes);
                        
                    } else {
                        Growl.growl("Falló la carga de Jefes");
                    }
                }
            }
        });
        rest.findAllBase();
        
    }
    
    public void setValue(Empleado jefe) {
        chosen.setValue(jefe, true);
    }
    
    public Empleado getValue() {
        return chosen.getValue();
    }
    
}
