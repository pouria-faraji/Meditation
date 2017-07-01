package com.blacksite.meditation;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.Collections;


/**
 * A simple {@link Fragment} subclass.
 */
public class TimelineFragment extends Fragment {
    private ListView timeline_listview;
    private String[] timeLineItemTitles;
    public TimelineFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_timeline, container, false);

        timeLineItemTitles = new String[]{"10", "09", "08", "07" ,"06", "05",
        "04", "03", "02", "01"};
        timeline_listview = (ListView)view.findViewById(R.id.time_line_listview);
        TimeLineListAdapter timeLineListAdapter = new TimeLineListAdapter(getContext(), timeLineItemTitles);
        timeline_listview.setAdapter(timeLineListAdapter);
        //timeline_listview.setSelection(5);

        //timeline_listview.smoothScrollToPosition(5);



        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        final int listView_height = timeline_listview.getHeight();
        final int listView_item_height = listView_height/10;
        timeline_listview.post(new Runnable() {
            @Override
            public void run() {
                //timeline_listview.smoothScrollToPosition(5);
                timeline_listview.smoothScrollToPositionFromTop(5,listView_height/2 - listView_item_height/2 , 2000);
            }
        });
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
    }
}
