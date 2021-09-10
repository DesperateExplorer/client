package com.example.testing.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.LayoutRes;

import com.example.testing.AppSingle;
import com.example.testing.R;
import com.example.testing.jsonTool.QuestionList;

import java.util.ArrayList;

public class PlusTestAdapter extends ArrayAdapter<QuestionList> {

    private ArrayList<QuestionList> Data = new ArrayList<>();
    private android.content.Context Context;
    @LayoutRes
    int res;

    public PlusTestAdapter(android.content.Context sContext, ArrayList<QuestionList> Data, @LayoutRes int res) {
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
    public QuestionList getItem(int position) {
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

        TextView body = (TextView) convertView.findViewById(R.id.body);
        RadioButton A = (RadioButton) convertView.findViewById(R.id.branchA);
        RadioButton B = (RadioButton) convertView.findViewById(R.id.branchB);
        RadioButton C = (RadioButton) convertView.findViewById(R.id.branchC);
        RadioButton D = (RadioButton) convertView.findViewById(R.id.branchD);

        body.setText(Data.get(i).getBody());
        A.setText(Data.get(i).getBranchA());
        B.setText(Data.get(i).getBranchB());
        C.setText(Data.get(i).getBranchC());
        D.setText(Data.get(i).getBranchD());

        Button button = (Button) convertView.findViewById(R.id.submit);
        View root_view = convertView;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                A.setEnabled(false);
                B.setEnabled(false);
                C.setEnabled(false);
                D.setEnabled(false);

                TextView t = root_view.findViewById(R.id.answer);
                String ans = Data.get(i).getAnswer();
                t.setText("本题的答案是：" + ans);
                t.setVisibility(View.VISIBLE);

                //TODO:在全局变量里面记分
                boolean correct = false;
                switch (ans)
                {
                    case "A":
                        if(A.isChecked())
                            correct = true;
                        break;
                    case "B":
                        if(B.isChecked())
                            correct = true;
                        break;
                    case "C":
                        if(C.isChecked())
                            correct = true;
                        break;
                    case "D":
                        if(D.isChecked())
                            correct = true;
                        break;
                }

                AppSingle.setScore(correct);
            }
        });

        return convertView;
    }

}
