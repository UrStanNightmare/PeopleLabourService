package ru.academicians.myhelper.model;

public class ErrorResponse {
    private int code;
    private String information;

    public ErrorResponse() {
    }

    public ErrorResponse(int code, String information) {
        this.code = code;
        this.information = information;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }
}
