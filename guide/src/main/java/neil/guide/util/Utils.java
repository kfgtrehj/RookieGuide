package neil.guide.util;

import android.content.res.Resources;

/**
 * Created by neil on 16/10/28.
 */
public class Utils {
    public static int pxToDp(int px){
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public static int dpToPx(int dp){
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}
