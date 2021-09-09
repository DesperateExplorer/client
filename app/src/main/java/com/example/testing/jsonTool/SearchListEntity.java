package com.example.testing.jsonTool;

public class SearchListEntity {
    private String label;
    private String uri;

    //构造函数
    SearchListEntity(String label, String uri)
    {
        this.label = label;
        this.uri = uri;
    }

    public void setLabel(String s){
        this.label = s;
    }
    public void setUri(String s){
        this.uri = s;
    }

    public String getLabel(){
        return label;
    }
    public String getUri(){
        return uri;
    }

}
