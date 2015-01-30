/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cimav.client.ui.empleados.tipoempleado;

import com.google.gwt.core.client.GWT;
import com.google.gwt.text.shared.Renderer;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.cimav.client.db.domain.ETipoEmpleado;
import org.gwtbootstrap3.client.ui.ValueListBox;

/**
 *
 * @author juan.calderon
 */
public class TipoEmpleadoChosen extends Composite {
    
    private static TipoEmpleadoChosenUiBinder uiBinder = GWT.create(TipoEmpleadoChosenUiBinder.class);
    
    interface TipoEmpleadoChosenUiBinder extends UiBinder<Widget, TipoEmpleadoChosen> {
    }
    
    @UiField HTMLPanel htmlPanel;
    private final ValueListBox<ETipoEmpleado> chosen;
    
    public TipoEmpleadoChosen() {
        initWidget(uiBinder.createAndBindUi(this));
        
        chosen = new ValueListBox<>(new Renderer<ETipoEmpleado>() {
            @Override
            public String render(ETipoEmpleado object) {
                if (object == null) {
                    return "None";
                }
                return object.getNombre();
            }

            @Override
            public void render(ETipoEmpleado object, Appendable appendable) throws IOException {
                if (object != null) {
                    String s = render(object);
                    appendable.append(s);
                }
            }
        });

        htmlPanel.add(chosen);
        
        List<ETipoEmpleado> values = Arrays.asList(ETipoEmpleado.values());
        chosen.setValue(ETipoEmpleado.NORMAL); //default
        chosen.setAcceptableValues(values);
        
    }
    
    public void setSelected(ETipoEmpleado value) {
        chosen.setValue(value, true);
    }
    
    public ETipoEmpleado getSelected() {
        return chosen.getValue();
    }
    
}
