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
import com.google.gwt.user.client.Random;
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
public class TabuladorDatabase {
    
    private static final String  URL_REST = "http://localhost:8080/SigreRHFish/api/tabulador";
    
    public static Departamento currentDepto;
    
    /**
     * The singleton instance of the database.
     */
    private static TabuladorDatabase instance;

    /**
     * Get the singleton instance of the contact database.
     *
     * @return the singleton instance
     */
    public static TabuladorDatabase get() {
        if (instance == null) {
            instance = new TabuladorDatabase();
        }
        return instance;
    }

    /**
     * The provider that holds the list of contacts in the database.
     */
    private ListDataProvider<Tabulador> dataProvider = new ListDataProvider<Tabulador>();

    /**
     * Construct a new contact database.
     */
    private TabuladorDatabase() {
        // Generate initial data.
        //generateContacts(250);

        dataProvider = new ListDataProvider<>();
        
        dpartamentoCodec = GWT.create( TabuladorCodec.class );
    }

    public interface TabuladorCodec extends JsonEncoderDecoder<Tabulador> {}
    public TabuladorCodec dpartamentoCodec;
    
    public void updateDepto(Tabulador tabulador) {
        
        JSONValue tabuladorJSONValue = dpartamentoCodec.encode(tabulador); 
        
        HashMap<String, String> headers = new HashMap<>();
        headers.put(Resource.HEADER_CONTENT_TYPE, "application/json; charset=utf-8");
        
        Resource rb = new Resource(URL_REST + "/" + tabulador.getId(), headers);
        
        rb.put().json(tabuladorJSONValue).send(new JsonCallback() {
            @Override
            public void onFailure(Method method, Throwable exception) {
                Window.alert("Error: "+exception);
            }
            @Override
            public void onSuccess(Method method, JSONValue response) {
                
                Tabulador tabuladorUpdated = dpartamentoCodec.decode(response);

                List<Tabulador> tabuladors = dataProvider.getList();
                int idx = tabuladors.indexOf(tabuladorUpdated);
                if (idx >= 0) {
                    Tabulador tabuladorInList = tabuladors.get(idx);
                    tabuladorInList.setCode(tabuladorUpdated.getCode());
                    tabuladorInList.setName(tabuladorUpdated.getName());
                }
            }
        });
        
        
    }
    
    /**
     * Add a new contact.
     *
     * @param tabulador the DeptoInfo to add.
     */
    public void addDepto(Tabulador tabulador) {

        //Create a PersonJsonizer instance
        //Tabulador.TabuladorJsonizer dj = (Tabulador.TabuladorJsonizer)GWT.create(Tabulador.TabuladorJsonizer.class);
        
        JSONValue tabuladorJSONValue = dpartamentoCodec.encode(tabulador); 
        
        HashMap<String, String> headers = new HashMap<>();
        headers.put(Resource.HEADER_CONTENT_TYPE, "application/json; charset=utf-8");
        
        Resource rb = new Resource(URL_REST, headers);
        
        rb.post().json(tabuladorJSONValue).send(new JsonCallback() {
            @Override
            public void onFailure(Method method, Throwable exception) {
                Window.alert("Error: "+exception);
            }
            @Override
            public void onSuccess(Method method, JSONValue response) {
                
                Tabulador newDepto = dpartamentoCodec.decode(response);
                
                List<Tabulador> tabuladors = dataProvider.getList();
                tabuladors.remove(newDepto); // en caso de que existiera, lo elimina
                tabuladors.add(newDepto); // lo agrega
            }
        });
        
    }

    public void removeDepto(final Tabulador tabulador) {

        Resource r = new Resource(URL_REST + "/" + tabulador.getId()); //TODO Meter el Id en el Resource
        r.delete().send(new JsonCallback() {
            @Override
            public void onFailure(Method method, Throwable exception) {
                Window.alert("Error: "+exception);
            }
            @Override
            public void onSuccess(Method method, JSONValue response) {
                List<Tabulador> tabuladors = dataProvider.getList();
                tabuladors.remove(tabulador);
            }
        });
        
    }

    /**
     * Add a display to the database. The current range of interest of the
     * display will be populated with data.
     *
     * @param display a {@Link HasData}.
     */
    public void addDataDisplay(HasData<Tabulador> display) {
        dataProvider.addDataDisplay(display);
    }

    public ListDataProvider<Tabulador> getDataProvider() {
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
                                
                List<Tabulador> tabuladorsProvider = dataProvider.getList();

                JSONArray array = null;
                if (response instanceof JSONObject) {
                    JSONObject obj = (JSONObject) response;
                    array = obj.get("tabulador").isArray(); //TODO que el elemento-root se llame 'Tabulador'
                } else if (response instanceof JSONArray) {
                    array = response.isArray();
                } else {
                    throw new NullPointerException("EL arreglo de Tabuladors es Nulo en: " + URL_REST);
                }
                
                for (int i = 0; i < array.size(); i++) {
                    JSONObject item = array.get(i).isObject();
                    
                    Integer id = (int) item.get("id").isNumber().doubleValue();
                    
                    String nivel = item.get("nivel") != null ? item.get("nivel").isString().stringValue() : "NO_NIV_" +  Random.nextInt(100000);
                    String nombre = item.get("nombre") != null ? item.get("nombre").isString().stringValue() : "NO_NOM_"  + Random.nextInt(100000);

                    Tabulador tabulador = new Tabulador();
                    tabulador.setId(id);
                    tabulador.setCode(nivel);
                    tabulador.setName(nombre);
                    
                    // Remove the contact first so we don't add a duplicate.
                    tabuladorsProvider.remove(tabulador);
                    tabuladorsProvider.add(tabulador);
                }
                
            }
        });

    }
    
}
