package com.example.clinton.companion.dictionary.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.clinton.companion.R;
import com.example.clinton.companion.dictionary.facades.DictionaryFacade;
import com.example.clinton.companion.dictionary.workers.HandleFavWordAsync;
import com.example.clinton.companion.dictionary.workers.DelFavAsync;
import com.example.clinton.companion.dictionary.workers.DeleteFavWordAsync;
import com.example.clinton.companion.utilities.CursorRecyclerViewAdapter;
import com.example.clinton.companion.utilities.FRAGMENT_ID;

public class DictionaryAdapter extends CursorRecyclerViewAdapter<DictionaryAdapter.ViewHolder> {

    View vi;
    Context context;
    FRAGMENT_ID fragmentId;

    public DictionaryAdapter(Context context, Cursor cursor, FRAGMENT_ID id){
        super(context,cursor);
        this.context = context;
        fragmentId = id;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dict_item_layout, parent, false);
        ViewHolder holder = new ViewHolder(v);
        vi = v;
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final Cursor cursor) {
        if (viewHolder instanceof DictionaryAdapter.ViewHolder)
        {
            final DictionaryFacade facade = DictionaryFacade.fromCursor(cursor);
            String wrd = "<u>"+facade.getmWord()+"</u>";
            ((ViewHolder) viewHolder).word.setText(Html.fromHtml(wrd));
            String sp = "<i><b>" + facade.getmSpeech()+"</b></i>";
            ((ViewHolder) viewHolder).speech.setText(Html.fromHtml(sp));
            ((ViewHolder) viewHolder).definition.setText(facade.getmDefinition());
            ((ViewHolder) viewHolder).example.setText(facade.getmExamples());
            MenuItem menuItem = ((ViewHolder) viewHolder).toolbar.getMenu().findItem(R.id.fav);
            if (facade.getmFavorited() == 0)
            {
                ((ViewHolder) viewHolder).button.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_action_unfavorite));
                menuItem.setTitle(context.getResources().getString(R.string.add_to_favorites));
            }
            else
            {
                ((ViewHolder) viewHolder).button.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_action_favorited));
                menuItem.setTitle(R.string.remove_from_fav);
            }
            ((ViewHolder) viewHolder).linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                    switch (fragmentId)
                    {
                        case DICTIONARY_HOME:
                        case RANDOM_HOME:
                            break;
                        default:
                            addToFavorites(facade);
                            break;
                    }

                    return true;
                }
            });
            ((ViewHolder) viewHolder).toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    int id = item.getItemId();
                    switch (id)
                    {
                        case R.id.fav:
                            addToFavorites(facade);
                            break;
                        case R.id.share:
                            break;
                    }
                    return true;
                }
            });
        /*switch (fragmentId)
        {
            case DICTIONARY_HOME:
            case RANDOM_HOME:
                viewHolder.toolbar.setVisibility(View.INVISIBLE);
                break;
            default:
                viewHolder.toolbar.setVisibility(View.VISIBLE);
                break;
        }*/
        }



    }

    private void addToFavorites(DictionaryFacade facade)
    {
        switch (fragmentId)
        {
            case DICTIONARY_FAVORITE_ID:
                new DeleteFavWordAsync().execute(facade.getRowID(), context, fragmentId);
                break;
            default:
                long fav = facade.getmFavorited();
                if (fav == 0)
                {
                    new HandleFavWordAsync().execute(facade.getRowID(), context, fragmentId, facade);
                }else
                    new DelFavAsync().execute(facade.getRowID(), context, fragmentId, facade);
                break;
        }
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView word;
        TextView speech;
        TextView definition;
        TextView example;
        CardView cardView;
        ImageButton button;
        Toolbar toolbar;
        LinearLayout linearLayout;

        public ViewHolder (View itemView) {
            super(itemView);
            word = (TextView) itemView.findViewById(R.id.word);
            speech = (TextView) itemView.findViewById(R.id.speech);
            definition = (TextView) itemView.findViewById(R.id.meaning);
            example = (TextView)itemView.findViewById(R.id.exampleTextView);
            cardView = (CardView)itemView.findViewById(R.id.dictionaryCardView);
            button = (ImageButton)itemView.findViewById(R.id.words_fav);

            toolbar = (Toolbar) itemView.findViewById(R.id.toolbar);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.clickableLayout);

            toolbar.inflateMenu(R.menu.dict_card);
        }
    }
}
