package com.shop.service;

import com.shop.error.ServiceException;

/**
 * Created by Vladyslav Usenko on 18.08.2016.
 */
public class ServiceResponse {

    public enum ServiceStatus{
        SUCCESS, FAILURE
    }

    private Object data;
    private Exception exception;
    private ServiceStatus status;

    public ServiceResponse() {
    }

    public ServiceResponse(Object data, ServiceException serviceException, ServiceStatus status) {
        this.data = data;
        this.exception = serviceException;
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public ServiceStatus getStatus() {
        return status;
    }

    public void setStatus(ServiceStatus status) {
        this.status = status;
    }
}
