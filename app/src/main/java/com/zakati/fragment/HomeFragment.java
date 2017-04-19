package com.zakati.fragment;


import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.ta.R;
import com.ta.base.BaseFragment;
import com.ta.utils.AnimUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment {




    public HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public void initializeUi(ViewDataBinding binding) {


    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       /* AnimatedVectorDrawableCompat playToResetAnim = AnimatedVectorDrawableCompat.create(getContext(), R.drawable.ic_discover_image);
        ImageView iv = (ImageView) getView().findViewById(R.id.iv);
        iv.setImageDrawable(playToResetAnim);
        playToResetAnim.start();*/

        //AnimUtils.slideAnimator(0,700,getView().findViewById(R.id.v)).start();
        getView().findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimUtils.expand2( getView().findViewById(R.id.vx));
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    public static HomeFragment newInstance() {
       return new HomeFragment();
    }

    public static HomeFragment newInstance(Bundle args) {
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return new HomeFragment();
    }
}
