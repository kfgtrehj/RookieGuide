package neil.guide.view.target;

import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;

/**
 * Created by neil on 16/10/11.
 */
public interface Target {
    /**
     * 返回目标中心点
     * 可以使用xy坐标
     * @return
     */
    Point getPoint();

    /**
     * 制造一个矩形,根据View的实际高宽
     * @return
     */
    Rect getRect();

    /**
     * 返回视图
     * @return
     */
    View getView();

    float getWidth();
    float getHeight();
}
