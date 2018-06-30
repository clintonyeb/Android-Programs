package com.example.clinton.companion.todayword;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.clinton.companion.R;

import java.util.Arrays;
import java.util.List;

public class CustomRecyAdapter extends RecyclerView.Adapter<CustomRecyAdapter.RecyclerViewHolder> {

    List<String> topList;
    List<String> downList;

    public CustomRecyAdapter(String def, String exam)
    {
        topList = Arrays.asList(def.split("\\s*>\\s*"));
        downList = Arrays.asList(exam.split("\\s*>\\s*"));
    }
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.today_recy_item, parent, false);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        String sp = "<i><b>" + topList.get(position) +"</b></i>";
        holder.topTextView.setText(Html.fromHtml(sp));
        holder.downTextView.setText(downList.get(position));
    }

    @Override
    public int getItemCount() {
        return topList.size() < downList.size() ? topList.size() : downList.size();
    }

    public void upDateList(String top, String down)
    {
        topList = Arrays.asList(top.split("\\s*>\\s*"));
        downList = Arrays.asList(down.split("\\s*>\\s*"));
        notifyDataSetChanged();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public TextView topTextView;
        public TextView downTextView;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            topTextView = (TextView)itemView.findViewById(R.id.speech);
            downTextView = (TextView)itemView.findViewById(R.id.meaning);
        }
    }
}
