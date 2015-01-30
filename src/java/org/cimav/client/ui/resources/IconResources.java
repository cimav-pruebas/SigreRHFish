/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cimav.client.ui.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ClientBundle.Source;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;

/**
 *
 * @author juan.calderon
 */
public interface IconResources extends ClientBundle {

    /** Get access to the css resource during gwt compilation */
    @CssResource.NotStrict
    @Source("images/css/baseIcons.css")
    CssResource css();

    /** Our sample image icon. Makes the image resource for the gwt-compiler's css composer accessible */
    @Source("resources/logo.jpg")
    ImageResource logo();
}
