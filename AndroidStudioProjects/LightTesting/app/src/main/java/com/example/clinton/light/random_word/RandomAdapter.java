package com.example.clinton.light.random_word;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.clinton.light.R;
import com.example.clinton.light.dictionary_main.DeleteFavWord;
import com.example.clinton.light.dictionary_main.StoreFavWord;
import com.example.clinton.light.newsdir.CursorRecyclerViewAdapter;
import com.example.clinton.light.utilities.OnDoubleTapListener;

public class RandomAdapter extends CursorRecyclerViewAdapter<RandomAdapter.ViewHolder> {

    View vi;
    Context context;
    int which = -1;
    public RandomAdapter(Context context,Cursor cursor, int pos){
        super(context,cursor);
        this.context = context;
        which = pos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.random_item_layout, parent, false);
        ViewHolder holder = new ViewHolder(v);
        vi = v;
        return holder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final Cursor cursor) {

        final RandomFacade facade = RandomFacade.fromCursor(cursor);
        String wrd = "<u>"+facade.getmWord()+"</u>";
        viewHolder.word.setText(Html.fromHtml(wrd));
        String sp = "<i><b>" + facade.getmSpeech()+"</b></i>";
        viewHolder.speech.setText(Html.fromHtml(sp));
        viewHolder.definition.setText(facade.getmDefinition());
        viewHolder.example.setText(facade.getmExamples());
        viewHolder.cardView.setOnTouchListener(new OnDoubleTapListener(context)
        {
            @Override
            public void onDoubleTap(MotionEvent e) {
                super.onDoubleTap(e);
                if (facade.getmFavorited() == 0)
                {
                    new StoreFavWord().execute(context, viewHolder.getAdapterPosition(), which);
                    facade.setmFavorited(1);
                }
                else
                {
                    new DeleteFavWord().execute(context, viewHolder.getAdapterPosition(), which);
                    facade.setmFavorited(0);
                }
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView word;
        TextView speech;
        TextView definition;
        TextView example;
        CardView cardView;

        public ViewHolder (View itemView) {
            super(itemView);
            word = (TextView) itemView.findViewById(R.id.word);
            speech = (TextView) itemView.findViewById(R.id.speech);
            definition = (TextView) itemView.findViewById(R.id.meaning);
            example = (TextView)itemView.findViewById(R.id.exampleTextView);
            cardView = (CardView)itemView.findViewById(R.id.dictionaryCardView);
        }
    }
}
