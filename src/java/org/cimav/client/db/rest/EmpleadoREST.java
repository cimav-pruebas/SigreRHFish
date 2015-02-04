/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cimav.client.db.rest;

import com.google.gwt.core.client.Duration;
import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.cimav.client.db.domain.Empleado;
import org.cimav.client.db.domain.Empleados;
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
public class EmpleadoREST extends BaseREST {

    private static final String URL_REST = "http://localhost:8080/SigreRHFish/api/empleado";

    public interface EmpleadoJsonCodec extends JsonEncoderDecoder<Empleado> {
    }
    public EmpleadoJsonCodec empleadoJsonCodec = GWT.create(EmpleadoJsonCodec.class);

    public interface EmpleadoListJsonCodec extends JsonEncoderDecoder<Empleados> {
    }
    public EmpleadoListJsonCodec empleadoListJsonCodec = GWT.create(EmpleadoListJsonCodec.class);

    // <editor-fold defaultstate="collapsed" desc="métodos CRUD-REST"> 
    public void findById(Integer id) {

        HashMap<String, String> headers = new HashMap<>();
        headers.put(Resource.HEADER_CONTENT_TYPE, "application/json; charset=utf-8");

        Resource rb = new Resource(URL_REST + "/" + id, headers);
        rb.get().send(Ajax.jsonCall(new JsonCallback() {

            @Override
            public void onFailure(Method method, Throwable exception) {
                Window.alert(exception.getLocalizedMessage());

                RESTEvent dbEvent = new RESTEvent(ProviderMethod.FIND_BY_ID, TypeResult.FAILURE, exception.getMessage());
                onRESTExecuted(dbEvent);
            }

            @Override
            public void onSuccess(Method method, JSONValue response) {

                try {
                    Empleado empleado = empleadoJsonCodec.decode(response);
                    RESTEvent dbEvent = new RESTEvent(ProviderMethod.FIND_BY_ID, TypeResult.SUCCESS, "");
                    dbEvent.setResult(empleado);
                    onRESTExecuted(dbEvent);
                } catch (Exception e) {
                    RESTEvent dbEvent = new RESTEvent(ProviderMethod.FIND_BY_ID, TypeResult.FAILURE, e.getMessage());
                    onRESTExecuted(dbEvent);
                }
            }

        }));

    }

    private Duration duration;

