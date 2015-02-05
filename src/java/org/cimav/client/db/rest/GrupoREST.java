/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cimav.client.db.rest;

import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.cimav.client.db.domain.Grupo;
import org.cimav.client.tools.Ajax;
import org.cimav.client.tools.ProviderMethod;
import org.cimav.client.tools.RESTEvent;
import org.cimav.client.tools.TypeResult;
import org.fusesource.restygwt.client.Defaults;
import org.fusesource.restygwt.client.JsonCallback;
import org.fusesource.restygwt.client.JsonEncoderDecoder;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.Resource;

/**
 *
 * @author juan.calderon
 */
public class GrupoREST extends BaseREST {
    
    public interface JsonCodec extends JsonEncoderDecoder<Grupo> {
    }
    
    public JsonEncoderDecoder jsonCodec = GWT.create(JsonCodec.class);
    
    private static final String URL_REST = Defaults.getServiceRoot() + "api/grupo"; //"http://localhost:8080/SigreRHFish/api/grupo";
    
    public void findAllBase() {

        org.fusesource.restygwt.client.Defaults.setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ"); 
         
        HashMap<String, String> headers = new HashMap<>();
        headers.put(Resource.HEADER_CONTENT_TYPE, "application/json; charset=utf-8");

        Resource rb = new Resource(URL_REST + "/base", headers);
        rb.get().send(Ajax.jsonCall(new JsonCallback() {

            @Override
            public void onFailure(Method method, Throwable exception) {
                Window.alert(exception.getLocalizedMessage());

                RESTEvent dbEvent = new RESTEvent(ProviderMethod.FIND_ALL_BASE, TypeResult.FAILURE, exception.getMessage());
                onRESTExecuted(dbEvent);
            }

            @Override
            public void onSuccess(Method method, JSONValue response) {
                List<Grupo> grupos = new ArrayList<>();
                try {
                    JSONArray array = response.isArray();
                    for (int i = 0; i < array.size(); i++) {
                        JSONValue val = array.get(i);
                        
                        Grupo grupo  = (Grupo) jsonCodec.decode(val);
                        grupos.add(grupo);
                    }
                    RESTEvent dbEvent = new RESTEvent(ProviderMethod.FIND_ALL_BASE, TypeResult.SUCCESS, "");
                    dbEvent.setResult(grupos);
                    onRESTExecuted(dbEvent);
                } catch (Exception e) {
                    RESTEvent dbEvent = new RESTEvent(ProviderMethod.FIND_ALL_BASE, TypeResult.FAILURE, e.getMessage());
                    onRESTExecuted(dbEvent);
                }
            }

        }));

    }
    
}
