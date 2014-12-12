/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cimav.client.tools;

/**
 *
 * @author juan.calderon
 */
public class DBEvent {
    
    private DBMethod dbMethod;
    private DBTypeResult dbTypeResult;
    private String reason;

    public DBEvent(DBMethod dbMethod, DBTypeResult dBTypeResult, String reason) {
        this.dbMethod = dbMethod;
        this.dbTypeResult = dBTypeResult;
        this.reason = reason;
    }

    public DBMethod getDbMethod() {
        return dbMethod;
    }

    public void setDbMethod(DBMethod dbMethod) {
        this.dbMethod = dbMethod;
    }

    public DBTypeResult getDbTypeResult() {
        return dbTypeResult;
    }

    public void setDbTypeResult(DBTypeResult dbTypeResult) {
        this.dbTypeResult = dbTypeResult;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
    
}
