package com.example.clinton.companion.dictionary.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.clinton.companion.R;
import com.example.clinton.companion.dictionary.facades.RelatedFacade;
import com.example.clinton.companion.utilities.CursorRecyclerViewAdapter;


public class RelatedAdapter extends CursorRecyclerViewAdapter<RelatedAdapter.ViewHolder> {

    View vi;
    Context context;
    public RelatedAdapter(Context context, Cursor cursor){
        super(context,cursor);
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.related_item_layout, parent, false);
        ViewHolder holder = new ViewHolder(v);
        vi = v;
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final Cursor cursor) {
        if (viewHolder instanceof  RelatedAdapter.ViewHolder)
        {
            final RelatedFacade facade = RelatedFacade.fromCursor(cursor);
            ((ViewHolder) viewHolder).word.setText(facade.getWord());
        }

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView word;
        TextView speech1;

        public ViewHolder (View itemView) {
            super(itemView);
            word = (TextView) itemView.findViewById(R.id.word);
            speech1 = (TextView) itemView.findViewById(R.id.speech);
        }
    }
}
