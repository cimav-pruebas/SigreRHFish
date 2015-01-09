/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cimav.client.domain;

import java.util.ArrayList;
import java.util.Iterator;
import org.cimav.client.tools.RESTEvent;

/**
 *
 * @author juan.calderon
 */
public class BaseREST {
    
//    private static BaseREST instance;
//
//    public static BaseREST get() {
//        if (instance == null) {
//            instance = new BaseREST();
//        }
//        return instance;
//    }
    
    // <editor-fold defaultstate="collapsed" desc="interface RESTExecutedListener"> 
    public interface RESTExecutedListener extends java.util.EventListener {
        void onRESTExecuted(RESTEvent restEvent);
    }
    private final ArrayList listeners = new ArrayList();
    public void addRESTExecutedListener(RESTExecutedListener listener) {
        listeners.add(listener);
    }
    public void removeRESTExecutedListener(RESTExecutedListener listener) {
        listeners.remove(listener);
    }
    public void onRESTExecuted(RESTEvent restEvent) {
        for(Iterator it = listeners.iterator(); it.hasNext();) {
            RESTExecutedListener listener = (RESTExecutedListener) it.next();
            listener.onRESTExecuted(restEvent);
        }
    }
    // </editor-fold>
    
}
