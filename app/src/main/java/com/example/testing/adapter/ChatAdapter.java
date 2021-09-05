package com.example.testing.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testing.model.ChatModel;
import com.example.testing.R;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    //存放数据
    List<ChatModel> chatModelList;


    //通过构造函数传入数据
    public ChatAdapter(List<ChatModel> dataList) {
        this.chatModelList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //布局加载器
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChatModel chatModel = chatModelList.get(position);
        if (chatModel.getType() == ChatModel.SEND) {
            holder.leftLayout.setVisibility(View.GONE);
            holder.rightLayout.setVisibility(View.VISIBLE);
            holder.rightNameTextView.setText(chatModel.getName());
            holder.rightContentTextView.setText(chatModel.getContent());
            holder.rightImageView.setImageResource(chatModel.getImgId());
        } else {
            holder.rightLayout.setVisibility(View.GONE);
            holder.leftLayout.setVisibility(View.VISIBLE);
            holder.leftNameTextView.setText(chatModel.getName());
            holder.leftContentTextView.setText(chatModel.getContent());
            holder.leftImageView.setImageResource(chatModel.getImgId());
        }

    }

    /**
     * 获取数据长度
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return chatModelList.size();
    }

    /**
     * 缓存页面布局，页面快速滚动时不必每次都重新创建View
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView leftImageView;
        TextView leftNameTextView;
        TextView leftContentTextView;
        LinearLayout leftLayout;

        ImageView rightImageView;
        TextView rightNameTextView;
        TextView rightContentTextView;
        LinearLayout rightLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            leftImageView = (ImageView) itemView.findViewById(R.id.left_image);
            leftContentTextView = (TextView) itemView.findViewById(R.id.left_content);
            leftNameTextView = (TextView) itemView.findViewById(R.id.left_name);
            leftLayout = (LinearLayout) itemView.findViewById(R.id.left_bubble);

            rightImageView = (ImageView) itemView.findViewById(R.id.right_image);
            rightContentTextView = (TextView) itemView.findViewById(R.id.right_content);
            rightNameTextView = (TextView) itemView.findViewById(R.id.right_name);
            rightLayout = (LinearLayout) itemView.findViewById(R.id.right_bubble);

        }
    }
}
