package com.example.testing.jsonTool;

/**
 * "questionList": [
 *      {
 * 	"answer": "B",
 * 	"body": "下列与我国隔海相望的国家中,纬度位置最高的是()",
 * 	"branchA": "A.韩国",
 * 	"branchB": "B.日本",
 * 	"branchC": "C.印度尼西亚",
 * 	"branchD": "D.菲律宾"
 *        },
 *      {
 * 	"answer": "D",
 * 	"body": "近日朝韩局势日益紧张,如要了解朝鲜、韩国的位置,应查阅()",
 * 	"branchA": "A.中国政区图",
 * 	"branchB": "B.城市导游图",
 * 	"branchC": "C.中国气候图",
 * 	"branchD": "D.世界政治地图"
 *    },
 *      // ...
 *     ]
 *
 */

public class QuestionList {
    private String answer;
    private String body;
    private String branchA;
    private String branchB;
    private String branchC;
    private String branchD;

    //构造函数
    public QuestionList(String answer, String body, String branchA, String branchB, String branchC, String branchD)
    {
        this.answer = answer;
        this.body = body;
        this.branchA = branchA;
        this.branchB = branchB;
        this.branchC = branchC;
        this.branchD = branchD;
    }

    public void setAnswer(String s){
        this.answer=s;
    }
    public void setBody(String s){
        this.body=s;
    }
    public void setBranchA(String s){
        this.branchA=s;
    }
    public void setBranchB(String s){
        this.branchB=s;
    }
    public void setBranchC(String s){
        this.branchC=s;
    }
    public void setBranchD(String s){
        this.branchD=s;
    }

    public String getAnswer(){
        return answer;
    }
    public String getBody(){
        return body;
    }
    public String getBranchA(){
        return branchA;
    }
    public String getBranchB(){
        return branchB;
    }
    public String getBranchC(){
        return branchC;
    }
    public String getBranchD(){
        return branchD;
    }

}
