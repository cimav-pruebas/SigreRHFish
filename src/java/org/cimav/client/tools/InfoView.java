/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cimav.client.tools;

import com.github.gwtbootstrap.client.ui.Alert;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author juan.calderon
 */
public class InfoView extends Composite {
    
    private static InfoViewUiBinder uiBinder = GWT.create(InfoViewUiBinder.class);
    
    interface InfoViewUiBinder extends UiBinder<Widget, InfoView> {
    }
    
    @UiField static Alert alertInfoView;
    
    public InfoView() {
        initWidget(uiBinder.createAndBindUi(this));
        
        alertInfoView.setVisible(false);
        alertInfoView.setAnimation(true);
    }
    
    public static void show(final String mshHtml) {
        alertInfoView.setHTML(mshHtml);
        alertInfoView.setVisible(true);
        // Create a new timer that calls Window.alert().
        Timer t = new Timer() {
            @Override
            public void run() {
                alertInfoView.setVisible(false);
                alertInfoView.setHTML("<p>Vacío</p>");
            }
        };
        // Schedule the timer to run once in 5 seconds.
        t.schedule(3000);
    }
}
