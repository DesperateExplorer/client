package com.example.testing.jsonTool;

/**
 * "content2":[
 *         {
 *             "predicate": "" ,//一个uri
 *             "predicateLabel": "",// 文字
 *             "object": //一个uri
 *             "objectLabel": //文字
 *         },
 *     ]
 */

public class EntityContent2 {

    private String predicate;
    private String predicateLabel;
    private String object;
    private String objectLabel;

    //构造函数
    EntityContent2(String predicate, String predicateLabel, String object, String objectLabel)
    {
        this.predicate = predicate;
        this.predicateLabel = predicateLabel;
        this.object = object;
        this.objectLabel = objectLabel;
    }

    //get函数
    public String getPredicate() { return predicate; }
    public String getPredicateLabel() { return predicateLabel; }
    public String getObject() { return object; }
    public String getObjectLabel() { return objectLabel; }
}
