package com.example.clinton.homeflavour;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FoodItemAdapter extends BaseAdapter{

    String [] header;
    String [] description;
    Context context;
    int imageId;

    public FoodItemAdapter( Context context) {
        this.context = context;
        initializeItems();
    }
    public void initializeItems()
    {
        header = new String[]
                {
                    context.getString(R.string.header1),
                    context.getString(R.string.header2),
                    context.getString(R.string.header3),
                    context.getString(R.string.header4)
                };

        description = new String[]
                {
                  context.getString(R.string.description1),
                        context.getString(R.string.description2),
                        context.getString(R.string.description3),
                        context.getString(R.string.description4)
                };

        imageId = R.drawable.food_item_image;
    }

    @Override
    public int getCount() {
        return header.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item, parent, false);
        Holder holder=new Holder(convertView);
        holder.itemHeader.setText(header[position]);
        holder.itemDescription.setText(description[position]);
        holder.itemOrder.setText(R.string.order_here);
        holder.itemImage.setImageResource(imageId);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                /*menu.listView.setItemChecked(position, true);
                menu.ItemSelected(position);*/
            }
        });
        return convertView;
    }
    public class Holder
    {
        TextView itemHeader;
        TextView itemDescription;
        TextView itemOrder;
        ImageView itemImage;


        public Holder(View itemView)
        {
            itemHeader = (TextView) itemView.findViewById(R.id.foodHeader);
            itemDescription = (TextView) itemView.findViewById(R.id.foodDescription);
            itemOrder = (TextView) itemView.findViewById(R.id.orderLink);
            itemImage=(ImageView) itemView.findViewById(R.id.foodImage);
        }
    }
}
