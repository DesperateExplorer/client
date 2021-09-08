package com.example.testing.jsonTool;

/**
 *     "property": [//后端需要注意："res3"中的property格式并不工整，所以做好预处理，不要留object的uri
 *       {
 *         "predicateLabel": "", // 自然语言
 *         "objectLabel": //后端注意，要确保这个位置是文字！！！！！！！！
 *       },
 *       {
 *        // ...
 *       },
 *         // ...
 *     ]
 */


public class EntityProperty {
    private String predicateLabel;
    private String objectLabel;

    //构造函数
    EntityProperty(String predicateLabel, String objectLabel)
    {
        this.predicateLabel = predicateLabel;
        this.objectLabel = objectLabel;
    }

    //set 函数
    public void setPredicateLabel(String predicateLabel){
        this.predicateLabel=predicateLabel;
    }
    public void setObjectLabel(String objectLabel){
        this.objectLabel = objectLabel;
    }

    //get 函数
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