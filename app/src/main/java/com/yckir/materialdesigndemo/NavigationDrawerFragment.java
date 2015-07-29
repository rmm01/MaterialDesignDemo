package com.yckir.materialdesigndemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawerFragment extends Fragment {

    private static final String PREF_FINAL_NAME="testpref";
    private static final String KEY_USER_LEARNED_DRAWER="user_learned_drawer";
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private View containerView;
    //is user aware of drawers existence
    private boolean mUserLearnedDrawer;
    //fragment started for first time or from rotation
    private boolean mFromSavedInstanceState;

    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceBundle){
        super.onCreate(savedInstanceBundle);
        mUserLearnedDrawer=Boolean.valueOf(readFromPreferences(getActivity(),KEY_USER_LEARNED_DRAWER,"false"));
        if(savedInstanceBundle!=null)
            mFromSavedInstanceState=true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
    }


    public void setUp(int fragmentId,DrawerLayout drawerLayout,Toolbar toolbar) {
        containerView=getActivity().findViewById(fragmentId);
        mDrawerLayout=drawerLayout;
        mDrawerToggle= new ActionBarDrawerToggle(getActivity(),drawerLayout,
                toolbar,R.string.drawer_open,R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if(!mUserLearnedDrawer){
                    mUserLearnedDrawer=true;
                    saveToPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, mUserLearnedDrawer + "");
                }
                //redraw appbar
                getActivity().supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().supportInvalidateOptionsMenu();
            }
        };
        if(!mUserLearnedDrawer&& !mFromSavedInstanceState){
            mDrawerLayout.openDrawer(containerView);
        }
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }
    public void saveToPreferences(Context context, String preferenceName,String preferenceValue){
        SharedPreferences sharedPreferences=context.getSharedPreferences(PREF_FINAL_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(preferenceName,preferenceValue);
        editor.apply();

    }
    public static String readFromPreferences(Context context, String preferenceName,String preferenceValue){
        SharedPreferences sharedPreferences=context.getSharedPreferences(PREF_FINAL_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(preferenceName,preferenceValue);


    }
}
