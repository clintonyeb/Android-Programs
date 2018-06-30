package com.example.holys.light.NEWS_DIR;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.holys.light.Browser;
import com.example.holys.light.R;
import com.example.holys.light.TabsFragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by holys on 2/25/2016.
 */

public class NewsCustomCardAdapter extends RecyclerView.Adapter<NewsCustomCardAdapter.NewsDataHolder> {

    List<NewsFacade> newsData;
    TabsFragment newsFragment;
    View vi;

    public NewsCustomCardAdapter (List<NewsFacade> data, TabsFragment fragment) {
        this.newsData = data;
        newsFragment = fragment;
    }

    @Override
    public NewsDataHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_layout, parent, false);
        NewsDataHolder holder = new NewsDataHolder(v);
        vi = v;
        return holder;
    }

    @Override
    public void onBindViewHolder (final NewsDataHolder holder, final int position) {
        holder.title.setText(newsData.get(position).getTitle());
        String iTime = newsData.get(position).getDate();
        String parsedTime = ParseDate(iTime);
        holder.time.setText(parsedTime);
        holder.content.setText(Html.fromHtml(newsData.get(position).getText()));
        String hash = "<b>"+ newsData.get(position).getTag()+"</b>";
        holder.tag.setText(Html.fromHtml(hash));
        holder.image.setImageBitmap(newsData.get(position).getThumb());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                CallIntent(position);
            }
        });
    }
    public static String ParseDate(String dateR)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat print  = new SimpleDateFormat("HH:mm");
        try
        {
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            Date date = sdf.parse(dateR);
            return (print.format(date));
        }
        catch(Exception e)
        {
            System.err.println("Error" + e);
        }
        return dateR;
    }


    @Override
    public int getItemCount () {
        return newsData.size();
    }

    private void CallIntent(int position)
    {
        Intent intent = new Intent(newsFragment.getContext(), Browser.class);
        intent.putExtra(Intent.EXTRA_TEXT, newsData.get(position).getWebAddress()
        );
        newsFragment.startActivity(intent);
    }

    @Override
    public void onAttachedToRecyclerView (RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

    }

    public static class NewsDataHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView title;
        TextView time;
        TextView content;
        ImageView image;
        TextView tag;

        public NewsDataHolder (View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.newsCardView);
            title = (TextView) itemView.findViewById(R.id.headerTextView);
            time = (TextView) itemView.findViewById(R.id.timeTextView);
            content = (TextView) itemView.findViewById(R.id.textTextView);
            image = (ImageView)itemView.findViewById(R.id.thumbImageView);
            tag = (TextView)itemView.findViewById(R.id.tagTextView);
        }

    }
}
