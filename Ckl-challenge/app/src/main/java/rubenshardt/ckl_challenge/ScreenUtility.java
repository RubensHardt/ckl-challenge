package rubenshardt.ckl_challenge;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.Display;

/**
 * Created by rubenshardtjunior on 11/29/16.
 */
//get the width screen in dp
public class ScreenUtility {

    private float dpWidth;

    public ScreenUtility(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float density = activity.getResources().getDisplayMetrics().density;
        dpWidth = outMetrics.widthPixels / density;
    }

    public float getWidth() {
        return dpWidth;
    }
}
