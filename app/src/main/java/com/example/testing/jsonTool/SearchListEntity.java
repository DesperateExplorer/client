package com.example.testing.jsonTool;

public class SearchListEntity {
    private String label;
    private String uri;

    public void setLabel(String s){
        this.label = s;
    }
    public void setUrl(String s){
        this.uri = s;
    }

    public String getLabel(){
        return label;
    }
    public String getUri(){
        return uri;
    }

}
