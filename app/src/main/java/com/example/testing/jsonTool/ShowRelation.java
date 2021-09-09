package com.example.testing.jsonTool;

public class ShowRelation {

    private String predicate;
    private String subject;
    private String object;

    public ShowRelation(String predicate, String subject, String object)
    {
        this.predicate = predicate;
        this.subject = subject;
        this.object = object;
    }

    public String getPredicate() {
        return predicate;
    }

    public String getObject() {
        return object;
    }

    public String getSubject() {
        return subject;
    }
}
