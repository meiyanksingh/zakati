package com.zakati.base;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.ta.R;
import com.ta.models.events.LeftDrawerToggleEvent;
import com.ta.models.events.RightDrawerToggleEvent;
import com.ta.ui.mkloader.MKLoader;
import com.ta.utils.Lg;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by Rahil on 11/14/2015.
 * This is the base class of the activities that has tool bar with navigation drawer
 */
public abstract class BaseNavigationActivity extends BaseActivity {

    protected Toolbar mToolbar;
    protected DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    protected MKLoader mToolbarLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void setUpToolBar(int titleResId, int menu) {
        setUpToolBar(getString(titleResId), menu);
    }

    protected void setUpToolBar(int titleResId, int menu, Toolbar.OnMenuItemClickListener listener) {
        setUpToolBar(getString(titleResId), menu);
        mToolbar.setOnMenuItemClickListener(listener);
    }

    protected void setUpToolBar(String titleResId, int menu, Toolbar.OnMenuItemClickListener listener) {
        setUpToolBar(titleResId, menu);
        mToolbar.setOnMenuItemClickListener(listener);
    }

    protected void setUpToolBar(String title, int menu) {
        setUpToolBar(title);
        mToolbar.inflateMenu(menu);
    }


    protected MKLoader getToolbarLoader() {
        return mToolbarLoader;
    }

    /**
     * @param titleResId string resources id for title
     */
    protected void setUpToolBar(int titleResId) {
        setUpToolBar(getString(titleResId));
    }

    /**
     * @param title string for title
     */
    protected void setUpToolBar(int toolBarId, String title) {
        mToolbar = (Toolbar) findViewById(toolBarId);
        if (mToolbar != null) {
            mToolbar.setTitle(title);
            mToolbarLoader = (MKLoader) mToolbar.findViewById(R.id.toolbar_loader);
        }


    }

    protected void showHeaderLogo() {
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        if (mToolbar != null) {
            showView(mToolbar.findViewById(R.id.header_logo_iv));
        } else {
            Lg.e(TAG, "can not find toolbar ");
        }
    }

    protected void hideHeaderLogo() {
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        if (mToolbar != null) {
            hideView(mToolbar.findViewById(R.id.header_logo_iv));
        } else {
            Lg.e(TAG, "can not find toolbar ");
        }
    }


    protected void setUpToolBar(String title) {
        setUpToolBar(R.id.tool_bar, title);
    }

    /**
     * This method sync the navigation drawer and the home icon,it also broadcasts the drawer open and close event
     *
     * @param drawerId
     */
    protected void setupNavigation(int drawerId) {
        mDrawerLayout = (DrawerLayout) findViewById(drawerId);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.lbl_drawer_open,
                R.string.lbl_drawer_close) {
            //comment below if drawer callbacks are needed
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                Lg.e(TAG, "drawer opened");
                EventBus.getDefault().post(new LeftDrawerToggleEvent(true));
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                Lg.e(TAG, "drawer closed");
                EventBus.getDefault().post(new LeftDrawerToggleEvent(false));
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        //if you want to handle drawer navigation explicitly comment the code below
        mDrawerToggle.syncState();
    }

    protected void setupNavigation(int drawerId, int homeIconId) {
        setupNavigation(drawerId);
        mDrawerToggle.setDrawerIndicatorEnabled(false);
        mDrawerToggle.setHomeAsUpIndicator(homeIconId);
        mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
    }


    protected void setupRightDrawer(int drawerId, final int toggleMenuId, final Toolbar.OnMenuItemClickListener listener) {
        mDrawerLayout = (DrawerLayout) findViewById(drawerId);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.lbl_drawer_open,
                R.string.lbl_drawer_close) {
            //comment below if drawer callbacks are needed
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                Lg.e(TAG, "drawer opened");
                EventBus.getDefault().post(new RightDrawerToggleEvent(true));
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                Lg.e(TAG, "drawer closed");
                EventBus.getDefault().post(new RightDrawerToggleEvent(false));
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.setDrawerIndicatorEnabled(false);
        mDrawerToggle.setHomeAsUpIndicator(R.drawable.shape_back_arrow);
        mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == toggleMenuId) {
                    if (mDrawerLayout.isDrawerOpen(GravityCompat.END)) {
                        mDrawerLayout.closeDrawer(GravityCompat.END);
                    } else {
                        mDrawerLayout.openDrawer(GravityCompat.END);
                    }
                    return true;
                } else {
                    listener.onMenuItemClick(item);
                }
                return false;
            }
        });
    }

    public void closeDrawer() {
        mDrawerLayout.closeDrawers();
    }


    @Override
    public void onBackPressed() {
        if (mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else
            super.onBackPressed();
    }


}
