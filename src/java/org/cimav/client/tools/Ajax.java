/*
 * Copyright 2013 juan.calderon.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.cimav.client.tools;

import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.ArrayList;
import java.util.List;
import org.fusesource.restygwt.client.JsonCallback;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

/**
 *
 * @author juan.calderon
 */
public class Ajax {
    private static final AppLoadingView loadingIndicator = new AppLoadingView();
    private static final List<AsyncCallback<?>> callstack = new ArrayList<AsyncCallback<?>>();

    public static <T> AsyncCallback<T> call(final AsyncCallback<T> callback) {
        if(!loadingIndicator.isShowing()){
            loadingIndicator.center();
        }
        callstack.add(callback);
        return new AsyncCallback<T>() {
            @Override
            public void onFailure(Throwable caught) {
                handleReturn();
                callback.onFailure(caught);
            }

            @Override
            public void onSuccess(T result) {
                handleReturn();
                callback.onSuccess(result);
            }

            private void handleReturn(){
                callstack.remove(callback);
                if(callstack.size() < 1) {
                    loadingIndicator.hide();
                }
            }
        };
    }
    
    private static final List<JsonCallback> callstack2 = new ArrayList<JsonCallback>();
    public static JsonCallback enviar(final JsonCallback back) {
        if(!loadingIndicator.isShowing()){
            loadingIndicator.center();
        }
        callstack2.add(back);
        return new JsonCallback() {

            @Override
            public void onFailure(Method method, Throwable exception) {
                handleReturn();
                back.onFailure(method, exception);
            }

            @Override
            public void onSuccess(Method method, JSONValue response) {
                handleReturn();
                back.onSuccess(method, response);
            }
            private void handleReturn(){
                callstack2.remove(back);
                if(callstack2.size() < 1) {
                    loadingIndicator.hide();
                }
            }
        };
    }
    
}
