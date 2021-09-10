package com.example.testing;

import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;

import java.util.ArrayList;

public class StringDesignUtil {
    /**
     * 方法描述：根据文本下标，指定单个部分文字变色
     **/
    public static SpannableStringBuilder getSpannableStringBuilder(String text, int color, ArrayList<Integer> startIndex, ArrayList<Integer> endIndex) {
        SpannableStringBuilder builder = new SpannableStringBuilder(text);

        int len = startIndex.size();
        int len2 = endIndex.size();
        for(int i=0; i<len; i++)
        {
            if(startIndex.get(i)>= 0 && endIndex.get(i) > startIndex.get(i) && endIndex.get(i) <= text.length())
            {
                builder.setSpan(new ForegroundColorSpan(color), startIndex.get(i), endIndex.get(i), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return builder;
    }

    /**
     * 方法描述：指定关键字变色，并且给相对应的关键指定颜色
     **/
    public static SpannableStringBuilder getSpannableStringBuilder(String text, String texts[], int color[]) {
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        if (texts != null) {
            for (int i = 0; i < texts.length; i++) {
                String value = texts[i];
                if (!TextUtils.isEmpty(value) && text.contains(value)) {
                    if (color != null && color.length > i) {
                        int startIndex = text.indexOf(value);
                        int entIndex = startIndex + value.length();
                        if (entIndex > startIndex && entIndex <= text.length()) {
                            builder.setSpan(new ForegroundColorSpan(color[i]), startIndex, entIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                    }
                }
            }
        }
        return builder;
    }

    /**
     * 方法描述： 文本由关键字拼接而成，文本内容与字体颜色一一对应显示
     **/
    public static SpannableStringBuilder getSpannableStringBuilder(String text[], int color[]) {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        if (text != null) {
            for (int i = 0; i < text.length; i++) {
                int startIndex = builder.length();
                builder.append(text[i]);
                if (color != null && color.length > i) {
                    builder.setSpan(new ForegroundColorSpan(color[i]), startIndex, builder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }
        return builder;
    }

    /**
     * 根据关键字，指定单个部分文字颜色
     **/
    public static Spanned getSpanned(String text, String keyword, String colorValue) {
        return Html.fromHtml(text.replace(keyword, "<font color=" + colorValue + ">" + keyword + "</font>"));
    }

    /**
     * 指定关键字变色，并且给相对应的关键指定颜色
     **/
    public static Spanned getSpanned(String text, String keyword[], String colorValue[]) {
        if (keyword != null && colorValue != null) {
            for (int i = 0; i < keyword.length; i++) {
                if (colorValue.length > i) {
                    text = text.replace(keyword[i], "<font color=" + colorValue[i] + ">" + keyword[i] + "</font>");
                }
            }
        }
        return Html.fromHtml(text);
    }

    /**
     * 文本由关键字拼接而成，文本内容与字体颜色一一对应显示
     **/
    public static Spanned getSpanned(String keyword[], String colorValue[]) {
        StringBuffer buffer = new StringBuffer();
        if (keyword != null && colorValue != null) {
            for (int i = 0; i < keyword.length; i++) {
                if (colorValue.length > i) {
                    buffer.append("<font color=" + colorValue[i] + ">" + keyword[i] + "</font>");
                }
            }
        }
        return Html.fromHtml(buffer.toString());
    }

}
