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
public class PersonalItem extends Composite {

    private static PersonalItemUiBinder uiBinder = GWT.create(PersonalItemUiBinder.class);

    interface PersonalItemUiBinder extends UiBinder<Widget, PersonalItem> {
    }

//    @UiField HTML barraSeleccion;
//    @UiField Image urlPhoto;
//    @UiField HTML apellidos;
    public PersonalItem() {
        initWidget(uiBinder.createAndBindUi(this));
    }

//    public void setSelected(boolean isSelected) {
//        if (isSelected) {
//            this.barraSeleccion.setHTML("<span  style=background-color: #628cd5;' />");
//        } else {
//            this.barraSeleccion.setHTML("<span  style=background-color: #F8F8F8;' />");
//        }
//    }
//    public void setPhoto(String urlPhoto) {
//        this.urlPhoto.setUrl(urlPhoto);
//    }
//    
//    public void setApellidos(String apellidos) {
//        this.apellidos.setHTML("<h4 style='margin-top: 0px; margin-bottom: 0px;' " + apellidos + " />");
//    }
}
