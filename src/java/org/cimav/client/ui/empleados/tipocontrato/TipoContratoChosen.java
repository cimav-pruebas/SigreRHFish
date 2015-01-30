/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cimav.client.ui.empleados.tipocontrato;

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
import org.cimav.client.db.domain.ETipoContrato;
import org.gwtbootstrap3.client.ui.ValueListBox;

/**
 *
 * @author juan.calderon
 */
public class TipoContratoChosen extends Composite {
    
    private static TipoContratoChosenUiBinder uiBinder = GWT.create(TipoContratoChosenUiBinder.class);
    
    interface TipoContratoChosenUiBinder extends UiBinder<Widget, TipoContratoChosen> {
    }
    
    @UiField HTMLPanel htmlPanel;
    private final ValueListBox<ETipoContrato> chosen;
    
    public TipoContratoChosen() {
        initWidget(uiBinder.createAndBindUi(this));
        
        chosen = new ValueListBox<>(new Renderer<ETipoContrato>() {
            @Override
            public String render(ETipoContrato object) {
                if (object == null) {
                    return "None";
                }
                return object.getNombre();
            }

            @Override
            public void render(ETipoContrato object, Appendable appendable) throws IOException {
                if (object != null) {
                    String s = render(object);
                    appendable.append(s);
                }
            }
        });

        htmlPanel.add(chosen);
        
        List<ETipoContrato> values = Arrays.asList(ETipoContrato.values());
        chosen.setValue(ETipoContrato.INDETERMINADO); //default
        chosen.setAcceptableValues(values);
        
    }
    
    public void setSelected(ETipoContrato value) {
        chosen.setValue(value, true);
    }
    
    public ETipoContrato getSelected() {
        return chosen.getValue();
    }
    
}
