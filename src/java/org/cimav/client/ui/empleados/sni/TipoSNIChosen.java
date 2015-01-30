/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cimav.client.ui.empleados.sni;

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
import org.cimav.client.db.domain.ETipoSNI;
import org.gwtbootstrap3.client.ui.ValueListBox;

/**
 *
 * @author juan.calderon
 */
public class TipoSNIChosen extends Composite {
    
    private static SNIChosenUiBinder uiBinder = GWT.create(SNIChosenUiBinder.class);
    
    interface SNIChosenUiBinder extends UiBinder<Widget, TipoSNIChosen> {
    }
    
    @UiField HTMLPanel htmlPanel;
    private final ValueListBox<ETipoSNI> chosen;
    
    public TipoSNIChosen() {
        initWidget(uiBinder.createAndBindUi(this));
        
        chosen = new ValueListBox<>(new Renderer<ETipoSNI>() {
            @Override
            public String render(ETipoSNI object) {
                if (object == null) {
                    return "None";
                }
                return object.getNombre();
            }

            @Override
            public void render(ETipoSNI object, Appendable appendable) throws IOException {
                if (object != null) {
                    String s = render(object);
                    appendable.append(s);
                }
            }
        });

        htmlPanel.add(chosen);
        
        List<ETipoSNI> values = Arrays.asList(ETipoSNI.values());
        chosen.setValue(ETipoSNI.NO_APLICA); //default
        chosen.setAcceptableValues(values);
        
    }
    
    public void setSelected(ETipoSNI value) {
        chosen.setValue(value, true);
    }
    
    public ETipoSNI getSelected() {
        return chosen.getValue();
    }
    
}
