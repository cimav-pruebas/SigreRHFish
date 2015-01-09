/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cimav.client.personal;

import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.cimav.client.domain.BaseREST;
import org.cimav.client.domain.Empleado;
import org.cimav.client.tools.Ajax;
import org.cimav.client.tools.ProviderMethod;
import org.cimav.client.tools.RESTEvent;
import org.cimav.client.tools.TypeResult;
import org.fusesource.restygwt.client.JsonCallback;
import org.fusesource.restygwt.client.JsonEncoderDecoder;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.Resource;

/**
 *
 * @author juan.calderon
 */
public class PersonalREST extends BaseREST {
    
    private static final String URL_REST = "http://localhost:8080/SigreRHFish/api/empleado";

    public interface EmpleadoJsonCodec extends JsonEncoderDecoder<Empleado> {}
    public EmpleadoJsonCodec empleadoListJsonCodec = GWT.create(EmpleadoJsonCodec.class);
    
    // <editor-fold defaultstate="collapsed" desc="métodos CRUD-REST"> 
    public void findAll() {
                
        //dataProvider.getList().clear();

        HashMap<String, String> headers = new HashMap<>();
        headers.put(Resource.HEADER_CONTENT_TYPE, "application/json; charset=utf-8");

        Resource rb = new Resource(URL_REST, headers);
        rb.get().send(Ajax.jsonCall(new JsonCallback() {

            @Override
            public void onFailure(Method method, Throwable exception) {
                Window.alert(exception.getLocalizedMessage());
                
                RESTEvent dbEvent = new RESTEvent(ProviderMethod.FIND_ALL, TypeResult.FAILURE, exception.getMessage());
                onRESTExecuted(dbEvent);
            }

            @Override
            public void onSuccess(Method method, JSONValue response) {
                // TODO debería poderse con List<Empleado>
                // Pasar la lista completa. Probablemente si se soluciona el XmlRootElement name="empleados"
                
                List<Empleado> empleados = new ArrayList<>();
                
                JSONArray array = response.isArray();
                for (int i=0;i<array.size();i++) {
                    JSONValue val = array.get(i);
                    Empleado empleado = empleadoListJsonCodec.decode(val);
                    empleados.add(empleado);
                }
                RESTEvent dbEvent = new RESTEvent(ProviderMethod.FIND_ALL, TypeResult.SUCCESS, "");
                dbEvent.setResult(empleados);
                onRESTExecuted(dbEvent); 
            }
            
        }));

    }
    
    public void add(Empleado empleado) {

        //Create a PersonJsonizer instance
        //Departamento.DepartamentoJsonizer dj = (Departamento.DepartamentoJsonizer)GWT.create(Departamento.DepartamentoJsonizer.class);
        JSONValue empleadoJSONValue = empleadoListJsonCodec.encode(empleado);

        HashMap<String, String> headers = new HashMap<>();
        headers.put(Resource.HEADER_CONTENT_TYPE, "application/json; charset=utf-8");

        Resource rb = new Resource(URL_REST, headers);
        
        rb.post().json(empleadoJSONValue).send(Ajax.jsonCall(new JsonCallback() {
            @Override
            public void onFailure(Method method, Throwable exception) {
                Window.alert("Error: " + exception);
            }

            @Override
            public void onSuccess(Method method, JSONValue response) {
                RESTEvent dbEvent = new RESTEvent(ProviderMethod.CREATE, TypeResult.SUCCESS, "listo");
                onRESTExecuted(dbEvent); 

                System.out.println(">>> " + response);
            }
        }));

    }    
    // </editor-fold>
    
}