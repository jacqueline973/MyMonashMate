package com.monash.mymonashmate.custom;

import android.graphics.LightingColorFilter;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;

public class ButtonEffect {
	
    public static void setupButtonEffect(Button button) {
    	button.setOnTouchListener(new OnTouchListener() {
    	    @Override
    	    public boolean onTouch(View v, MotionEvent event) {
    	        switch (event.getAction()) {
    	            case MotionEvent.ACTION_DOWN:
    	                // 0x6D6D6D sets how much to darken - tweak as desired
    	                setColorFilter(v, 0x6D6D6D);
    	                break;
    	            // remove the filter when moving off the button
    	            // the same way a selector implementation would 
    	            case MotionEvent.ACTION_MOVE:
    	                Rect r = new Rect();
    	                v.getLocalVisibleRect(r);
    	                if (!r.contains((int) event.getX(), (int) event.getY())) {
    	                    setColorFilter(v, null);
    	                }
    	                break;
    	            case MotionEvent.ACTION_OUTSIDE:
    	            case MotionEvent.ACTION_CANCEL:
    	            case MotionEvent.ACTION_UP:
    	                setColorFilter(v, null);
    	                break;
    	        }
    	        return false;
    	    }
    	    private void setColorFilter(View v, Integer filter) {
    	        if (filter == null) v.getBackground().clearColorFilter();
    	        else {
    	            // To lighten instead of darken, try this:
    	            // LightingColorFilter lighten = new LightingColorFilter(0xFFFFFF, filter);
    	            LightingColorFilter darken = new LightingColorFilter(filter, 0x000000);
    	            v.getBackground().setColorFilter(darken);
    	        }
    	        // required on Android 2.3.7 for filter change to take effect (but not on 4.0.4)
    	        v.getBackground().invalidateSelf();
    	    }
    	});
    }

}
