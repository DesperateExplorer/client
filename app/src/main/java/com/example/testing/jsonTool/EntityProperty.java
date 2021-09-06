package com.example.testing.jsonTool;

/**
 * 定义一个Bean
 * [
 *             {
 *                 "predicate": "http://edukg.org/knowledge/0.1/property/common#topicOf",
 *                 "predicateLabel": "强相关于",
 *                 "objectLabel": "边",
 *                 "object": "http://edukg.org/knowledge/0.1/instance/math#119"
 *             },
 *             {
 *                 "predicate": "http://edukg.org/knowledge/0.1/property/common#topicOf",
 *                 "predicateLabel": "强相关于",
 *                 "objectLabel": "三角",
 *                 "object": "http://edukg.org/knowledge/0.1/instance/math#332"
 *             },
 *             {
 *                 "predicate": "http://edukg.org/knowledge/0.1/property/common#source",
 *                 "predicateLabel": "出处",
 *                 "object": "1.2.14.1.3"
 *             },
 *             {
 *                 "predicate": "http://www.w3.org/2000/01/rdf-schema#label",
 *                 "predicateLabel": "名称",
 *                 "object": "余弦定理"
 *             },
 *             {
 *                 "predicate": "http://www.w3.org/1999/02/22-rdf-syntax-ns#type",
 *                 "predicateLabel": "类型",
 *                 "object": "http://edukg.org/knowledge/0.1/class/math#shuxuedingli-d7efa6467a1cb775026237ea82988b61"
 *             },
 *             {
 *                 "predicate": "http://edukg.org/knowledge/0.1/property/common#page",
 *                 "predicateLabel": "页码",
 *                 "object": "高中数学知识清单133"
 *             },
 *             {
 *                 "predicate": "http://edukg.org/knowledge/0.1/property/common#topicOf",
 *                 "predicateLabel": "强相关于",
 *                 "objectLabel": "角",
 *                 "object": "http://edukg.org/knowledge/0.1/instance/math#260"
 *             },
 *             {
 *                 "predicate": "http://edukg.org/knowledge/0.1/property/common#content",
 *                 "predicateLabel": "内容",
 *                 "object": "三角形任何一边的平方等于其他两边的平方和减去这两边与它们夹角的余弦的积的两倍"
 *             },
 *             {
 *                 "predicate": "http://edukg.org/knowledge/0.1/property/common#topicOf",
 *                 "predicateLabel": "强相关于",
 *                 "objectLabel": "三角形",
 *                 "object": "http://edukg.org/knowledge/0.1/instance/math#332"
 *             }
 *         ]
 *
 */
public class EntityProperty {
    private String predicateLabel;
    private String objectLabel;

    public void setLabel(String predicateLabel){
        this.predicateLabel=predicateLabel;
    }

    public void setUrl(String objectLabel){
        this.objectLabel = objectLabel;
    }

    public String getPredicateLabel(){
        return predicateLabel;
    }

    public String getObjectLabel(){
        return objectLabel;
    }

    public void show(){
        System.out.println("predicate = "+predicateLabel);
        System.out.println("object = "+objectLabel);
    }

}