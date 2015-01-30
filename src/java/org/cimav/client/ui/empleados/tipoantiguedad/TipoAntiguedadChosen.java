/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cimav.client.ui.empleados.tipoantiguedad;

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
import org.cimav.client.db.domain.ETipoAntiguedad;
import org.gwtbootstrap3.client.ui.ValueListBox;

/**
 *
 * @author juan.calderon
 */
public class TipoAntiguedadChosen extends Composite {
    
    private static TipoAntiguedadChosenUiBinder uiBinder = GWT.create(TipoAntiguedadChosenUiBinder.class);
    
    interface TipoAntiguedadChosenUiBinder extends UiBinder<Widget, TipoAntiguedadChosen> {
    }
    
    @UiField HTMLPanel htmlPanel;
    private final ValueListBox<ETipoAntiguedad> chosen;
    
    public TipoAntiguedadChosen() {
        initWidget(uiBinder.createAndBindUi(this));
        
        chosen = new ValueListBox<>(new Renderer<ETipoAntiguedad>() {
            @Override
            public String render(ETipoAntiguedad object) {
                if (object == null) {
                    return "None";
                }
                return object.getNombre();
            }

            @Override
            public void render(ETipoAntiguedad object, Appendable appendable) throws IOException {
                if (object != null) {
                    String s = render(object);
                    appendable.append(s);
                }
            }
        });

        htmlPanel.add(chosen);
        
        List<ETipoAntiguedad> values = Arrays.asList(ETipoAntiguedad.values());
        chosen.setValue(ETipoAntiguedad.SIN_ANTIGUEDAD); //default
        chosen.setAcceptableValues(values);
        
    }
    
    public void setSelected(ETipoAntiguedad value) {
        chosen.setValue(value, true);
    }
    
    public ETipoAntiguedad getSelected() {
        return chosen.getValue();
    }
    
}
