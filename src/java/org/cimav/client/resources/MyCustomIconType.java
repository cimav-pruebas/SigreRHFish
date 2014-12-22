/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cimav.client.resources;

import com.github.gwtbootstrap.client.ui.constants.BaseIconType;
import com.google.gwt.core.client.GWT;

/**
 *
 * @author juan.calderon
 */
public enum MyCustomIconType implements BaseIconType {

    logo; // Our runtime access

    /** Inject the icon's css once at first usage */
    static {
        IconResources icons = GWT.create(IconResources.class);
        icons.css().ensureInjected();
    }

    private static final String PREFIX = "myBaseIcon_";
    private String className;

    private MyCustomIconType() {
        this.className = this.name().toLowerCase();
    }
    @Override public String get() {
        return PREFIX + className;
    }
}
