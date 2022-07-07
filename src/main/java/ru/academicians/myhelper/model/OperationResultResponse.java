package ru.academicians.myhelper.model;

public class OperationResultResponse {
    private String operation;

    private String result;

    public OperationResultResponse() {
    }

    public OperationResultResponse(String operation, String result) {
        this.operation = operation;
        this.result = result;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
