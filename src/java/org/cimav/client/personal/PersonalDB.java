/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cimav.client.personal;

import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.user.client.Window;
import com.google.gwt.view.client.HasData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.cimav.client.domain.Empleado;
import org.cimav.client.tools.Ajax;
import org.cimav.client.tools.DBEvent;
import org.cimav.client.tools.DBMethod;
import org.cimav.client.tools.DBTypeResult;
import org.cimav.client.tools.FilteredListDataProvider;
import org.cimav.client.tools.IFilter;
import org.fusesource.restygwt.client.JsonCallback;
import org.fusesource.restygwt.client.JsonEncoderDecoder;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.Resource;

/**
 *
 * @author juan.calderon
 */
public class PersonalDB implements IFilter<Empleado> {

    private static final String URL_REST = "http://localhost:8080/SigreRHFish/api/empleado";

    /**
     * The singleton instance of the database.
     */
    private static PersonalDB instance;

    /**
     * The provider that holds the list of contacts in the database.
     */
    public FilteredListDataProvider<Empleado> dataProvider = new FilteredListDataProvider<>(this);
    
    public interface EmpleadoJsonCodec extends JsonEncoderDecoder<Empleado> {}
    public EmpleadoJsonCodec empleadoListJsonCodec = GWT.create(EmpleadoJsonCodec.class);
    
    /**
     * Se crea una sola vez en todo el ciclo de vida de la aplicacion.
     * Por eso, cuando se re-entra ya esta cargada la Lista (sin filtrar).
     * 
     * @return PersonalDB singleton
     */
    public static PersonalDB get() {
        if (instance == null) {
            instance = new PersonalDB();
        }
        return instance;
    }

    private PersonalDB() {
    }

    @Override
    public boolean matchFilter(Empleado value, String filter) {
        if (value == null) {
            return false;
        }
        if (filter == null || filter.trim().isEmpty()) {
            return true;
        }
        
        // ^.*\b(one|two|three)\b.*$    one, tow or three
        // ^(?=.*?\bone\b)(?=.*?\btwo\b)(?=.*?\bthree\b).*$ one, two AND three
        
        // ^(?=.*?one)(?=.*?two)(?=.*?three).*$ las palabras no tiene boundiry
        
        // la frase completa debe tener todos los terminos
        
        String pattern = "^";
        filter = filter.toLowerCase();
        String[] array = filter.split("\\s+");
        for (String term : array) {
            pattern = pattern + "(?=.*?" + term.trim() + ")";
        }
        pattern = pattern + ".+";
        
        String grupoStr = value.getGrupo() != null ? value.getGrupo().getCode() + " " + value.getGrupo().getName() : "";
        String nivelStr = value.getNivel() != null ? value.getNivel().getCode() + " " + value.getNivel().getName() : "";
        String string = (value.getCode() + " " + value.getUrlPhoto() + " " + grupoStr.toLowerCase() + " " + nivelStr.toLowerCase());
        
        RegExp regExp = RegExp.compile(pattern);
        MatchResult matcher = regExp.exec(string);
        
        return matcher != null;
    }
    
    /**
     * Add a display to the database. The current range of interest of the display will be populated with data.
     *
     * @param display a {@Link HasData}.
     */
    public void addDataDisplay(HasData<Empleado> display) {
        dataProvider.addDataDisplay(display);
        this.display = display;
    }
    private HasData<Empleado> display;
//    private HasData<Empleado> getDataDisplay() {
//        return (HasData<Empleado>) dataProvider.getDataDisplays();
//    }

    public FilteredListDataProvider<Empleado> getDataProvider() {
        return dataProvider;
    }

    public int getRowCount() {
        if (this.display == null) {
            return dataProvider.getList().size();
        } else {
            return this.display.getRowCount();
        }
    }

    /**
     * Refresh all displays.
     */
    public void refreshDisplays() {
        dataProvider.refresh();
    }
    
    // <editor-fold defaultstate="collapsed" desc="métodos CRUD-REST"> 
    public void findAll() {
                
        //dataProvider.getList().clear();

        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put(Resource.HEADER_CONTENT_TYPE, "application/json; charset=utf-8");

        Resource rb = new Resource(URL_REST, headers);
        rb.get().send(Ajax.jsonCall(new JsonCallback() {

            @Override
            public void onFailure(Method method, Throwable exception) {
                Window.alert(exception.getLocalizedMessage());
                
                DBEvent dbEvent = new DBEvent(DBMethod.FIND_ALL, DBTypeResult.FAILURE, exception.getMessage());
                onMethodExecuted(dbEvent);
            }

            @Override
            public void onSuccess(Method method, JSONValue response) {
                // TODO debería poderse con List<Empleado>
                // Pasar la lista completa. Probablemente si se soluciona el XmlRootElement name="empleados"
                
                dataProvider.getList().clear();
                List<Empleado> deptosProvider = dataProvider.getList();
                
                JSONArray array = response.isArray();
                for (int i=0;i<array.size();i++) {
                    JSONValue val = array.get(i);
                    Empleado empleado = empleadoListJsonCodec.decode(val);
                    deptosProvider.add(empleado);
                }
                
                DBEvent dbEvent = new DBEvent(DBMethod.FIND_ALL, DBTypeResult.SUCCESS, "");
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
