package neil.rookieguide.view.shape;

import android.graphics.Canvas;
import android.graphics.Paint;

import neil.rookieguide.view.target.Target;


/**
 * Created by neil on 16/10/11.
 */
public class Rect {

    private Target target;

    public Rect(Target target){
        this.target = target;
    }

    /**
     * 用橡皮擦掉这一块的浮层
     * */
    public void draw(Canvas canvas, Paint eraser){

        canvas.drawRect(new android.graphics.Rect(target.getRect()), eraser);
    }


}
