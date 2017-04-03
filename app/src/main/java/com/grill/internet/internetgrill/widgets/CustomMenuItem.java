package com.grill.internet.internetgrill.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;

import com.grill.internet.internetgrill.R;

/**
 * Created by denys on 23.03.17.
 */

public class CustomMenuItem extends RelativeLayout {
    public CustomMenuItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTouchListener();
    }

    public CustomMenuItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTouchListener();
    }

    public CustomMenuItem(Context context) {
        super(context);
        setTouchListener();
    }

    private void setTouchListener() {
        int id = getId();
        final AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.55F);
        if (id != R.id.menuMain)
            this.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                   view.startAnimation(buttonClick);
                    return false;
                }
            });
    }
}
