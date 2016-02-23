/*
 * Copyright [2016] [UFMS]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ufms.br.com.ufmsapp.fragment;


import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ufms.br.com.ufmsapp.R;
import ufms.br.com.ufmsapp.adapter.DrawerLayoutAdapter;
import ufms.br.com.ufmsapp.pojo.DrawerItem;


public class NavigationDrawerFragment extends Fragment {

    private static final String PREF_FILE_NAME = "drawer_preference";
    private static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";

    ActionBarDrawerToggle mDrawerToggle;
    DrawerLayout mDrawerLayout;

    boolean mUserLearnedDrawer;
    boolean mFromSavedInstanceState;

    View containerView;

    DrawerLayoutAdapter adapter;

    RecyclerView mListDrawer;

    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    public static List<DrawerItem> getData() {
        List<DrawerItem> data = new ArrayList<>();

        int[] icons = {android.R.drawable.ic_dialog_info, android.R.drawable.ic_dialog_email, android.R.drawable.ic_dialog_alert, android.R.drawable.ic_dialog_map};
        String[] titles = {"Eventos", "Disciplinas", "Notas", "Curso"};

        for (int i = 0; i < icons.length && i < titles.length; i++) {
            DrawerItem drawerItem = new DrawerItem();
            drawerItem.setIconRes(icons[i]);
            drawerItem.setItemTitle(titles[i]);

            data.add(drawerItem);
        }

        return data;
    }

    public static void saveToPreference(Context context, String preferenceName, String preferenceValue) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();
    }

    public static String readFromPreference(Context context, String preferenceName, String defValue) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        return prefs.getString(preferenceName, defValue);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUserLearnedDrawer = Boolean.valueOf(readFromPreference(getActivity(), KEY_USER_LEARNED_DRAWER, "false"));

        if (savedInstanceState != null) {
            mFromSavedInstanceState = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);

        mListDrawer = (RecyclerView) view.findViewById(R.id.drawer_list);
        adapter = new DrawerLayoutAdapter(getActivity(), getData());
        mListDrawer.setAdapter(adapter);

        mListDrawer.setLayoutManager(new LinearLayoutManager(getActivity()));

        mListDrawer.addOnItemTouchListener(new RecyclerViewTouchListener(getActivity(), mListDrawer, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(getActivity(), "onClick " + position, Toast.LENGTH_SHORT).show();
                mDrawerLayout.closeDrawer(GravityCompat.START);
               // ((MainActivity) getActivity()).onDrawerItemClicked(position - 1);
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));

        return view;
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        mDrawerLayout = drawerLayout;
        containerView = getActivity().findViewById(fragmentId);

        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;

                    //saveToPreference(getActivity(), KEY_USER_LEARNED_DRAWER, mUserLearnedDrawer + "");
                    saveToPreference(getActivity(), KEY_USER_LEARNED_DRAWER, "true");
                }

                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                if (slideOffset < 0.6) {
                    toolbar.setAlpha(1 - slideOffset);
                }

            }
        };

        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(containerView);
        }

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
                if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
                    mDrawerLayout.openDrawer(containerView);
                }
            }
        });
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    class RecyclerViewTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector detector;
        private ClickListener listener;

        public RecyclerViewTouchListener(Context context, final RecyclerView recyclerView, final ClickListener listener) {
            this.listener = listener;

            detector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View childView = mListDrawer.findChildViewUnder(e.getX(), e.getY());

                    if (childView != null && listener != null) {
                        listener.onLongClick(childView, recyclerView.getChildAdapterPosition(childView));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {


            View childView = rv.findChildViewUnder(e.getX(), e.getY());

            if (childView != null && listener != null && detector.onTouchEvent(e)) {
                listener.onClick(childView, rv.getChildAdapterPosition(childView));
            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

}
