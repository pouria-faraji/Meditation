package com.blacksite.meditation.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.blacksite.meditation.fragments.MainFragment;
import com.blacksite.meditation.preference.PrefManager;
import com.blacksite.meditation.R;
import com.blacksite.meditation.adapters.DrawerListAdapter;

public class MainActivity extends AppCompatActivity {
    View customMenu;

    private String[] mainPageDrawerTitles;
    private int[] mainPageDrawerIcons = {R.drawable.ic_home, R.drawable.ic_logout};
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private CharSequence mTitle;
    private LinearLayout mLinearLayoutDrawer;

    private CharSequence mDrawerTitle;
    private ActionBarDrawerToggle mDrawerToggle;

    private PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createMenu();

        prefManager = new PrefManager(this);

        mainPageDrawerTitles = new String[]{getString(R.string.home), getString(R.string.logout)};
        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mLinearLayoutDrawer = (LinearLayout) findViewById(R.id.drawer_linear_layout);

        /*mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mainPageDrawerTitles));*/
        //mDrawerList.setAdapter(new ArrayAdapter<Pair<String, Integer>>(this, R.layout.drawer_list_item, mainPageDrawerTitles,mainPageDrawerIcons));

        DrawerListAdapter drawerListAdapter = new DrawerListAdapter(this, mainPageDrawerTitles, mainPageDrawerIcons);
        mDrawerList.setAdapter(drawerListAdapter);

        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //getSupportActionBar().setTitle(mTitle);
                TextView actionbarTitle = (TextView)customMenu.findViewById(R.id.action_bar_title);
                actionbarTitle.setText(mTitle);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //getSupportActionBar().setTitle(mDrawerTitle);
                TextView actionbarTitle = (TextView)customMenu.findViewById(R.id.action_bar_title);
                actionbarTitle.setText(mDrawerTitle);
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        //It is for navigation drawer from left side
        /*getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);*/

        selectItem(0);
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public void createMenu(){
        ActionBar actionBar = getSupportActionBar();
        //getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        //getSupportActionBar().setHomeButtonEnabled(false);
        LayoutInflater li = LayoutInflater.from(this);
        customMenu = li.inflate(R.layout.custom_menu, null);
        actionBar.setCustomView(customMenu);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        Toolbar toolbar = (Toolbar)actionBar.getCustomView().getParent();
        toolbar.setContentInsetsAbsolute(0,0);
        toolbar.getContentInsetEnd();
        toolbar.setPadding(0, 0, 0, 0);
        //actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setElevation(0);
        ImageView drawer_icon = (ImageView)customMenu.findViewById(R.id.action_drawer);
        drawer_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                    mDrawerLayout.closeDrawer(Gravity.RIGHT);
                }
                else {
                    mDrawerLayout.openDrawer(Gravity.RIGHT);
                }
            }
        });
    }
    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        /*if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        if (item != null && item.getItemId() == R.id.drawer_icon_action_bar) {
            if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                mDrawerLayout.closeDrawer(Gravity.RIGHT);
            }
            else {
                mDrawerLayout.openDrawer(Gravity.RIGHT);
            }
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }*/
    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }
    private void selectItem(int position) {
        Fragment drawer_fragment = null;
        switch (position){
            case 0:
                drawer_fragment = new MainFragment();
                break;
            case 1:
                //drawer_fragment = new MainFragment();
                launchWelcomeScreen();
                return;
                //break;
            default:
                break;
        }

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, drawer_fragment);
        fragmentTransaction.commit();
        // Insert the fragment by replacing any existing fragment
        /*FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, drawer_fragment)
                .commit();*/

        // Highlight the selected item, update the title, and close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mainPageDrawerTitles[position]);
        //mDrawerLayout.closeDrawer(mDrawerList);
        mDrawerLayout.closeDrawer(mLinearLayoutDrawer);
    }
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }
    private void launchWelcomeScreen() {
        prefManager.setFirstTimeLaunch(true);
        startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
        finish();
    }
}
