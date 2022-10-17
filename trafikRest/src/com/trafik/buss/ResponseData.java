package com.trafik.buss;

import java.util.ArrayList;

public class ResponseData {
    private String Version;
    private String Type;

    private ArrayList<Result> Result = new ArrayList<Result>();

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getVersion() {
        return Version;
    }

    public void setVersion(String version) {
        Version = version;
    }

    public ArrayList<Result> getResult() {
        return Result;
    }

    public void setResult(ArrayList<Result> list) {
        this.Result = list;
    }

}
