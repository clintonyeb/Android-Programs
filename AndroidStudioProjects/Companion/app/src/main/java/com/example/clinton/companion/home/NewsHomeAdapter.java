package com.example.clinton.companion.home;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clinton.companion.R;
import com.example.clinton.companion.activities.CompanionBrowser;
import com.example.clinton.companion.news.workers.DelFavNews;
import com.example.clinton.companion.news.workers.DeleteFavWord;
import com.example.clinton.companion.news.workers.HandleFavoriteAsync;
import com.example.clinton.companion.news.facades.NewsFacade;
import com.example.clinton.companion.utilities.CursorRecyclerViewAdapter;
import com.example.clinton.companion.utilities.DateFormatting;
import com.example.clinton.companion.utilities.FRAGMENT_ID;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class NewsHomeAdapter extends CursorRecyclerViewAdapter<RecyclerView.ViewHolder> {

    Context context;
    FRAGMENT_ID fragmentId;
    Animation hyperspaceJumpAnimation;


    public NewsHomeAdapter(Context context, Cursor cursor, FRAGMENT_ID id) {
        super(context, cursor);
        this.context = context;
        fragmentId = id;
        hyperspaceJumpAnimation = AnimationUtils.loadAnimation(context, R.anim.hyperspace_jump);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_layout, parent, false);
        return new NewsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final Cursor cursor) {
            final NewsFacade facade = NewsFacade.fromCursor(cursor);
            String t = "<b>" + DateFormatting.ParseDate(facade.getDate()) + "</b>";
            ((NewsViewHolder) viewHolder).time.setText(Html.fromHtml(t));
            ((NewsViewHolder) viewHolder).content.setText(Html.fromHtml(facade.getText()));
            Bitmap bitmap = facade.getThumb();
            if (bitmap != null)
                ((NewsViewHolder) viewHolder).image.setImageBitmap(bitmap);
            String hash = "<b>" + facade.getTag() + "</b>";
            ((NewsViewHolder) viewHolder).tag.setText(Html.fromHtml(hash));
            ((NewsViewHolder) viewHolder).title.setText(facade.getTitle());
            MenuItem favMenuItem = ((NewsViewHolder) viewHolder).toolbar.getMenu().findItem(R.id.fav);

            if (facade.getmFavorited() == 0) {
                ((NewsViewHolder) viewHolder).button.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_action_unfavorite));
                favMenuItem.setTitle("Add to Favorites");
            } else {
                ((NewsViewHolder) viewHolder).button.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_action_favorited));
                favMenuItem.setTitle("Remove from Favorites");
            }
            ((NewsViewHolder) viewHolder).linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CallIntent(facade.getWebAddress());
                }
            });

            ((NewsViewHolder) viewHolder).linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    // viewHolder.button.startAnimation(hyperspaceJumpAnimation);
                    addToFavorites(facade);
                    return true;
                }
            });

            ((NewsViewHolder) viewHolder).toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    int id = item.getItemId();

                    switch (id) {
                        case R.id.link:
                            CallIntent(facade.getWebAddress());
                            break;
                        case R.id.fav:
                            addToFavorites(facade);
                            break;
                        case R.id.share:
                            onShareItem(facade);
                            //onShareClick();
                            break;
                    }
                    return true;
                }
            });

            switch (fragmentId) {
                case NEWS_HOME_LIFE:
                case NEWS_HOME_CULTURE:
                case NEWS_HOME_SCIENCE:
                case NEWS_HOME_SPORT:
                case NEWS_HOME_WORLD:
                    ((NewsViewHolder) viewHolder).toolbar.setVisibility(View.INVISIBLE);
                    break;
                default:
                    ((NewsViewHolder) viewHolder).toolbar.setVisibility(View.VISIBLE);
                    break;
            }
    }

    public void onShareClick() {
        Resources resources = context.getResources();

        Intent emailIntent = new Intent();
        emailIntent.setAction(Intent.ACTION_SEND);
        // Native email client doesn't currently support HTML, but it doesn't hurt to try in case they fix it
        emailIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml("Body of intent"));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject of Intent");
        emailIntent.setType("message/rfc822");

        PackageManager pm = context.getPackageManager();
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");


        Intent openInChooser = Intent.createChooser(emailIntent, "Share choose text");

        List<ResolveInfo> resInfo = pm.queryIntentActivities(sendIntent, 0);
        List<LabeledIntent> intentList = new ArrayList<LabeledIntent>();
        for (int i = 0; i < resInfo.size(); i++) {
            // Extract the label, append it, and repackage it in a LabeledIntent
            ResolveInfo ri = resInfo.get(i);
            String packageName = ri.activityInfo.packageName;
            if (packageName.contains("android.email")) {
                emailIntent.setPackage(packageName);
            } else if (packageName.contains("twitter") || packageName.contains("facebook") || packageName.contains("mms") || packageName.contains("android.gm")) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName(packageName, ri.activityInfo.name));
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                if (packageName.contains("twitter")) {
                    intent.putExtra(Intent.EXTRA_TEXT, "Share to Twitter");
                } else if (packageName.contains("facebook")) {
                    // Warning: Facebook IGNORES our text. They say "These fields are intended for users to express themselves. Pre-filling these fields erodes the authenticity of the user voice."
                    // One workaround is to use the Facebook SDK to post, but that doesn't allow the user to choose how they want to share. We can also make a custom landing page, and the link
                    // will show the <meta content ="..."> text from that page with our link in Facebook.
                    intent.putExtra(Intent.EXTRA_TEXT, "Share to facebook");
                } else if (packageName.contains("mms")) {
                    intent.putExtra(Intent.EXTRA_TEXT, "Share to sms");
                } else if (packageName.contains("android.gm")) { // If Gmail shows up twice, try removing this else-if clause and the reference to "android.gm" above
                    intent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml("Share to gmail"));
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Gmail Subject");
                    intent.setType("message/rfc822");
                }

                intentList.add(new LabeledIntent(intent, packageName, ri.loadLabel(pm), ri.icon));
            }
        }

        // convert intentList to array
        LabeledIntent[] extraIntents = intentList.toArray(new LabeledIntent[intentList.size()]);

        openInChooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, extraIntents);
        context.startActivity(openInChooser);
    }




    private void onShareItem(NewsFacade facade) {
        Uri bmpUri = getLocalBitmapUri(facade);
        if (bmpUri != null) {

            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, facade.getTitle());
            shareIntent.putExtra(Intent.EXTRA_TEXT, facade.getWebAddress());
            shareIntent.setType("image/*");

            context.startActivity(Intent.createChooser(shareIntent, "Share News To:"));
        } else {
            Toast.makeText(context, "Failed to share", Toast.LENGTH_SHORT).show();
        }
    }

    public Uri getLocalBitmapUri(NewsFacade facade) {

        Bitmap bmp = facade.getThumb();
        Uri bmpUri = null;
        try {

            File file =  new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                    "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

     void addToFavorites(NewsFacade facade)
    {
        switch (fragmentId)
        {
            case LIFESTYLE_ID:
            case SCIENCE_ID:
            case SPORT_ID:
            case CULTURE_ID:
            case WORLD_ID:
            case NEWS_SEARCH_FRAGMENT_ID:
                if (facade.getmFavorited() == 0)
                {
                    new HandleFavoriteAsync().execute(facade.getRowID(), context, fragmentId, facade);
                } else
                    new DelFavNews().execute(facade.getRowID(), context, fragmentId, facade);
                break;
            case NEWS_FAVORITE_FRAG_ID:
                new DeleteFavWord().execute(facade.getRowID(), context ,fragmentId);
                break;

        }
    }
     void CallIntent(String address)
    {
        Intent intent = new Intent(context, CompanionBrowser.class);
        intent.putExtra(Intent.EXTRA_TEXT, address
        );
        context.startActivity(intent);
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView title;
        TextView time;
        TextView content;
        ImageView image;
        TextView tag;
        RecyclerView recyclerView;
        ImageButton button;
        Toolbar toolbar;
        LinearLayout linearLayout;

        public NewsViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.newsCardView);
            title = (TextView) itemView.findViewById(R.id.headerTextView);
            time = (TextView) itemView.findViewById(R.id.timeTextView);
            content = (TextView) itemView.findViewById(R.id.textTextView);
            image = (ImageView) itemView.findViewById(R.id.thumbImageView);
            tag = (TextView) itemView.findViewById(R.id.tagTextView);
            recyclerView = (RecyclerView)itemView.findViewById(R.id.lRecyclerView);
            button = (ImageButton)itemView.findViewById(R.id.words_fav);
            toolbar = (Toolbar) itemView.findViewById(R.id.toolbar);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.clickableLayout);

            toolbar.inflateMenu(R.menu.news_card_menu);

        }
    }
}
