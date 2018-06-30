package com.example.holys.bgcclms;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by holys on 2/13/2016.
 */
public class CustomAdapter extends BaseAdapter {
    String [] result;
    Context context;
    int [] imageId;
    MainActivityFragment fragment;
    private static LayoutInflater inflater=null;
    public CustomAdapter(MainActivityFragment frag, MainActivity mainActivity, String[] prgmNameList, int[] prgmImages) {
        // TODO Auto-generated constructor stub
        result=prgmNameList;
        context=mainActivity;
        imageId=prgmImages;
        fragment =  frag;
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
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        final View rowView;
        rowView = inflater.inflate(R.layout.list_item, null);
        holder.tv=(TextView) rowView.findViewById(R.id.list_item_textView);
        holder.img=(ImageView) rowView.findViewById(R.id.list_item_imageView);
        holder.tv.setText(result[position]);
        holder.img.setImageResource(imageId[position]);
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                // TODO Auto-generated method stub
                //Log.v( "You Clicked " , result[position]);
                Resources res = context.getResources();
                boolean land = res.getBoolean(R.bool.landscape);
                fragment.listView.setItemChecked(position, true);
                if (position == 4) {
                    Intent intent = new Intent(context, NewsDataDisplay.class);
                    intent.putExtra(Intent.EXTRA_TEXT, position);
                    context.startActivity(intent);
                    return;
                } else {
                    if (!land) {
                        Intent intent = new Intent(context, InnerListItems.class);
                        intent.putExtra(Intent.EXTRA_TEXT, result[position]);
                        context.startActivity(intent);
                    } else {

                        fragment.updateDetail(result[position]);
                    }
                }


            }
        });
        return rowView;
    }
}
