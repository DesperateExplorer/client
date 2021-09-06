package com.example.testing.jsonTool;

/**
 * 定义一个Bean
 *[{
 * 	"answer": "B",
 * 	"body": "下列与我国隔海相望的国家中,纬度位置最高的是()",
 * 	"branchA": "A.韩国",
 * 	"branchB": "B.日本",
 * 	"branchC": "C.印度尼西亚",
 * 	"branchD": "D.菲律宾"
 * }, {
 * 	"answer": "D",
 * 	"body": "近日朝韩局势日益紧张,如要了解朝鲜、韩国的位置,应查阅()",
 * 	"branchA": "A.中国政区图",
 * 	"branchB": "B.城市导游图",
 * 	"branchC": "C.中国气候图",
 * 	"branchD": "D.世界政治地图"
 * }, {
 * 	"answer": "A",
 * 	"body": "我国在朝核六方会谈中占很重的分量,除了是因为我国是国际大国外,还与其中两国接壤,他们是()",
 * 	"branchA": "A.俄罗斯、朝鲜",
 * 	"branchB": "B.韩国、俄罗斯",
 * 	"branchC": "C.朝鲜、日本",
 * 	"branchD": "D.日本、朝鲜"
 * }, {
 * 	"answer": "D",
 * 	"body": "与我国隔海相望的一组国家是()",
 * 	"branchA": "A.印度、文莱、印度尼西亚",
 * 	"branchB": "B.马来西亚、朝鲜、韩国",
 * 	"branchC": "C.日本、朝鲜、印度尼西亚",
 * 	"branchD": "D.文莱、马来西亚、印度尼西亚"
 * }, {
 * 	"answer": "D",
 * 	"body": "下列四组国家中,全部与我国隔海相望的一组国家是()",
 * 	"branchA": "A.印度、文莱、菲律宾",
 * 	"branchB": "B.马来西亚、朝鲜、韩国",
 * 	"branchC": "C.日本、朝鲜、印度尼西亚",
 * 	"branchD": "D.文莱、菲律宾、日本"
 * }, {
 * 	"answer": "B",
 * 	"body": "下列国家与我国陆地接壤的是()",
 * 	"branchA": "A.韩国",
 * 	"branchB": "B.朝鲜",
 * 	"branchC": "C.日本",
 * 	"branchD": "D.泰国"
 * }, {
 * 	"answer": "D",
 * 	"body": "近年来,日本与邻国因岛屿争端,关系日益紧张。下列不属于日本近邻的国家是()",
 * 	"branchA": "A.中国",
 * 	"branchB": "B.韩国",
 * 	"branchC": "C.朝鲜",
 * 	"branchD": "D.印度"
 * }, {
 * 	"answer": "B",
 * 	"body": "下列各国家中,人均国民生产总值最高的是()",
 * 	"branchA": "A.印度、巴基斯坦",
 * 	"branchB": "B.日本、新加坡",
 * 	"branchC": "C.沙特阿拉伯、以色列",
 * 	"branchD": "D.韩国、马来西亚"
 * }, {
 * 	"answer": "D",
 * 	"body": "与我国隔海相望的国家有()",
 * 	"branchA": "A.朝鲜、韩国、日本",
 * 	"branchB": "B.越南、菲律宾、文莱",
 * 	"branchC": "C.缅甸、越南、文莱",
 * 	"branchD": "D.韩国、菲律宾、印度尼西亚"
 * }, {
 * 	"answer": "B",
 * 	"body": "第17届亚运会于2014年9月19日—10月4日在韩国仁川举行,我国运动员取得了优异的成绩。关于韩国与我国的位置关系说法正确的是()",
 * 	"branchA": "A.陆上相邻",
 * 	"branchB": "B.隔海相望",
 * 	"branchC": "C.既陆上相邻又隔海相望",
 * 	"branchD": "D.既不陆上相邻又不隔海相望"
 * }]
 *
 */

public class EntityPractice {
    private String answer;
    private String body;
    private String branchA;
    private String branchB;
    private String branchC;
    private String branchD;

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
