package neil.guide.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import neil.guide.R;
import neil.guide.util.Utils;
import neil.guide.view.shape.Rect;
import neil.guide.view.target.RookieContextVLP;
import neil.guide.view.target.ViewGravity;
import neil.guide.view.target.ViewTarget;


/**
 * Created by neil on 16/10/11.
 * 这是一个遮罩视图, 实现思路,绘制一个半透明的背景颜色.需要进行引导的视图,放入进来,然后进行擦除.
 */
public class RookieGuide extends RelativeLayout {

    private Rect[] rectShapes;

    private int positiveButtonRes = 0;

    private Handler handler;
    private ImageView positiveImage;



    private int PositiveButtonMargin = 60;



    /**
     * 橡皮擦
     * */
    private Paint eraser;

    /**
     * Layout width/height
     */
    private int width;
    private int height;

    private ViewTarget viewTargets[];

    /**
     * Check using this Id whether user learned
     * or not.
     */
    private int rookieViewId = 100000;



    /**
     * All views will be drawn to
     * this bitmap and canvas then
     * bitmap will be drawn to canvas
     */
    private Bitmap bitmap;
    private Canvas canvas;

    public RookieGuide(Context context) {
        super(context);
        init(context);
    }

    public RookieGuide(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        //如果在当前的view上面不做任何的绘制操作，需要设置这个标记以便将来的更好的需要，默认的，这个标记在View里是不设定的。
        setWillNotDraw(false);
        setVisibility(INVISIBLE);
        eraser = new Paint();
        eraser.setColor(0xFFFFFFFF);
        eraser.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        eraser.setFlags(Paint.ANTI_ALIAS_FLAG);
        handler = new Handler();


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width = getMeasuredWidth();
        height = getMeasuredHeight();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    /**
     * 点击事件
     * */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //获取屏幕上点击的坐标
        float xT = event.getX();
        float yT = event.getY();
        int[] location = new int[2];
        positiveImage.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];


        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_DOWN:
                if(!(xT < x || xT > (x + positiveImage.getWidth()) || yT < y || yT > (y + positiveImage.getHeight()))) {
                    removeRookieGuidelView();
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:

                return true;
        }
        //这句话不要修改
        return true;
    }
    /**
     * 移除
     * */
    private void removeRookieGuidelView(){
        if(getParent() != null )
            ((ViewGroup) getParent()).removeView(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (bitmap == null || canvas == null) {
            if (bitmap != null) bitmap.recycle();

            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            this.canvas = new Canvas(bitmap);
        }
        //背景的遮罩.
        this.canvas.drawColor(0x77000000);
//        this.canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        for (Rect rect:rectShapes) {
            rect.draw(this.canvas,eraser);
        }

        canvas.drawBitmap(bitmap, 0, 0, null);

    }
    private void show(Activity activity){

        ((ViewGroup) activity.getWindow().getDecorView()).addView(this);

        handler.post(new Runnable() {
            @Override
            public void run() {
                setVisibility(VISIBLE);
            }
        });
        measure(0,0);

    }

    /**
     *  内容区域
     *  拿到已擦除的点,根据点的位置放置tips ......
     * */
    private void addTipsView(RookieContextVLP... rookieContextVLPs){

        final AbsoluteLayout contextView = (AbsoluteLayout) LayoutInflater.from(getContext()).inflate(R.layout.rookie_content_view, null);
        int index = 0;
        for(RookieContextVLP rookieContextVLP : rookieContextVLPs){

            final ImageView imageView = new ImageView(getContext());
            imageView.setImageResource(rookieContextVLP.getResourceId());
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            int[] location = new int[2];
            location[0] = 0;
            location[1] = viewTargets[index].getPoint().y;
            System.out.println("当前View Y点---->"+location[1]);
            RelativeLayout.LayoutParams imageParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            imageParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            imageView.setLayoutParams(imageParams);


            AbsoluteLayout.LayoutParams params = new AbsoluteLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,location[0],location[1]+contextView.getHeight());;
         //   int id =  this.viewTargets[index].getView().getId();


            if(rookieContextVLP.getViewGravity() == ViewGravity.TOP){
                params = new AbsoluteLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,location[0],location[1]- Utils.dpToPx(110));

            }else if(rookieContextVLP.getViewGravity() == ViewGravity.BOTTOM){
                params = new AbsoluteLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,location[0],location[1]+ Utils.dpToPx(60));
            }
            contextView.addView(imageView,params);
