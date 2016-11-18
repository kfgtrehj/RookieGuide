package neil.guide.view.target;

import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;

/**
 * Created by neil on 16/10/11.
 */
public class ViewTarget implements Target{
    private View view;

    public ViewTarget(View view) {
        this.view = view;


    }

    @Override
    public Point getPoint() {

        int[] location = new int[2];
        view.getLocationInWindow(location);
        return new Point(location[0],location[1]);
//        return new Point(location[0] + (view.getWidth() / 2), location[1] + (view.getHeight() / 2));
    }

    /**
     * 根据View形状 返回一个矩形的形状
     * */
    @Override
    public Rect getRect() {
        int[] location = new int[2];
        view.getLocationInWindow(location);
        return new Rect(
                location[0],
                location[1],
                location[0] + view.getWidth(),
                location[1] + view.getHeight()
        );
    }

    @Override
    public View getView() {
        return view;
    }

    @Override
    public float getWidth() {
        return view.getWidth();
    }

    @Override
    public float getHeight() {
        return view.getHeight();
    }

}
