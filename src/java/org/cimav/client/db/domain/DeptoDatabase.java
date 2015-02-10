/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cimav.client.db.domain;

import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Window;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.ListDataProvider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.cimav.client.tools.Ajax;
import org.cimav.client.tools.DBEvent;
import org.cimav.client.tools.ProviderMethod;
import org.cimav.client.tools.TypeResult;
import org.cimav.client.tools.InfoView;
import org.fusesource.restygwt.client.Defaults;
import org.fusesource.restygwt.client.JsonCallback;
import org.fusesource.restygwt.client.JsonEncoderDecoder;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.Resource;

/**
 *
 * @author juan.calderon
 */
public class DeptoDatabase {

    private static final String URL_REST = Defaults.getServiceRoot() + "api/departamento"; //"http://localhost:8080/SigreRHFish/api/departamento";
    private static final String URL_REST_ALL = URL_REST + "";
    private static final String URL_REST_ADD = URL_REST + "/add";
    private static final String URL_REST_UPDATE = URL_REST + "/update";
    private static final String URL_REST_DELETE = URL_REST + "/delete";

    public static Departamento currentDepto;

    /**
     * The singleton instance of the database.
     */
    private static DeptoDatabase instance;

    /**
     * Get the singleton instance of the contact database.
     *
     * @return the singleton instance
     */
    public static DeptoDatabase get() {
        if (instance == null) {
            instance = new DeptoDatabase();
        }
        return instance;
    }

    /**
     * The provider that holds the list of contacts in the database.
     */
    private ListDataProvider<Departamento> dataProvider = new ListDataProvider<Departamento>();

    /**
     * Construct a new contact database.
     */
    private DeptoDatabase() {
        // Generate initial data.
        //generateContacts(250);

        dataProvider = new ListDataProvider<>();

        dpartamentoCodec = GWT.create(DepartamentoCodec.class);
    }

    public interface DepartamentoCodec extends JsonEncoderDecoder<Departamento> {
    }
    public DepartamentoCodec dpartamentoCodec;

    public void updateDepto(Departamento depto) {

        JSONValue deptoJSONValue = dpartamentoCodec.encode(depto);

        HashMap<String, String> headers = new HashMap<>();
        headers.put(Resource.HEADER_CONTENT_TYPE, "application/json; charset=utf-8");

        Resource rb = new Resource(URL_REST_UPDATE + "/" + depto.getId(), headers);

        rb.put().json(deptoJSONValue).send(Ajax.jsonCall(new JsonCallback() {
            @Override
            public void onFailure(Method method, Throwable exception) {
                Window.alert("Error: " + exception);
            }

            @Override
            public void onSuccess(Method method, JSONValue response) {

                Departamento deptoUpdated = dpartamentoCodec.decode(response);

                List<Departamento> deptos = dataProvider.getList();
                int idx = deptos.indexOf(deptoUpdated);
                if (idx >= 0) {
                    Departamento deptoInList = deptos.get(idx);
                    deptoInList.setCodigo(deptoUpdated.getCodigo());
                    deptoInList.setNombre(deptoUpdated.getNombre());
                }
            }
        }));

    }

    /**
     * Add a new contact.
     *
     * @param depto the DeptoInfo to add.
     */
    public void addDepto(Departamento depto) {

        //Create a PersonJsonizer instance
        //Departamento.DepartamentoJsonizer dj = (Departamento.DepartamentoJsonizer)GWT.create(Departamento.DepartamentoJsonizer.class);
        JSONValue deptoJSONValue = dpartamentoCodec.encode(depto);

        HashMap<String, String> headers = new HashMap<>();
        headers.put(Resource.HEADER_CONTENT_TYPE, "application/json; charset=utf-8");

        Resource rb = new Resource(URL_REST_ADD, headers);
        
        rb.post().json(deptoJSONValue).send(Ajax.jsonCall(new JsonCallback() {
            @Override
            public void onFailure(Method method, Throwable exception) {
                Window.alert("Error: " + exception);
            }

            @Override
            public void onSuccess(Method method, JSONValue response) {

                Departamento newDepto = dpartamentoCodec.decode(response);

                List<Departamento> deptos = dataProvider.getList();
                deptos.remove(newDepto); // en caso de que existiera, lo elimina
                deptos.add(newDepto); // lo agrega
            }
        }));

    }

