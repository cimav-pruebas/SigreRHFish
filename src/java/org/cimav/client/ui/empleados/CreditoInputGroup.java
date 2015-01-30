/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cimav.client.ui.empleados;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import org.gwtbootstrap3.client.ui.CheckBoxButton;
import org.gwtbootstrap3.client.ui.TextBox;

/**
 *
 * @author juan.calderon
 */
public class CreditoInputGroup extends Composite {

    private static CreditoInputGroupUiBinder uiBinder = GWT.create(CreditoInputGroupUiBinder.class);

    interface CreditoInputGroupUiBinder extends UiBinder<Widget, CreditoInputGroup> {
    }

    @UiField
    CheckBoxButton hasCreditoChkBox;
    @UiField
    TextBox creditoTxtBox;

    public CreditoInputGroup() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    public Boolean hasCredito() {
        return hasCreditoChkBox.getValue();
    }

    public String getNumCredito() {
        return creditoTxtBox.getText();
    }

    public void setHasCredito(boolean hasCredito) {
        hasCreditoChkBox.setValue(hasCredito, true);
    }

    public void setNumCredito(String numCredito) {
        creditoTxtBox.setValue(numCredito);
    }
}