    public void findAll() {

        org.fusesource.restygwt.client.Defaults.setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

        HashMap<String, String> headers = new HashMap<>();
        headers.put(Resource.HEADER_CONTENT_TYPE, "application/json; charset=utf-8");

        Resource rb = new Resource(URL_REST, headers);

        duration = new Duration();

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
                try {
                    List<Empleado> empleados = new ArrayList<>();

//                    //              JSONArray array2 = null;
//                    if (response instanceof JSONObject) {
//                        JSONObject obj = (JSONObject) response;
//
                System.out.println("  onSuccess(Method method, JSONValue response) UNO >>> " + duration.elapsedMillis());
//                        Empleados listaEmpleado = empleadoListJsonCodec.decode(response);
//                    System.out.println(" JsonCodec.DECODE(JSONValue)) >>> DOS " + duration.elapsedMillis());
//                        empleados.addAll(listaEmpleado.getEmpleados());
//
////                    array2 = obj.get("empleados").isArray(); //TODO que el elemento-root se llame 'Departamento'
//                    } else if (response instanceof JSONArray) {
//                        //                 array2 = response.isArray();
//                    } else {
//                        throw new NullPointerException("EL arreglo de Departamentos es Nulo en: " + URL_REST);
//                    }
     //           System.out.println("arra>>> " + array2);

//                List<Empleado> empleados = new ArrayList<>();                
//                try {
                    JSONArray array = response.isArray();
                    for (int i = 0; i < array.size(); i++) {
                        JSONValue val = array.get(i);
                        
                        EmpleadoJsonCodec theDecode = GWT.create(EmpleadoJsonCodec.class);
                        Empleado empleado = theDecode.decode(val);
                        empleados.add(empleado);
                    }

                    RESTEvent dbEvent = new RESTEvent(ProviderMethod.FIND_ALL, TypeResult.SUCCESS, "");
                    dbEvent.setResult(empleados);
                    onRESTExecuted(dbEvent);

                    System.out.println(" New RESTEvent FIND_ALL DOS >>> " + duration.elapsedMillis());

                } catch (Exception e) {
                    RESTEvent dbEvent = new RESTEvent(ProviderMethod.FIND_ALL, TypeResult.FAILURE, e.getMessage());
                    onRESTExecuted(dbEvent);
                }
            }

        }));

    }

    public void add(Empleado empleado) {

        org.fusesource.restygwt.client.Defaults.setDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");

        JSONValue empleadoJSONValue = empleadoJsonCodec.encode(empleado);

        HashMap<String, String> headers = new HashMap<>();
        headers.put(Resource.HEADER_CONTENT_TYPE, "application/json; charset=utf-8");

        Resource rb = new Resource(URL_REST, headers);

        rb.post().json(empleadoJSONValue).send(Ajax.jsonCall(new JsonCallback() {
            @Override
            public void onFailure(Method method, Throwable exception) {
                RESTEvent dbEvent = new RESTEvent(ProviderMethod.CREATE, TypeResult.SUCCESS, exception.getMessage());
                onRESTExecuted(dbEvent);
            }

            @Override
            public void onSuccess(Method method, JSONValue response) {
                RESTEvent dbEvent = new RESTEvent(ProviderMethod.CREATE, TypeResult.SUCCESS, "listo");
                Empleado nuevoEmpleado = empleadoJsonCodec.decode(response);
                dbEvent.setResult(nuevoEmpleado);
                onRESTExecuted(dbEvent);
            }
        }));

    }

    public void update(Empleado empleado) {

        //TODO encode/decode   "yyyy-MM-dd'T'HH:mm:ssXXX"/"yyyy-MM-dd'T'HH:mm:ssZ"
        org.fusesource.restygwt.client.Defaults.setDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");

        //Create a PersonJsonizer instance
        //Departamento.DepartamentoJsonizer dj = (Departamento.DepartamentoJsonizer)GWT.create(Departamento.DepartamentoJsonizer.class);
        JSONValue empleadoJSONValue = empleadoJsonCodec.encode(empleado);

        HashMap<String, String> headers = new HashMap<>();
        headers.put(Resource.HEADER_CONTENT_TYPE, "application/json; charset=utf-8");

        Resource rb = new Resource(URL_REST, headers);

        rb.put().json(empleadoJSONValue).send(Ajax.jsonCall(new JsonCallback() {
            @Override
            public void onFailure(Method method, Throwable exception) {
                RESTEvent dbEvent = new RESTEvent(ProviderMethod.UPDATE, TypeResult.FAILURE, exception.getMessage());
                onRESTExecuted(dbEvent);

                Window.alert("Error: " + exception);
            }

            @Override
            public void onSuccess(Method method, JSONValue response) {
                RESTEvent dbEvent = new RESTEvent(ProviderMethod.UPDATE, TypeResult.SUCCESS, "listo");
                onRESTExecuted(dbEvent);

                System.out.println(">>> " + response);
            }
        }));

    }

    public void delete(Integer id) {

        HashMap<String, String> headers = new HashMap<>();
        headers.put(Resource.HEADER_CONTENT_TYPE, "application/json; charset=utf-8");

        Resource rb = new Resource(URL_REST + "/" + id, headers);

        rb.delete().send(Ajax.jsonCall(new JsonCallback() {
            @Override
            public void onFailure(Method method, Throwable exception) {
                Window.alert("Error: " + exception);
            }

            @Override
            public void onSuccess(Method method, JSONValue response) {
                RESTEvent dbEvent = new RESTEvent(ProviderMethod.DELETE, TypeResult.SUCCESS, "listo");
                onRESTExecuted(dbEvent);

                System.out.println(">>> " + response);
            }
        }));

    }

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
                List<Empleado> empleados = new ArrayList<>();
                try {
                    JSONArray array = response.isArray();
                    for (int i = 0; i < array.size(); i++) {
                        JSONValue val = array.get(i);

                        Empleado empleado = empleadoJsonCodec.decode(val);
                        empleados.add(empleado);
                    }
                    RESTEvent dbEvent = new RESTEvent(ProviderMethod.FIND_ALL_BASE, TypeResult.SUCCESS, "");
                    dbEvent.setResult(empleados);
                    onRESTExecuted(dbEvent);
                } catch (Exception e) {
                    RESTEvent dbEvent = new RESTEvent(ProviderMethod.FIND_ALL_BASE, TypeResult.FAILURE, e.getMessage());
                    onRESTExecuted(dbEvent);
                }
            }

        }));

    }

    // </editor-fold>
}
