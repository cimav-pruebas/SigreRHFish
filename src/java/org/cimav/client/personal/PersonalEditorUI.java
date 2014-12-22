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
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;
import org.gwtbootstrap3.client.ui.Button;

/**
 *
 * @author juan.calderon
 */
public class PersonalEditorUI extends Composite {
    
    private static PersonalEditorUIUiBinder uiBinder = GWT.create(PersonalEditorUIUiBinder.class);
    
    interface PersonalEditorUIUiBinder extends UiBinder<Widget, PersonalEditorUI> {
    }
    
    @UiField FlexTable editor;
    
    public PersonalEditorUI() {
        initWidget(uiBinder.createAndBindUi(this));
    }
}
