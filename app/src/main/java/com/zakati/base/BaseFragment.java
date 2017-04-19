package com.zakati.base;


import android.app.ProgressDialog;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ta.R;
import com.ta.api.ApiClient;
import com.ta.api.Apis;
import com.ta.interfaces.BaseInterfaces;
import com.ta.ui.mkloader.MKLoader;
import com.ta.utils.DialogUtil;
import com.ta.utils.Lg;
import com.ta.utils.PrefMgr;
import com.ta.utils.Utils;

import java.lang.reflect.Field;


/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends DialogFragment implements BaseInterfaces {
    protected BaseActivity mBaseActivity;
    protected String TAG = "";
    protected Resources mRes;
    protected Apis mApis;
    private ProgressDialog mProgressDialog;
    protected MKLoader mProgress;
    protected View mView;
    private PrefMgr mPrefMgr;


    //  protected LogInRespBean.LogInData mUserData;

    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = getClass().getSimpleName();
        mBaseActivity = (BaseActivity) getActivity();

        mRes = getResources();
        mApis = ApiClient.getClient().getApis();
        mPrefMgr = PrefMgr.get();
    }

    /*protected ProgressWheel getLoader() {
        return mBaseActivity.getLoader();
    }*/

    protected MKLoader getToolbarLoader() {
        if (getActivity() instanceof BaseToolBarActivity) {
            return ((BaseToolBarActivity) getActivity()).getToolbarLoader();
        }

        if (getActivity() instanceof BaseNavigationActivity) {
            return ((BaseNavigationActivity) getActivity()).getToolbarLoader();
        }
        return null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewDataBinding binding = DataBindingUtil.inflate(
                inflater, getLayoutId(), container, false);
        mView = binding.getRoot();
        mProgress = (MKLoader) mView.findViewById(R.id.loader);
        initializeUi(binding);
        return mView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    public boolean toggleEmptyView(boolean isEmptyVisible, @IdRes int toBeHide, @IdRes int emptyView) {
        return mBaseActivity.toggleEmptyView(isEmptyVisible,toBeHide,emptyView);
    }

    protected void removeFragment(Class<? extends Fragment> clazz) {
        String fragmentName = clazz.getSimpleName();
        Fragment fragment = getChildFragmentManager().findFragmentByTag(fragmentName);
        getChildFragmentManager().beginTransaction().remove(fragment).commitAllowingStateLoss();
    }

    @Override
    public void onDestroyView() {
        Utils.hideKeyboard(getContext());
        super.onDestroyView();
    }

    protected void closeProgressDialog() {

        DialogUtil.hideProgressDialog(mProgressDialog);
    }

    protected void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = DialogUtil.showProgressDialog(getContext());
        }
        mProgressDialog.show();
    }
/*
    protected void showLoader() {
        DialogUtil.showProgressWheel(mProgress);
    }


    protected void hideLoader() {
        DialogUtil.showProgressWheel(mProgress);
    }*/

    protected void pushFragment(int containerId, Fragment fragment) {
        if (getActivity() != null) {
            getChildFragmentManager().beginTransaction()
                    .replace(containerId, fragment, fragment.getClass().getSimpleName()).commit();
        }
    }


    public String getName() {
        return getClass().getSimpleName();
    }

    public String getTrimmedText(TextView textView) {
        return textView.getText().toString().trim();
    }


    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void removeFragment(String tag) {
        Fragment fragment = getChildFragmentManager().findFragmentByTag(tag);
        if (fragment != null)
            getChildFragmentManager().beginTransaction().remove(fragment).commit();
        else
            Lg.e(TAG, "Fragment with tag : " + tag + " not found");
    }

    protected void removeFragment(int containerId) {
        Fragment fragment = getChildFragmentManager().findFragmentById(containerId);
        if (fragment != null)
            getChildFragmentManager().beginTransaction().remove(fragment).commit();
        else
            Lg.e(TAG, "Fragment not found");
    }
}
