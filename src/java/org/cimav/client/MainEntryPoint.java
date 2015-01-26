/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cimav.client;

import org.cimav.client.departamentos.DepartamentosUI;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.UmbrellaException;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import javax.validation.constraints.NotNull;
import org.cimav.client.personal.PersonalUI;

import org.fusesource.restygwt.client.Defaults;

/**
 * Main entry point.
 *
 * @author juan.calderon
 */
public class MainEntryPoint implements EntryPoint {

    static {

        /* Default initialize */
        Defaults.setServiceRoot(GWT.getHostPageBaseURL());
        String DATEFORMAT = "yyyy-mm-dd'T'HH:mm:ss.SSSZ";
        //2015-01-14T00:00:00-07:00
        //Defaults.setDateFormat("yyyy-mm-dd'T'HH:mm:ss.SSSZ"); //
        Defaults.setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ"); //2015-01-14T00:00:00-0700
        //Defaults.setDateFormat(null);   // // // "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"
//        Defaults.setDateFormat("yyyy-MM-dd'T'HH:mm:ssX"); // "yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        //ISO8601DateFormat df = new ISO8601DateFormat();
//        Defaults.setDateFormat(  "yyyy-MM-dd'T'HH:mm:ssXXX");
//        Defaults.setDateFormat(  "yyyy-MM-dd");
//
//        Defaults.setDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        //2015-01-16T00:00:00-07:00
        
        //Defaults.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"); 


    }

    // Vistas UI
    private final MainUI mainUi;
    private PersonalUI personalUI;
    private DepartamentosUI departamentosUI;

    /**
     * Creates a new instance of MainEntryPoint
     */
    public MainEntryPoint() {
        mainUi = new MainUI();
    }

    /**
     * The entry point method, called automatically by loading a module that declares an implementing class as an entry-point
     */
    @Override
    public void onModuleLoad() {

//        /* Default initialize */
//        Defaults.setServiceRoot(GWT.getHostPageBaseURL());
//        Defaults.setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
            @Override
            public void onUncaughtException(@NotNull Throwable e) {
                ensureNotUmbrellaError(e);
            }
        });

        //RootPanel.get("gwtContainer").add(mainUiBinder);
        RootLayoutPanel.get().add(mainUi);

        mainUi.addOptionMenuChangeListener(new MainUI.OptionMenuChangeListener() {

            @Override
            public void onOptionMenuChange(String option) {
                switch (option) {
                    case MainUI.OPT_PERSONAL:
                        if (personalUI == null) {
                            // actua como Singleton en el ciclo de vida
                            personalUI = new PersonalUI();
                        }
                        mainUi.setCenterPanel("Personal", "Consultas, altas, bajas y cambios", personalUI);
                        break;
                    case MainUI.OPT_DEPARTAMENTOS:
                        if (departamentosUI == null) {
                            departamentosUI = new DepartamentosUI();
                        }
                        mainUi.setCenterPanel("Departamentos", "Consultas, altas, bajas y cambios", departamentosUI);
                        break;
                    default:
                        mainUi.setCenterPanel(option, "Not Yet Implemented...", null);
                        break;
                }

            }
        });

    }

    private static void ensureNotUmbrellaError(@NotNull Throwable e) {
        for (Throwable th : ((UmbrellaException) e).getCauses()) {
            if (th instanceof UmbrellaException) {
                ensureNotUmbrellaError(th);
            } else {
                System.err.println(th);
            }
        }
    }

}
