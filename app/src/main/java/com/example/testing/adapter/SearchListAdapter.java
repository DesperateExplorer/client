//package com.example.testing.adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.TextView;
//
//import com.example.testing.R;
//import com.example.testing.jsonTool.SearchListEntity;
//
//import java.util.ArrayList;
//
//public class SearchListAdapter extends BaseAdapter {
//
//    private ArrayList<SearchListEntity> Data;
//    private Context Context;
//    private int mCurrentItem = 0;
//    private boolean isClick = false;
//
//    public SearchListAdapter(Context sContext, ArrayList<SearchListEntity> ssData, yout) {
//        this.Data = ssData;
//        this.Context = sContext;
//    }
//
//    public SearchListAdapter() {
//    }
//
//    @Override
//
//    public int getCount() {
//        return Data.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return position;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        if (convertView == null) {
//            convertView = LayoutInflater.from(Context).inflate(R.layout.sj_single, parent, false);
//        }
//        TextView sTime = (TextView) convertView.findViewById(R.id.stime);
//        TextView sName = (TextView) convertView.findViewById(R.id.name);
//        TextView sNumber = (TextView) convertView.findViewById(R.id.number);
//
//        sTime.setText(ssData.get(position).getTime());
//        sName.setText(ssData.get(position).getName());
//        sNumber.setText(ssData.get(position).getNumber());
//
//        /*
//         *这个进行判断mCurrentItem=行号，isClick是否点击
//         */
//        if (mCurrentItem == position && isClick) {
//            convertView.setBackgroundColor(Color.parseColor("#FFEFDB"));
//            sTime.setTextColor(Color.parseColor("#9933cc"));
//        } else {
//            convertView.setBackgroundColor(Color.parseColor("#ffffff"));
//            sTime.setTextColor(Color.parseColor("#000000"));
//        }
//
//
//        return convertView;
//    }
//
//    //获取行号
//    public void setCurrentItem(int currentItem) {
//        this.mCurrentItem = currentItem;
//    }
//
//    //是否点击
//    public void setClick(boolean click) {
//        this.isClick = click;
//    }
//
//}
