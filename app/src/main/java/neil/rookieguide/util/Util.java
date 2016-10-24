package neil.rookieguide.util;

import android.content.res.Resources;

/**
 * Created by neil on 16/10/24.
 */
public class Util {

    public static int pxToDp(int px){
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public static int dpToPx(int dp){
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}
