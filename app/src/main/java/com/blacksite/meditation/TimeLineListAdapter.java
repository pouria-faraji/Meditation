package com.blacksite.meditation;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by pouria on 7/1/2017.
 */
public class TimeLineListAdapter extends BaseAdapter {

    private Context mContext;
    private String[]  title;

    public TimeLineListAdapter(Context context, String[] titles){
        mContext = context;
        title = titles;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View row;
        row = inflater.inflate(R.layout.timeline_item, viewGroup, false);
        TextView t1;
        t1 = (TextView) row.findViewById(R.id.timeline_item_textview);
        t1.setText(this.title[i]);

        View timeline_item = (View)row.findViewById(R.id.timeline_item_circle);
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mContext, String.valueOf(i), Toast.LENGTH_SHORT).show();
                Intent player_intent = new Intent(mContext, AudioActivity.class);
                player_intent.putExtra("title", title[i]);
                mContext.startActivity(player_intent);
            }
        });

        return row;
    }
}
