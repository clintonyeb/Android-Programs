package com.example.clinton.light.dict_other;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.clinton.light.R;
import com.example.clinton.light.newsdir.CursorRecyclerViewAdapter;


public class DictOtherAdapter  extends CursorRecyclerViewAdapter<DictOtherAdapter.ViewHolder> {

    View vi;
    Context context;
    public DictOtherAdapter(Context context,Cursor cursor){
        super(context,cursor);
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dict_other_item_layout, parent, false);
        ViewHolder holder = new ViewHolder(v);
        vi = v;
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final Cursor cursor) {
        final DictOtherFacade facade = DictOtherFacade.fromCursor(cursor);
        viewHolder.word.setText(facade.getmWord());
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
