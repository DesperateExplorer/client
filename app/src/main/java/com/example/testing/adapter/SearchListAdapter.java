package com.example.testing.adapter;

import android.content.Context;
import android.telephony.TelephonyCallback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;

import com.example.testing.MyApplication;
import com.example.testing.R;
import com.example.testing.jsonTool.SearchListEntity;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SearchListAdapter extends ArrayAdapter<String> {

    private ArrayList<SearchListEntity> Data;
    private ArrayList<String> label;
    private Context Context;
    private int mCurrentItem = 0;
    private boolean isClick = false;
    @LayoutRes int res;
    private ArrayList<Integer> visited;

    public SearchListAdapter(Context sContext, ArrayList<String> label, ArrayList<SearchListEntity> Data, ArrayList<Integer> visited, @LayoutRes int res) {
        super(sContext,res,label);
        this.label = label;
        this.Data = Data;
        this.Context = sContext;
        this.res = res;
        this.visited = visited;

    }

    private static class ViewHolder
    {
        TextView search_list_title;
    }

    @Override
    public int getCount() {
        return Data.size();
    }

    @Override
    public String getItem(int position) {
        return label.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
//            convertView = LayoutInflater.from(Context).inflate(R.layout.sj_single, parent, false);
            convertView = LayoutInflater.from(Context).inflate(res, parent, false);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.search_list_title);

        if(visited.get(position) == 1)
        {
            textView.setTextColor(0xff5F5E5E);
        }
        else {
            textView.setTextColor(0xff000000);
        }

        textView.setText(label.get(position));
        return convertView;
    }


    //是否点击
    public void setClick(boolean click) {
        this.isClick = click;
    }

}
