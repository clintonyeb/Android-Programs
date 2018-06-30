package com.example.clinton.companion.dictionary.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.clinton.companion.R;
import com.example.clinton.companion.dictionaryFragments.MenuFragment;

public class MenuAdapter extends BaseAdapter {
    String [] result;
    Context context;
    int [] imageId;
    MenuFragment menu;

    public MenuAdapter(MenuFragment menu, Context context, String[] prgmNameList, int[] prgmImages) {
        result=prgmNameList;
        this.context = context;
        imageId=prgmImages;
        this.menu = menu;

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

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_menu_item, parent, false);
        Holder holder=new Holder(convertView);
        holder.tv.setText(result[position]);
        holder.img.setImageResource(imageId[0]);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                menu.listView.setItemChecked(position, true);
                menu.ItemSelected(position);
            }
        });
        return convertView;
    }

    public class Holder
    {
        TextView tv;
        ImageView img;

        public Holder(View itemView)
        {
            tv=(TextView) itemView.findViewById(R.id.list_item_textView);
            img=(ImageView) itemView.findViewById(R.id.list_item_imageView);
        }
    }


}