//            addView(contextView,params);
            System.out.println("当前位置---->"+params.x + params.y);
            index ++ ;
        }
        addView(contextView,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        addView(contextView);

    }
    /**
     * 添加确认按钮
     * */
    private void addPositiveButton(int resId){

        final View layoutInfo = LayoutInflater.from(getContext()).inflate(R.layout.poistive_button, null);
        positiveImage = (ImageView) layoutInfo.findViewById(R.id.positiveImage);
        final View infoView = layoutInfo.findViewById(R.id.info_layout);
        positiveImage.setImageResource(resId);
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (infoView.getParent() != null)
                    ((ViewGroup) layoutInfo.getParent()).removeView(infoView);

                RelativeLayout.LayoutParams infoDialogParams = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                ((RelativeLayout) infoView).setGravity(Gravity.BOTTOM);

                infoDialogParams.setMargins(
                        0,
                        0,
                        0,
                        Utils.dpToPx(PositiveButtonMargin));
                infoView.setLayoutParams(infoDialogParams);
                infoView.postInvalidate();

                RookieGuide.this.addView(infoView);
            }
        });


    }


    public int getPositiveButtonMargin() {
        return PositiveButtonMargin;
    }

    public void setPositiveButtonMargin(int positiveButtonMargin) {
        PositiveButtonMargin = positiveButtonMargin;
    }

    public int getPositiveButtonRes() {
        return positiveButtonRes;
    }

    public void setPositiveButtonRes(int positiveButtonRes) {
        positiveButtonRes = positiveButtonRes;

    }

    public Rect[] getRectShape() {
        return rectShapes;
    }

    public void setRectShape(Rect[] rectShape) {
        this.rectShapes = rectShape;
    }

    public Paint getEraser() {
        return eraser;
    }

    public void setEraser(Paint eraser) {
        this.eraser = eraser;
    }

    public ViewTarget[] getViewTarget() {
        return viewTargets;
    }

    public void setViewTarget(ViewTarget[] viewTarget) {
        this.viewTargets = viewTarget;
    }
    public static class Builder{

        private RookieGuide rookieGuide;
        private Activity activity;

        public Builder(Activity activity){
            this.activity = activity;
            rookieGuide = new RookieGuide(activity);

        }
        public Builder setTarget(View... views) {
            ViewTarget[] viewTargets = new ViewTarget[views.length];
            for(int i = 0; i < views.length; i ++){
                viewTargets[i] = new ViewTarget(views[i]);

            }
            rookieGuide.setViewTarget(viewTargets);
            return this;
        }
        public Builder setPositiveRes(int resId){
            rookieGuide.setPositiveButtonRes(resId);
            rookieGuide.addPositiveButton(resId);
            return this;
        }
        public Builder setPositiveMargin(int margin){
            rookieGuide.setPositiveButtonMargin(margin);
            return this;
        }
        public Builder addTipsView(RookieContextVLP ... rookieContextVLPs){
            rookieGuide.addTipsView(rookieContextVLPs);
            return this;
        }
        public RookieGuide build() {
            Rect[] rects = new Rect[rookieGuide.viewTargets.length];
            for(int i = 0; i < rookieGuide.viewTargets.length; i ++){
                rects[i] = new Rect(rookieGuide.viewTargets[i]);
            }
            rookieGuide.setRectShape(rects);
            return rookieGuide;
        }
        public RookieGuide show(){
            build().show(activity);
            return rookieGuide;
        }
    }
}
