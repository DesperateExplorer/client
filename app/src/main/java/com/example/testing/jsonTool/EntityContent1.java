package com.example.testing.jsonTool;

/**
 * "content1": [// "content"列表中每项有4个字段，如下例所示（这是前端需要画成图结构的部分）
 *         {
 *             "predicate": "" ,//一个uri
 *             "predicateLabel": "",// 文字
 *             "subject": //一个uri
 *             "subjectLabel": //文字
 *         },
 *     ],
 */

public class EntityContent1 {

    private String predicate;
    private String predicateLabel;
    private String subject;
    private String subjectLabel;

    //构造函数
    EntityContent1(String predicate, String predicateLabel, String subject, String subjectLabel)
    {
        this.predicate = predicate;
        this.predicateLabel = predicateLabel;
        this.subject = subject;
        this.subjectLabel = subjectLabel;
    }

    //set 函数
    public void setPredicate(String s){
        this.predicate=s;
    }
    public void setPredicateLabel(String s){
        this.predicateLabel=s;
    }
    public void setSubject(String s){
        this.subject=s;
    }
    public void setSubjectLabel(String s){
        this.subjectLabel=s;
    }

    //get 函数
    public String getPredicate(){ return predicate;}
    public String getPredicateLabel(){ return predicateLabel;}
    public String getSubject(){ return subject;}
    public String getSubjectLabel(){ return subjectLabel;}


}
