/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cimav.client.personal;

import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.cimav.client.domain.BaseProvider;
import org.cimav.client.domain.BaseREST;
import org.cimav.client.domain.Empleado;
import org.cimav.client.tools.ProviderEvent;
import org.cimav.client.tools.ProviderMethod;
import org.cimav.client.tools.RESTEvent;
import org.cimav.client.tools.TypeResult;

/**
 *
 * @author juan.calderon
 */
public class PersonalProvider extends BaseProvider<Empleado> {
    
    private static PersonalProvider instance;
    
    private PersonalREST personalREST;
    
    public static PersonalProvider get() {
        if (instance == null) {
            instance = new PersonalProvider();
        }
        return instance;
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
    
    public static final int ORDER_BY_NAME = 0;
    public static final int ORDER_BY_CODE = 1;
    public static final int ORDER_BY_GRUPO = 2;
    public static final int ORDER_BY_NIVEL = 3;
    
    @Override
    public void order(int orderBy) {
        switch(orderBy) {
            case ORDER_BY_NAME: {
                Collections.sort(dataProvider.getList(), new Comparator<Empleado>() {
                    @Override
                    public int compare(Empleado emp1, Empleado emp2) {
                         return  emp1.getName().compareTo(emp2.getName());
                    }
                });
                break;
            }
            case ORDER_BY_CODE: {
                Collections.sort(dataProvider.getList(), new Comparator<Empleado>() {
                    @Override
                    public int compare(Empleado emp1, Empleado emp2) {
                         return  emp1.getCode().compareTo(emp2.getCode());
                    }
                });
                break;
            }
            case ORDER_BY_GRUPO: {
                Collections.sort(dataProvider.getList(), new Comparator<Empleado>() {
                    @Override
                    public int compare(Empleado emp1, Empleado emp2) {
                        String grp1 = emp1.getGrupo() != null ? emp1.getGrupo().getCode() : "";
                        String grp2 = emp2.getGrupo() != null ? emp2.getGrupo().getCode() : "";
                        return grp1.compareTo(grp2);
                    }
                });
                break;
            }
            case ORDER_BY_NIVEL: {
                Collections.sort(dataProvider.getList(), new Comparator<Empleado>() {
                    @Override
                    public int compare(Empleado emp1, Empleado emp2) {
                        String niv1 = emp1.getNivel() != null ? emp1.getNivel().getCode() : "";
                        String niv2 = emp2.getNivel() != null ? emp2.getNivel().getCode() : "";
                        return niv1.compareTo(niv2);
                    }
                });
                break;
            }
        }
    }
    
    public PersonalREST getREST() {
        if (personalREST == null) {
            personalREST = new PersonalREST();
            
            personalREST.addRESTExecutedListener(new RestMethodExecutedListener());
        }
        return personalREST;
    }

    private class RestMethodExecutedListener implements BaseREST.RESTExecutedListener {

        @Override
        public void onRESTExecuted(RESTEvent dbEvent) {
            if (ProviderMethod.FIND_ALL.equals(dbEvent.getDbMethod())) {
                String m = "" + dataProvider.getDataDisplays().size() + "/" + dataProvider.getList().size();
                //reloadBtn.setText(m);

                dataProvider.getList().clear();
                
                ProviderEvent providerEvent = new ProviderEvent(ProviderMethod.FIND_ALL, TypeResult.SUCCESS, "");
                //providerEvent.setResult(dbEvent.getResult());
                onMethodExecuted(providerEvent);

                if (TypeResult.SUCCESS.equals(dbEvent.getDbTypeResult())) {
                    
                    List<Empleado> empleados = (List<Empleado>) dbEvent.getResult();
                    dataProvider.getList().addAll(empleados);
                    
//                    String msg = "Registros: " + PersonalDB.get().getDataProvider().getList().size();
//                    GrowlOptions go = GrowlHelper.getNewOptions();
//                    go.setSuccessType();
//                    go.setAllowDismiss(false);
//                    Growl.growl("", msg, Styles.FADE + " " + Styles.FONT_AWESOME_BASE /*+ " " + IconType.SMILE_O.getCssName()*/, go);

                } else {
//                    String msg = "Falló la carga de registros";
//                    GrowlOptions go = GrowlHelper.getNewOptions();
//                    go.setDangerType();
//                    go.setDelay(15000); // 15 segs
//                    //go.setAllowDismiss(false);
//                    Growl.growl("", msg, Styles.FONT_AWESOME_BASE /*+ " " + IconType.SMILE_O.getCssName()*/, go);
                }
            } else if (ProviderMethod.CREATE.equals(dbEvent.getDbMethod())) {
                ProviderEvent providerEvent = new ProviderEvent(ProviderMethod.CREATE, TypeResult.SUCCESS, dbEvent.getReason());
                //providerEvent.setResult(dbEvent.getResult());
                onMethodExecuted(providerEvent);
            }
        }
    }
    
    public void findAll() {
        this.getREST().findAll();
    }
    
    public void add(Empleado empleado) {
        this.getREST().add(empleado);
    }
}
