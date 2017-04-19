package com.zakati.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.widget.LinearLayout;

/**
 * Created by rahil on 15/6/16.
 */
public class AnimUtils {
    static ValueAnimator mAnimator;

    public static void listAnimation(View view, boolean goesUp, int position) {

        ObjectAnimator translationY = ObjectAnimator.ofFloat(view, "translationY", goesUp ? -30 : 30, 0);
        translationY.setInterpolator(new FastOutLinearInInterpolator());
        translationY.setDuration(600);
        //animator.start();

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 0.90f, 1f);
        scaleX.setDuration(600);
        scaleX.setInterpolator(new FastOutLinearInInterpolator());

        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 0.90f, 1.0f);
        scaleY.setDuration(600);
        scaleY.setInterpolator(new FastOutLinearInInterpolator());

        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 0.95f, 1.0f);
        scaleY.setDuration(600);
        scaleY.setInterpolator(new FastOutLinearInInterpolator());


        ObjectAnimator translationX = ObjectAnimator.ofFloat(view, "translationX", position % 2 == 0 ? -15 : 15, 0);
        translationX.setInterpolator(new FastOutLinearInInterpolator());
        translationX.setDuration(500);

        ObjectAnimator translationZ = ObjectAnimator.ofFloat(view, "translationZ", -20, 0);
        translationZ.setInterpolator(new FastOutLinearInInterpolator());
        translationZ.setDuration(500);


        AnimatorSet set = new AnimatorSet();
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        // set.setDuration(1000);
        set.playTogether(/*translationX,*/ translationY/*,translationZ*/, scaleX, scaleY/*, alpha*/);
        set.start();


    }


    public static void pulse(View view) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 0.80f, 1.25f);
        scaleX.setRepeatMode(ValueAnimator.REVERSE);
        scaleX.setRepeatCount(ValueAnimator.INFINITE);

        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 0.80f, 1.25f);
        scaleY.setRepeatMode(ValueAnimator.REVERSE);
        scaleY.setRepeatCount(ValueAnimator.INFINITE);

        AnimatorSet set1 = new AnimatorSet();
        set1.playTogether(scaleX, scaleY);
        set1.setInterpolator(new DecelerateInterpolator());
        set1.setDuration(1500);


        set1.start();

      /*  ObjectAnimator scaleX2 = ObjectAnimator.ofFloat(view, "scaleX", 1.25f, 0.80f);
        scaleX2.setRepeatMode(ValueAnimator.REVERSE);
        scaleX2.setRepeatCount(ValueAnimator.INFINITE);

        ObjectAnimator scaleY2 = ObjectAnimator.ofFloat(view, "scaleY", 1.25f, 0.80f);
        scaleY2.setRepeatMode(ValueAnimator.REVERSE);
        scaleY2.setRepeatCount(ValueAnimator.INFINITE);

        AnimatorSet set2 = new AnimatorSet();
        set2.playTogether(scaleX2, scaleY2);
        set2.setDuration(1500);
        set2.setInterpolator(new AnticipateInterpolator());*/

       /* AnimatorSet set3 = new AnimatorSet();
        set3.playTogether(set1, set2);

        set3.start();*/


    }

    public static void circularShow(final View v, int duration) {
        v.animate()
                .scaleX(1f)
                .scaleY(1f)
                .alpha(1f)
                .setDuration(duration)
                .setInterpolator(new LinearOutSlowInInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        Utils.showView(v);
                    }

                });

    }

    public static void circularShow( View v) {
        circularShow(v, 250);

    }

    public static void circularHide(final View v, int duration) {
        if (v==null)
            return;
        try {
            v.animate()
                    .scaleX(0f)
                    .scaleY(0f)
                    .alpha(0f)
                    .setDuration(duration)
                    .setInterpolator(new FastOutLinearInInterpolator())
                    .setListener(new AnimatorListenerAdapter() {

                        @Override
                        public void onAnimationStart(Animator animation) {
                            //Utils.showView(v);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            Utils.invisibleView(v);
                        }

                    });
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void circularHide(final View v) {
        circularHide(v, 250);

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void animateRevealShow(View viewRoot) {
      /*  int cx = (viewRoot.getLeft() + viewRoot.getRight()) / 2;
        int cy = (viewRoot.getTop() + viewRoot.getBottom()) / 2;*/


        int cx = viewRoot.getWidth() / 2;
        int cy = viewRoot.getHeight() / 2;

        int finalRadius = Math.max(viewRoot.getWidth(), viewRoot.getHeight());
        Animator anim = ViewAnimationUtils.createCircularReveal(viewRoot, cx, cy, 0, finalRadius);
        viewRoot.setVisibility(View.VISIBLE);
        // anim.setDuration(1000);
        // anim.setInterpolator(new FastOutSlowInInterpolator());
        anim.start();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void animateRevealShow(View viewRoot, View origin) {
      /*  int cx = (viewRoot.getLeft() + viewRoot.getRight()) / 2;
        int cy = (viewRoot.getTop() + viewRoot.getBottom()) / 2;*/
        if (Build.VERSION.SDK_INT >= 21) {

            int cx = (int) (origin.getX() + origin.getWidth() / 2);
            int cy = (int) (origin.getY() + origin.getHeight() / 2);

            int finalRadius = Math.max(viewRoot.getWidth(), viewRoot.getHeight());
            Animator anim = ViewAnimationUtils.createCircularReveal(viewRoot, cx, cy, 0, finalRadius);
            viewRoot.setVisibility(View.VISIBLE);
            // anim.setInterpolator(new FastOutSlowInInterpolator());
            anim.setDuration(600);
            anim.start();
        } else {
            circularShow(viewRoot, 900);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void animateRevealHide(final View view) {

        // get the center for the clipping circle
        int cx = view.getWidth() / 2;
        int cy = view.getHeight() / 2;

        // get the initial radius for the clipping circle
        float initialRadius = (float) Math.hypot(cx, cy);

        // create the animation (the final radius is zero)
        Animator anim =
                ViewAnimationUtils.createCircularReveal(view, cx, cy, initialRadius, 0);

        // make the view invisible when the animation is done
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.INVISIBLE);
            }
        });

        // start the animation
        anim.start();

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void animateRevealHide(final View view, View origin) {
        if (Build.VERSION.SDK_INT >= 21) {

            // get the center for the clipping circle
            int cx = (int) (origin.getX() + origin.getWidth() / 2);
            int cy = (int) (origin.getY() + origin.getHeight() / 2);

            // get the initial radius for the clipping circle
            float initialRadius = (float) Math.hypot(cx, cy);

            // create the animation (the final radius is zero)
            Animator anim =
                    ViewAnimationUtils.createCircularReveal(view, cx, cy, initialRadius, 0);

            anim.setStartDelay(200);
            // make the view invisible when the animation is done
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    view.setVisibility(View.INVISIBLE);
                }
            });

            // start the animation
            anim.start();
        } else {
            circularHide(view, 400);
        }

    }

    /*int x = (v.getLeft() + v.getRight()) / 2;
            int y = (v.getTop() + v.getBottom()) / 2;
            float radiusOfFab = 1f * v.getWidth() / 2f;
            float radiusFromFabToRoot = (float) Math.hypot(
                    Math.max(x, rootLayout.getWidth() - x),
                    Math.max(y, rootLayout.getHeight() - y));
    */


    public static void categoryAnimation(View view) {
        view.setScaleX(0f);
        view.setScaleY(0f);
        view.setVisibility(View.VISIBLE);

        ObjectAnimator rotation = ObjectAnimator.ofFloat(view, "rotation", -200, 0);
        rotation.setInterpolator(new AnticipateOvershootInterpolator());

        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 0, 1);
        alpha.setInterpolator(new AnticipateOvershootInterpolator());


        AnimatorSet scaleSet = new AnimatorSet();
        scaleSet.playTogether(ObjectAnimator.ofFloat(view, View.SCALE_X, 1f),
                ObjectAnimator.ofFloat(view, View.SCALE_Y, 1f));

        AnimatorSet set = new AnimatorSet();
        set.setDuration(800);
        //set.addListener(listener);
        set.playTogether(scaleSet, rotation, alpha);
        set.start();

    }


    public static void categoryReturnAnimation(final View view) {
        view.setScaleX(1f);
        view.setScaleY(1f);


        ObjectAnimator rotation = ObjectAnimator.ofFloat(view, "rotation", 0, -300);

        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 1, 0);


        AnimatorSet scaleSet = new AnimatorSet();
        scaleSet.playTogether(ObjectAnimator.ofFloat(view, View.SCALE_X, 0f),
                ObjectAnimator.ofFloat(view, View.SCALE_Y, 0f));
        //scaleSet.setInterpolator(new OvershootInterpolator());

        AnimatorSet set = new AnimatorSet();
        set.setDuration(600);
        //set.addListener(listener);
        set.playTogether(scaleSet, rotation, alpha);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.INVISIBLE);
            }
        });
        set.start();

    }


    public static void expand(View contentView) {

        contentView.setVisibility(View.VISIBLE);
        final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        contentView.measure(widthSpec, heightSpec);
        mAnimator = slideAnimator(0, contentView.getMeasuredHeight(), contentView);
        mAnimator.setDuration(3000);
        mAnimator.start();
    }

    public static void collapse(final View contentView) {

        int finalHeight = contentView.getHeight();
        ValueAnimator mAnimator = slideAnimator(finalHeight, 1, contentView);
        mAnimator.setDuration(500);

        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
                //Height=0, but it set visibility to GONE
                //ApplicationUtils.hideKeyboard(getActivity());
                contentView.setVisibility(View.GONE);
                // setScrollViewHeight(false);
            }

            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        mAnimator.start();
    }

    public static ValueAnimator slideAnimator(int start, int end, final View contentView) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //Update Height
                int value = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
                layoutParams.height = value;
                contentView.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }



    public static void expand2(final View v) {
        v.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? ViewGroup.LayoutParams.WRAP_CONTENT
                        : (int)(targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static void collapse2(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    v.setVisibility(View.GONE);
                }else{
                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }


}

