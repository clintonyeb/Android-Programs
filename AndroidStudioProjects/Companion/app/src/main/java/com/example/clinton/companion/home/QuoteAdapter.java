package com.example.clinton.companion.home;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.clinton.companion.R;
import com.example.clinton.companion.utilities.CursorRecyclerViewAdapter;

public class QuoteAdapter extends CursorRecyclerViewAdapter<QuoteAdapter.ViewHolder> {

    Context context;


    public QuoteAdapter(Context context, Cursor cursor) {
        super(context, cursor);
        this.context = context;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, Cursor cursor) {
        if (viewHolder instanceof QuoteAdapter.ViewHolder)
        {
            final QuoteFacade quoteFacade = QuoteFacade.fromCursor(cursor);
            ((ViewHolder) viewHolder).quoteText.setText(quoteFacade.getQuoteText());
            String auth = "<b>" + quoteFacade.getQuoteAuthor() + "</b>";
            ((ViewHolder) viewHolder).quoteAurthur.setText(Html.fromHtml(auth));

            ((ViewHolder) viewHolder).toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    int id = item.getItemId();
                    switch (id)
                    {
                        case R.id.share:
                            onShareItem(quoteFacade);
                            break;
                    }
                    return true;
                }
            });
        }


    }

    private void onShareItem(QuoteFacade quoteFacade) {

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.quote_item, parent, false);
        return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView quoteText;
        TextView quoteAurthur;
        Toolbar toolbar;

        public ViewHolder(View itemView) {
            super(itemView);
            quoteText = (TextView)itemView.findViewById(R.id.quoteTextTextView);
            quoteAurthur = (TextView)itemView.findViewById(R.id.quoteAurthurTextView);
            toolbar = (Toolbar) itemView.findViewById(R.id.toolbar);
            toolbar.inflateMenu(R.menu.share_menu);
        }
    }
}
