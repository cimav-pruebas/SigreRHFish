/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cimav.client.personal;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import org.cimav.client.domain.ESede;
import org.cimav.client.domain.EStatusEmpleado;
import org.gwtbootstrap3.client.ui.RadioButton;

/**
 *
 * @author juan.calderon
 */
public class SedeGroup extends Composite {
    
    @UiField RadioButton btnChi;
    @UiField RadioButton btnMty;
    @UiField RadioButton btnDgo;
    
    private static final SedeGroupUiBinder uiBinder = GWT.create(SedeGroupUiBinder.class);
    
    interface SedeGroupUiBinder extends UiBinder<Widget, SedeGroup> {
    }
    
    public SedeGroup() {
        initWidget(uiBinder.createAndBindUi(this));
    }
    
    public ESede getValue() {
        if (btnChi.isActive()) {
            return ESede.CHIHUAHUA;
        } else if (btnMty.isActive()) {
            return ESede.MONTERREY;
        } else if (btnDgo.isActive()) {
            return ESede.DURANGO;
        }
        return ESede.CHIHUAHUA;
    }
    
    public void setValue(ESede sede) {
        if (sede == null || sede.equals(ESede.CHIHUAHUA)) {
            btnChi.setActive(true);
        } else if (sede.equals(ESede.MONTERREY)) {
            btnMty.setActive(true);
        } else if (sede.equals(ESede.DURANGO)) {
            btnDgo.setActive(true);
        } else {
           btnChi.setActive(true);
        }
    }
}
