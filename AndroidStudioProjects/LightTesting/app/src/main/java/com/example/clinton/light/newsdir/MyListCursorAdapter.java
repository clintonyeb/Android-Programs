package com.example.clinton.light.newsdir;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.clinton.light.R;
import com.example.clinton.light.activities.Browser;
import com.example.clinton.light.utilities.DateFormatting;


public class MyListCursorAdapter extends CursorRecyclerViewAdapter<MyListCursorAdapter.ViewHolder>{

    View vi;
    Context context;
    public MyListCursorAdapter(Context context,Cursor cursor){
        super(context,cursor);
       this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_layout, parent, false);
        ViewHolder holder = new ViewHolder(v);
        vi = v;
        return holder;
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {
        final NewsFacade facade = NewsFacade.fromCursor(cursor);
        String t = "<b>" + DateFormatting.ParseDate(facade.getDate()) + "</b>";
        viewHolder.time.setText(Html.fromHtml(t));
        viewHolder.content.setText(Html.fromHtml(facade.getText()));
        viewHolder.image.setImageBitmap(facade.getThumb());
        String hash = "<b>"+ facade.getTag()+"</b>";
        viewHolder.tag.setText(Html.fromHtml(hash));
        viewHolder.title.setText(facade.getTitle());
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                CallIntent(facade.getWebAddress());
            }
        });
    }
    private void CallIntent(String address)
    {
        Intent intent = new Intent(context, Browser.class);
        intent.putExtra(Intent.EXTRA_TEXT, address
        );
        context.startActivity(intent);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView title;
        TextView time;
        TextView content;
        ImageView image;
        TextView tag;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.newsCardView);
            title = (TextView) itemView.findViewById(R.id.headerTextView);
            time = (TextView) itemView.findViewById(R.id.timeTextView);
            content = (TextView) itemView.findViewById(R.id.textTextView);
            image = (ImageView) itemView.findViewById(R.id.thumbImageView);
            tag = (TextView) itemView.findViewById(R.id.tagTextView);
        }

    }
}
