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
public class PersonalEditorUI extends Composite {
    
    private static PersonalEditorUIUiBinder uiBinder = GWT.create(PersonalEditorUIUiBinder.class);
    
    interface PersonalEditorUIUiBinder extends UiBinder<Widget, PersonalEditorUI> {
    }
    
//    private TextBox nombreTxtBox;
//    private TextBox paternoTxtBox;
//    private TextBox maternoTxtBox;
//    private TextBox rfcTxtBox;
//    private TextBox curpTxtBox;
//    private TextBox imssTxtBox;
//    private ListBox imssClinicaListBox;
//    private CheckBox tieneCreditoChkBox;
//    private TextBox creditoTxtBox;
//    private TextBox bancoTxtBox;
//    private TextBox cuentaBancoTxtBox;
//    private TextBox cuentaCimavTxtBox;
    
    public PersonalEditorUI() {
        initWidget(uiBinder.createAndBindUi(this));

    }
}
