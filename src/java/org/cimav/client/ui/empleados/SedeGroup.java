/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cimav.client.ui.empleados;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import org.cimav.client.db.domain.ESede;
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
        
        
        SedeChangeHandler sedeChangeHandler = new SedeChangeHandler();
        btnChi.addClickHandler(sedeChangeHandler);
        btnMty.addClickHandler(sedeChangeHandler);
        btnDgo.addClickHandler(sedeChangeHandler);
        
    }
    
    private class SedeChangeHandler implements ClickHandler {
        @Override
        public void onClick(ClickEvent event) {
            ESede sede = (ESede) event.getSource();
            System.out.println(" ------ " + sede);
            SedeGroup.this.setSelected(sede);
        }
        
    }
    
    public ESede getSelected() {
        if (btnChi.isActive()) {
            return ESede.CHIHUAHUA;
        } else if (btnMty.isActive()) {
            return ESede.MONTERREY;
        } else if (btnDgo.isActive()) {
            return ESede.DURANGO;
        }
        return ESede.CHIHUAHUA;
    }
    
    public void setSelected(ESede sede) {
        if (sede == null || sede.equals(ESede.CHIHUAHUA)) {
            btnChi.setActive(true);
            btnMty.setActive(false);
            btnDgo.setActive(false);
        } else if (sede.equals(ESede.MONTERREY)) {
            btnChi.setActive(false);
            btnMty.setActive(true);
            btnDgo.setActive(false);
        } else if (sede.equals(ESede.DURANGO)) {
            btnChi.setActive(false);
            btnMty.setActive(false);
            btnDgo.setActive(true);
        } else {
            btnChi.setActive(true);
            btnMty.setActive(false);
            btnDgo.setActive(false);
        }
    }
}