    public void removeDepto(final Departamento depto) {

        Resource r = new Resource(URL_REST_DELETE + "/" + depto.getId()); //TODO Meter el Id en el Resource
        r.delete().send(Ajax.jsonCall(new JsonCallback() {
            @Override
            public void onFailure(Method method, Throwable exception) {
                Window.alert("Error: " + exception);
            }

            @Override
            public void onSuccess(Method method, JSONValue response) {
                List<Departamento> deptos = dataProvider.getList();
                deptos.remove(depto);
            }
        }));

    }

    /**
     * Add a display to the database. The current range of interest of the display will be populated with data.
     *
     * @param display a {@Link HasData}.
     */
    public void addDataDisplay(HasData<Departamento> display) {
        dataProvider.addDataDisplay(display);
    }

    public ListDataProvider<Departamento> getDataProvider() {
        return dataProvider;
    }

    /**
     * Refresh all displays.
     */
    public void refreshDisplays() {
        dataProvider.refresh();
    }


    public void extra() {
    
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put(Resource.HEADER_CONTENT_TYPE, "application/json; charset=utf-8");
        headers.put(Resource.HEADER_ACCEPT, "*/*");
//        headers.put("Access-Control-Allow-Headers", "Content-Type, authorization");
        
        String ulr = "http://icalderas.cimav.edu.mx:8080/RestRh/webresources/entities.empleado/567";
        //String ulr = "http://calderas.cimav.edu.mx:8080/rh-alpha/api/grupo";
        //String ulr = "http://localhost:8080/SigreRHFish/api/grupo";
        
        Resource rb = new Resource( ulr, headers);
        rb.get().send(Ajax.jsonCall(new JsonCallback() {
            @Override
            public void onFailure(Method method, Throwable exception) {
                Window.alert(exception.getLocalizedMessage());
                System.out.println("---Vacio--- ");
            }

            @Override
            public void onSuccess(Method method, JSONValue response) {

                System.out.println(">>>> " + response);
                
                InfoView.show(" .G.R.U.P.O.S. ");
            }
        }));

    }
    
    public void load() {

        dataProvider.getList().clear();

        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put(Resource.HEADER_CONTENT_TYPE, "application/json; charset=utf-8");

        Resource rb = new Resource(URL_REST, headers);
        rb.get().send(Ajax.jsonCall(new JsonCallback() {
            @Override
            public void onFailure(Method method, Throwable exception) {
                Window.alert(exception.getLocalizedMessage());
                System.out.println("---Vacio--- ");
            }

            @Override
            public void onSuccess(Method method, JSONValue response) {

                List<Departamento> deptosProvider = dataProvider.getList();

                JSONArray array = null;
                if (response instanceof JSONObject) {
                    JSONObject obj = (JSONObject) response;
                    array = obj.get("departamento").isArray(); //TODO que el elemento-root se llame 'Departamento'
                } else if (response instanceof JSONArray) {
                    array = response.isArray();
                } else {
                    throw new NullPointerException("EL arreglo de Departamentos es Nulo en: " + URL_REST);
                }

                for (int i = 0; i < array.size(); i++) {
                    JSONObject item = array.get(i).isObject();

                    Integer id = (int) item.get("id").isNumber().doubleValue();

                    String codigo = item.get("codigo") != null ? item.get("codigo").isString().stringValue() : "NO_COD_" + Random.nextInt(100000);
                    String nombre = item.get("nombre") != null ? item.get("nombre").isString().stringValue() : "NO_NOM_" + Random.nextInt(100000);
                    Integer status = item.get("status") != null ? (int) item.get("status").isNumber().doubleValue() : -1;

                    Departamento depto = new Departamento(id, codigo, nombre, status);

                    // Remove the contact first so we don't add a duplicate.
                    deptosProvider.remove(depto);
                    deptosProvider.add(depto);
                }
                
                InfoView.show( array.size() + " registros recargados");
            }
        }));

        /*
         rb.get().send(new JsonCallback() {
         @Override
         public void onFailure(Method method, Throwable exception) {
         Window.alert(exception.getLocalizedMessage());
         System.out.println("---Vacio--- ");
         }

         @Override
         public void onSuccess(Method method, JSONValue response) {
                                
         List<Departamento> deptosProvider = dataProvider.getList();

         JSONArray array = null;
         if (response instanceof JSONObject) {
         JSONObject obj = (JSONObject) response;
         array = obj.get("departamento").isArray(); //TODO que el elemento-root se llame 'Departamento'
         } else if (response instanceof JSONArray) {
         array = response.isArray();
         } else {
         throw new NullPointerException("EL arreglo de Departamentos es Nulo en: " + URL_REST);
         }
                
         for (int i = 0; i < array.size(); i++) {
         JSONObject item = array.get(i).isObject();
                    
         Integer id = (int) item.get("id").isNumber().doubleValue();
                    
         String codigo = item.get("codigo") != null ? item.get("codigo").isString().stringValue() : "NO_COD_" +  Random.nextInt(100000);
         String nombre = item.get("nombre") != null ? item.get("nombre").isString().stringValue() : "NO_NOM_"  + Random.nextInt(100000);
         Integer status = item.get("status") != null ? (int) item.get("status").isNumber().doubleValue() : -1;

         Departamento depto = new Departamento(id, codigo, nombre, status);
                    
         // Remove the contact first so we don't add a duplicate.
         deptosProvider.remove(depto);
         deptosProvider.add(depto);
         }
                
         }
         });
         */
    }

