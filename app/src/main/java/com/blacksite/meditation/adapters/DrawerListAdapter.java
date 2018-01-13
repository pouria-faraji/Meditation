package com.blacksite.meditation.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.blacksite.meditation.R;

/**
 * Created by pouria on 6/28/2017.
 */
public class DrawerListAdapter extends BaseAdapter{

    private Context mContext;
    private String[]  title;
    private int[] icon;

    public DrawerListAdapter(Context context, String[] titles,int[] imageIds) {
        mContext = context;
        title = titles;
        icon = imageIds;

    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View row;
        row = inflater.inflate(R.layout.drawer_list_item, parent, false);
        TextView t1;
        ImageView i1;
        i1 = (ImageView) row.findViewById(R.id.drawer_icon);
        t1 = (TextView) row.findViewById(R.id.drawer_title);
        t1.setText(this.title[position]);
        i1.setImageResource(icon[position]);

        return (row);
    }
}
