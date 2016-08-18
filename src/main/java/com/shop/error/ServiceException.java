package com.shop.error;

import java.io.Serializable;

/**
 * Created by Vladyslav Usenko on 18.08.2016.
 */
public class ServiceException extends Exception implements Serializable {
    private ErrorCode errorCode;
    private String errorMessage;
    private Throwable causedBy;

    public ServiceException(ErrorCode errorCode, String errorMessage, Throwable causedBy){
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.causedBy = causedBy;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Throwable getCausedBy() {
        return causedBy;
    }

    public void setCausedBy(Throwable causedBy) {
        this.causedBy = causedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServiceException that = (ServiceException) o;

        if (errorCode != that.errorCode) return false;
        if (errorMessage != null ? !errorMessage.equals(that.errorMessage) : that.errorMessage != null) return false;
        return causedBy != null ? causedBy.equals(that.causedBy) : that.causedBy == null;

    }

    @Override
    public int hashCode() {
        int result = errorCode != null ? errorCode.hashCode() : 0;
        result = 31 * result + (errorMessage != null ? errorMessage.hashCode() : 0);
        result = 31 * result + (causedBy != null ? causedBy.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ServiceException{" +
                "errorCode=" + errorCode +
                ", errorMessage='" + errorMessage + '\'' +
                ", causedBy=" + causedBy +
                '}';
    }
}
