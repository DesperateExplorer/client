package com.example.testing.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;

import com.example.testing.R;
import com.example.testing.jsonTool.SearchListEntity;
import com.example.testing.jsonTool.ShowRelation;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class RelationAdapter extends ArrayAdapter<ShowRelation> {

    private ArrayList<ShowRelation> Data = new ArrayList<>();
    private android.content.Context Context;
    @LayoutRes int res;

    public RelationAdapter(Context sContext, ArrayList<ShowRelation> Data,  @LayoutRes int res) {
        super(sContext, res, Data);
        this.Data = Data;
        this.Context = sContext;
        this.res = res;
    }

    @Override
    public int getCount() {
        return Data.size();
    }

    @Override
    public ShowRelation getItem(int position) {
        return Data.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(Context).inflate(res, parent, false);
        }

        TextView predicate = (TextView) convertView.findViewById(R.id.predicate);
        TextView subject = (TextView) convertView.findViewById(R.id.subject);
        TextView object = (TextView) convertView.findViewById(R.id.object);

        predicate.setText(Data.get(i).getPredicate());
        object.setText(Data.get(i).getObject());
        subject.setText(Data.get(i).getSubject());

        return convertView;
    }

}