    // <editor-fold defaultstate="collapsed" desc="métodos CRUD-REST"> 
    
    public interface DepartamentoJsonCodec extends JsonEncoderDecoder<Departamento> {}
    public DepartamentoJsonCodec departamentoListJsonCodec = GWT.create(DepartamentoJsonCodec.class);
    
    public void findAll() {
                
        //dataProvider.getList().clear();

        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put(Resource.HEADER_CONTENT_TYPE, "application/json; charset=utf-8");

        Resource rb = new Resource(URL_REST, headers);
        rb.get().send(Ajax.jsonCall(new JsonCallback() {

            @Override
            public void onFailure(Method method, Throwable exception) {
                Window.alert(exception.getLocalizedMessage());
                
                DBEvent dbEvent = new DBEvent(ProviderMethod.FIND_ALL, TypeResult.FAILURE, exception.getMessage());
                onMethodExecuted(dbEvent);
            }

            @Override
            public void onSuccess(Method method, JSONValue response) {
                // TODO debería poderse con List<Empleado>
                // Pasar la lista completa. Probablemente si se soluciona el XmlRootElement name="empleados"
                
                List<Departamento> deptos = new ArrayList<>();
                
                JSONArray array = response.isArray();
                for (int i=0;i<array.size();i++) {
                    JSONValue val = array.get(i);
                    Departamento depto = departamentoListJsonCodec.decode(val);
                    deptos.add(depto);
                }
                
                DBEvent dbEvent = new DBEvent(ProviderMethod.FIND_ALL, TypeResult.SUCCESS, "");
                dbEvent.setResult(deptos);
                onMethodExecuted(dbEvent); //TODO onMethodExecuted(dbEvent) hacerlo con fire.
            }
            
        }));

    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="interface MethodExecutedListener"> 
    public interface MethodExecutedListener extends java.util.EventListener {
        void onMethodExecuted(DBEvent dbEvent);
    }
    private final ArrayList listeners = new ArrayList();
    public void addMethodExecutedListener(MethodExecutedListener listener) {
        listeners.add(listener);
    }
    public void removeMethodExecutedListener(MethodExecutedListener listener) {
        listeners.remove(listener);
    }
    public void onMethodExecuted(DBEvent dbEvent) {
        for(Iterator it = listeners.iterator(); it.hasNext();) {
            MethodExecutedListener listener = (MethodExecutedListener) it.next();
            listener.onMethodExecuted(dbEvent);
        }
    }
    // </editor-fold>
    
}
