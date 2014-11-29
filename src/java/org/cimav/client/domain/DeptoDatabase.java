/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cimav.client.domain;

import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.ListDataProvider;
import java.util.HashMap;
import java.util.List;
import org.fusesource.restygwt.client.JsonCallback;
import org.fusesource.restygwt.client.JsonEncoderDecoder;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.Resource;

/**
 *
 * @author juan.calderon
 */
public class DeptoDatabase {

    private static final String  URL_REST = "http://localhost:8080/SigreRHFish/api/departamento";
    
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
        
        dpartamentoCodec = GWT.create( DepartamentoCodec.class );
    }

    public interface DepartamentoCodec extends JsonEncoderDecoder<Departamento> {}
    public DepartamentoCodec dpartamentoCodec;
    
    public void updateDepto(Departamento depto) {
        List<Departamento> deptos = dataProvider.getList();
        int idx = deptos.indexOf(depto);
        if (idx >= 0) {
            Departamento deptoInList = deptos.get(idx);
            deptoInList.setCodigo(depto.getCodigo());
            deptoInList.setNombre(depto.getNombre());
        }
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
        //headers.put(Resource.HEADER_ACCEPT, "application/json; charset=utf-8");
        headers.put(Resource.HEADER_CONTENT_TYPE, "application/json; charset=utf-8");
        
        Resource rb = new Resource(URL_REST + "/add", headers);
        
        rb.post().json(deptoJSONValue).send(new JsonCallback() {
            @Override
            public void onFailure(Method method, Throwable exception) {
                Window.alert("Error: "+exception);
            }
            @Override
            public void onSuccess(Method method, JSONValue response) {
                
                Departamento newDepto = dpartamentoCodec.decode(response);
                
                List<Departamento> deptos = dataProvider.getList();
                deptos.remove(newDepto); // en caso de que existiera, lo elimina
                System.out.println("Removed? " +  deptos.contains(newDepto) +  " ::: " + newDepto);
                deptos.add(newDepto); // lo agrega
            }
        });
        
    }

    public void removeDepto(final Departamento depto) {

        List<Departamento> deptos = dataProvider.getList();
        deptos.remove(depto);

//        Resource r = new Resource(URL_REST);
//        r.delete().send(new JsonCallback() {
//            @Override
//            public void onFailure(Method method, Throwable exception) {
//                Window.alert("Error: "+exception);
//            }
//            @Override
//            public void onSuccess(Method method, JSONValue response) {
//                List<Departamento> deptos = dataProvider.getList();
//                deptos.remove(depto);
//            }
//        });
        
    }

    /**
     * Add a display to the database. The current range of interest of the
     * display will be populated with data.
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

    public void load() {
        
        dataProvider.getList().clear();

        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put(Resource.HEADER_CONTENT_TYPE, "application/json; charset=utf-8");
        
        Resource rb = new Resource(URL_REST, headers);
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
                
                for (int i = 0; i < array.size(); ++i) {
                    JSONObject item = array.get(i).isObject();
                    Integer id = (int) item.get("id").isNumber().doubleValue();
                    String codigo = item.get("codigo").isString().stringValue();
                    String nombre = item.get("nombre").isString().stringValue();
                    Integer status = (int) item.get("status").isNumber().doubleValue();

                    Departamento depto = new Departamento(id, codigo, nombre, status);
                    
                    // Remove the contact first so we don't add a duplicate.
                    deptosProvider.remove(depto);
                    deptosProvider.add(depto);
                }
                
            }
        });

    }

}
