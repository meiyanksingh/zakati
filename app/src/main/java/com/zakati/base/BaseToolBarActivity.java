package com.zakati.base;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.MenuRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ta.R;
import com.ta.ui.mkloader.MKLoader;
import com.ta.utils.Lg;


/**
 * Created by Rahil on 11/14/2015.
 * Base class for the activities which has simple toolbar with simple home icon or with back_icon/exit icon
 */
public abstract class BaseToolBarActivity extends BaseActivity {

    protected Toolbar mToolbar;
    protected MKLoader mToolbarLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * base method to integrate toolbar with the activity
     *
     * @param title         string for title
     * @param homeIconResId drawable resource for home icon
     */
    protected void setUpToolbar(String title, @DrawableRes int homeIconResId) {
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        if (mToolbar != null) {
            mToolbar.setTitle(title);
            mToolbar.setNavigationIcon(homeIconResId);
            mToolbarLoader = (MKLoader) mToolbar.findViewById(R.id.toolbar_loader);
        } else {
            Lg.e(TAG, "can not find toolbar ");
        }
        //setSupportActionBar(mToolbar);
    }

    public MKLoader getToolbarLoader(){
        return mToolbarLoader;
    }


    /**
     * method to show forkspot logo in toolbar
     */
    protected void showHeaderLogo() {
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        if (mToolbar != null) {
            showView(mToolbar.findViewById(R.id.header_logo_iv));
        } else {
            Lg.e(TAG, "can not find toolbar ");
        }
    }

    /**
     * method to integrate toolbar with the activity
     *
     * @param titleId       string resources id for title
     * @param homeIconResId drawable resource for home icon
     */
    protected void setUpToolbar(int titleId, @DrawableRes int homeIconResId) {
        setUpToolbar(getString(titleId), homeIconResId, true);
    }

    protected void setUpToolbar(int titleId) {
        setUpToolbar(getString(titleId), R.drawable.shape_back_arrow, true);
    }

    protected void setUpToolbar(String title) {
        setUpToolbar(title, R.drawable.shape_back_arrow, true);
    }

    protected void setUpToolbar(@StringRes int titleRes, @MenuRes int menuRes, Toolbar.OnMenuItemClickListener menuClickListener) {
        setUpToolbar(getString(titleRes), menuRes, menuClickListener);
    }

    protected void setUpToolbar(String title, @MenuRes int menuRes, Toolbar.OnMenuItemClickListener menuClickListener) {
        setUpToolbar(title, R.drawable.shape_back_arrow, true);
        mToolbar.inflateMenu(menuRes);
        mToolbar.setOnMenuItemClickListener(menuClickListener);
    }

    /**
     * @param titleResId    string resource id for title
     * @param backIconResId drawable resource for back_icon or exit icon
     * @param isBackEnabled boolean if true it will automatically call onBackPressed() on clicking exit or back_icon icon
     */
    protected void setUpToolbar(int titleResId, int backIconResId, boolean isBackEnabled) {
        setUpToolbar(getString(titleResId), backIconResId, isBackEnabled);
    }

    /**
     * @param title         string for title
     * @param backIconResId drawable resource for back_icon or exit icon
     * @param isBackEnabled boolean if true it will automatically call onBackPressed() on clicking exit or back_icon icon
     */
    protected void setUpToolbar(String title, int backIconResId, boolean isBackEnabled) {
        setUpToolbar(title, backIconResId);
        if (isBackEnabled) {
           /* mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if (item.getItemId() == android.R.id.home) {
                        onBackPressed();
                        return true;
                    }
                    return false;
                }
            });*/
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }


    protected void setUpToolbar(@StringRes int titleResId, @DrawableRes int backIconResId, boolean isBackEnabled, @MenuRes int menu, Toolbar.OnMenuItemClickListener listener) {
        setUpToolbar(getString(titleResId), backIconResId, isBackEnabled);
        mToolbar.inflateMenu(menu);
        mToolbar.setOnMenuItemClickListener(listener);
    }
}
