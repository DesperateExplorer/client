package com.example.testing.jsonTool;

/**
 * 定义一个Bean
 * [{
 * 			'feature_key': '下属于',
 * 			'feature_value': '整式方程'
 *                }, {
 * 			'feature_key': '定义',
 * 			'feature_value': '只含有一个未知数，并且未知数的最高次数是2的整式方程叫做一元二次方程'
 *        }, {
 * 			'feature_key': '性质1',
 * 			'feature_value': '一元二次方程的一般形式是ax^2＋bx＋c=0（a≠0）'
 *        }, {
 * 			'feature_key': '性质2',
 * 			'feature_value': '通过开平方运算解一元二次方程的方法叫做直接开平方法'
 *        }, {
 * 			'feature_key': '性质3',
 * 			'feature_value': '对于一个一元二次方程，首先利用恒等变形通过配方把它化成一边是含有未知数的完全平方的形式，另一边是非负常数，再用开平方法解方程的方法就是配方法'
 *        }, {
 * 			'feature_key': '性质4',
 * 			'feature_value': '公式法是用求根公式求出一元二次方程的解的方法，它是解一元二次方程的一般方法'
 *        }, {
 * 			'feature_key': '性质5',
 * 			'feature_value': '当一元二次方程的一边为0，而另一边易分解成两个一次因式的乘积时，可分别得到两个一元一次方程，从而达到“降次”的目的，得到的两个解就是一元二次方程的解，这种解方程的方法叫做因式分解法'
 *        }, {
 * 			'feature_key': '性质6',
 * 			'feature_value': '配方法解一元二次方程的一般步骤：（1）移项：将常数项移到方程右边.（2）把二次项系数化为1：方程左右两边同时除以二次项系数.（3）配方：方程左右两边同时加上一次项系数一半的平方，把原方程化为（x＋m）2=n的形式.（4）用直接开平方法解变形后的方程'
 *        }, {
 * 			'feature_key': '性质7',
 * 			'feature_value': '一般地，常用字母“△”表示b^2－4ac，即Δ=b^2－4ac'
 *        }, {
 * 			'feature_key': '性质8',
 * 			'feature_value': '一元二次方程ax^2＋bx＋c=0（a≠0）,当Δ=b^2－4ac＞0时，方程有两个不相等的实数根'
 *        }, {
 * 			'feature_key': '性质9',
 * 			'feature_value': '一元二次方程ax^2＋bx＋c=0（a≠0）,当Δ=b^2－4ac=0时，方程有两个相等的实数根'
 *        }, {
 * 			'feature_key': '性质10',
 * 			'feature_value': '一元二次方程ax^2＋bx＋c=0（a≠0）,当Δ=b^2－4ac＜0时，方程没有实数根'
 *        }, {
 * 			'feature_key': '性质11',
 * 			'feature_value': '公式法解一元二次方程的一般步骤：（1）将一元二次方程整理成一般形式；（2）确定公式中a，b，c的值；（3）求出b2－4ac的值；（4）当b2－4ac≥0时，将a，b，c的值及b2－4ac的值代入求根公式即可；当b2－4ac＜0时，方程无实数根'
 *        }, {
 * 			'feature_key': '性质12',
 * 			'feature_value': '因式分解法解一元二次方程的一般步骤（1）将方程的右边化为0；（2）将方程的左边分解为两个一次因式的乘积；（3）令每个因式分别为零，得到两个一元一次方程；（4）解这两个一元一次方程，它们的解就是原方程的解'
 *        }, {
 * 			'feature_key': '性质13',
 * 			'feature_value': '如果ax^2＋bx＋c=0（a≠0）的两个实数根是x1，x2，那么x1＋x2=－b/a，x1x2=c/a'
 *        }, {
 * 			'feature_key': '性质14',
 * 			'feature_value': '如果方程x^2＋px＋q=0的两个根是x1，x2，那么x1＋x2=－p，x1x2=q'
 *        }, {
 * 			'feature_key': '性质15',
 * 			'feature_value': '以两个数x1，x2为根的一元二次方程（二次项系数为1）是x^2－（x1＋x2）x＋x1x2=0'
 *  }]
 *
 *
 */
public class EntityDescription {
    private String feature_key;
    private String feature_value;

    public void setLabel(String key){
        this.feature_key=key;
    }

    public void setUrl(String value){
        this.feature_value=value;
    }

    public String getFeature_key(){
        return feature_key;
    }

    public String getFeature_value(){
        return feature_value;
    }

    public void show(){
        System.out.println("feature_key="+feature_key);
        System.out.println("feature_value="+feature_value);
    }

}
