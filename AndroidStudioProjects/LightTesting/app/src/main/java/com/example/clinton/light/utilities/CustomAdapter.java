package com.example.clinton.light.utilities;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.clinton.light.R;
import com.example.clinton.light.activities.AllRecentActivity;
import com.example.clinton.light.activities.DictOther;
import com.example.clinton.light.activities.RandomWord;
import com.example.clinton.light.activities.TodayWordActivity;
import com.example.clinton.light.activities.WordsMenu;

public class CustomAdapter extends BaseAdapter {
    String [] result;
    Context context;
    int [] imageId;
    View rowView;
    WordsMenu menu;
    Intent intent;
    private static LayoutInflater inflater=null;
    public CustomAdapter (WordsMenu menu, Context context, String[] prgmNameList, int[] prgmImages) {
        // TODO Auto-generated constructor stub
        result=prgmNameList;
        this.context = context;
        imageId=prgmImages;
        this.menu = menu;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView tv;
        ImageView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder=new Holder();
        rowView = inflater.inflate(R.layout.word_menu_item, parent, false);
        holder.tv=(TextView) rowView.findViewById(R.id.list_item_textView);
        holder.img=(ImageView) rowView.findViewById(R.id.list_item_imageView);
        holder.tv.setText(result[position]);
        holder.img.setImageResource(imageId[0]);

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                switch (position) {
                    case 0:
                        intent = new Intent(context, TodayWordActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent );
                        break;
                    case 1:
                        intent = new Intent(context, DictOther.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent );
                        break;
                    case 2:
                        intent = new Intent(context, RandomWord.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent );
                        break;

                    case 3:
                        menu.finish();
                        break;
                    case 4:
                        intent = new Intent(context, AllRecentActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("POSITION", 1);
                        context.startActivity(intent);
                        break;
                    case 5:
                        intent = new Intent(context, AllRecentActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("POSITION", 0);
                        context.startActivity(intent);
                        break;
                }

                menu.listView.setItemChecked(position, true);
            }
        });
        return rowView;
    }
}
