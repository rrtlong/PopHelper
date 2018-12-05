package com.moli.module.widget.widget.dialog.base;

import android.animation.ObjectAnimator;
import android.util.DisplayMetrics;
import android.view.View;

public class BounceTopEnter extends BaseAnimatorSet {
    public BounceTopEnter() {
        duration = 500;
    }

    @Override
    public void setAnimation(View view) {
        DisplayMetrics dm = view.getContext().getResources().getDisplayMetrics();
        animatorSet.playTogether(ObjectAnimator.ofFloat(view, "alpha", 0, 1, 1, 1),//
                ObjectAnimator.ofFloat(view, "translationY", -250 * dm.density, 30, -100, 0));

    }
}
