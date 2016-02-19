package com.example.maklumi.yora.infrastructure;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by HomePC on 19/2/2016.
 */
public abstract class ServiceResponse {
    private static final String TAG = "ServiceResponse";
    private String operationError; //wide scale
    private HashMap <String, String> propertyErrors; //validation errors eg email format
    private boolean isCritical; //eg not network connected

    public ServiceResponse() {
        propertyErrors = new HashMap<>();
    }

    public ServiceResponse(String operationError){
        this.operationError = operationError;
    }

    public ServiceResponse(String operationError, boolean isCritical){
        this.operationError = operationError;
        this.isCritical = isCritical;
    }

    public String getOperationError() {
        return operationError;
    }

    public void setOperationError(String operationError) {
        this.operationError = operationError;
    }

    public String getPropertyErrors(String property) {
        return propertyErrors.get(property);
    }

    public void setPropertyErrors(String property, String error) {
        propertyErrors.put(property, error);
    }

    public boolean isCritical() {
        return isCritical;
    }

    public void setIsCritical(boolean isCritical) {
        this.isCritical = isCritical;
    }

    public boolean didSucceed() {
        return operationError == null || operationError.isEmpty() && propertyErrors.size() == 0;
    }

    public void showErrorToast(Context context) {
        if (context == null || operationError == null || operationError.isEmpty())
            return;

        try {
            Toast.makeText(context, operationError, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.e(TAG, "Can't create error toast");
        }
    }
}
