package com.example.testing.jsonTool;

/**
 * "description1": [//来自接口7的数据（鉴于接口8不稳定，后端可以考虑在超时的情况下没有这个字段）
 *          {
 *              "subject": "", //
 *              "predicate": "", //
 *              "value": "", //后端要把value要去掉<br></br>，但是对于连续的<br>，如<br>asldfja</br><br>a;lsdkfj</br>，去<br>的时候不妨中间加一个逗号？
 *          } ,
 *          {
 *              // ...
 *          },
 *         // ...
 * 	],
 */
public class EntityDescription1 {
    private String subject;
    private String predicate;
    private String value;

    //构造函数
    EntityDescription1(String subject, String predicate, String value){
        this.subject = subject;
        this.predicate = predicate;
        this.value = value;
    }

    //set 函数
    public void setSubject(String s){
        this.subject=s;
    }
    public void setPredicate(String s){
        this.predicate=s;
    }
    public void setValue(String s){
        this.value=s;
    }

    //get 函数
    public String getSubject(){
        return subject;
    }
    public String getPredicate(){
        return predicate;
    }
    public String getValue(){
        return value;
    }


}
