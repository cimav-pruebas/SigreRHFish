/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cimav.client.personal;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author juan.calderon
 */
public class CreditoInputGroup extends Composite {
    
    private static CreditoInputGroupUiBinder uiBinder = GWT.create(CreditoInputGroupUiBinder.class);
    
    interface CreditoInputGroupUiBinder extends UiBinder<Widget, CreditoInputGroup> {
    }
    
    public CreditoInputGroup() {
        initWidget(uiBinder.createAndBindUi(this));
    }
}
