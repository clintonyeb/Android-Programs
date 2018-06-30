
package com.example.holys.bgcclms;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.provider.Browser;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class DisplayCustomAdapter extends RecyclerView.Adapter<DisplayCustomAdapter.NewsDataHolder> {

    List<NewsDataFacade> newsData;
    NewsDataDisplay newsDataDisplay;
    View vi;

    DisplayCustomAdapter (List<NewsDataFacade> data, NewsDataDisplay activity) {
        this.newsData = data;
        newsDataDisplay = activity;
    }

    @Override
    public NewsDataHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.display_info_item_layout, parent, false);
        NewsDataHolder holder = new NewsDataHolder(v);
        vi = v;
        return holder;
    }

    @Override
    public void onBindViewHolder (final NewsDataHolder holder, final int position) {
        holder.title.setText(newsData.get(position).getmTitlte());
        holder.date.setText(newsData.get(position).getmPubDate());
        holder.content.setText(newsData.get(position).getmContent());
        holder.frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                CallIntent(position);
            }
        });

        holder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                cycleTextView(holder.content);
            }
        });
        /*for (int i = 0; i < getItemCount(); i++) {

            animate(vi, i);

        }*/
    }
    private void CallIntent(int position)
    {
        Intent intent = new Intent(newsDataDisplay, MainBrowser.class);
        intent.putExtra(Intent.EXTRA_TEXT, newsData.get(position).getmNewsUri());
        newsDataDisplay.startActivity(intent);
    }

    private void cycleTextView(TextView textView)
    {
        int collapsedLines = 3;
        ObjectAnimator animator = ObjectAnimator.ofInt(textView, "maxLines", textView.getMaxLines()
                == collapsedLines ? textView.getLineCount() : collapsedLines);
        animator.setDuration(200).start();
    }
    private void expandTextView(TextView textView)
    {
        ObjectAnimator animator = ObjectAnimator.ofInt(textView, "maxLines", textView.getMaxLines());
        animator.setDuration(200).start();

    }
    private void collapseTextView(TextView textView)
    {
        ObjectAnimator animator = ObjectAnimator.ofInt(textView, "maxLines", 3);
        animator.setDuration(200).start();
    }
    private int lastPosition = -1;
    private void animate(final View view, final int position){

        Animation animation = AnimationUtils.loadAnimation(newsDataDisplay, android.R.anim.slide_in_left);
        view.startAnimation(animation);
        lastPosition = position;

    }

    @Override
    public void onAttachedToRecyclerView (RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

    }

    @Override
    public int getItemCount () {
        return newsData.size();
    }

    public static class NewsDataHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView title;
        TextView date;
        TextView content;
        FrameLayout frame;
        LinearLayout linear;
        public NewsDataHolder (View itemView) {
            super(itemView);
            linear = (LinearLayout)itemView.findViewById(R.id.linear_layout);
            cardView = (CardView) itemView.findViewById(R.id.mCardView);
            title = (TextView) itemView.findViewById(R.id.news_title);
            date = (TextView) itemView.findViewById(R.id.news_date);
            content = (TextView) itemView.findViewById(R.id.news_content);
            frame = (FrameLayout)itemView.findViewById(R.id.frame_layout);
        }

    }
}

