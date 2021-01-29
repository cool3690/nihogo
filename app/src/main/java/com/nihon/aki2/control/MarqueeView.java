package com.nihon.aki2.control;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;

import com.nihon.aki2.R;

/**
 * <p>作者   wurui</p>
 * <p>时间   2018/11/28 0028</p>
 * <p>包名   com.testanimdemo.widget</p>
 * <p>描述   水平滚动的跑马灯效果</p>
 */
public class MarqueeView extends HorizontalScrollView implements Runnable {
int x=0;
    private Context context;
    private RelativeLayout mainLayout;//跑马灯滚动部分

    private View parentView;//自定义父类滚动容器
    private boolean hasParent=false;//默认没有父容器

    private int scrollSpeed = 25;//滚动速度
    private int scrollDirection = DOWN_TO_UP;//滚动方向
    private int currentY;//当前x坐标
    private int viewMargin = 20;//View间距
    private int viewHeight;//View总宽度
    private int screenHeight;//屏幕宽度

    public static final int DOWN_TO_UP = 1;
   // public static final int RIGHT_TO_LEFT = 2;

    public MarqueeView(Context context) {
        this(context, null);
    }

    public MarqueeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MarqueeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        initView();
    }

    void initView() {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);

        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        screenHeight = dm.heightPixels;
        if(hasParent){
            this.addView(parentView);
        }else{
            mainLayout = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.scroll_content, null);
            this.addView(mainLayout);
        }
    }

    /**
     * 添加自定义滚动视图
     * @param parentView
     */
    public void setParentView(View parentView){
       this.parentView=parentView;
        hasParent=true;
    }

    /**
     * 添加单个视图  如TextView imageView等等
     * @param view
     */
    public void addViewInQueue(View view){
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(viewMargin, 0, 0, 0);
        view.setLayoutParams(lp);
        if(!hasParent){
            mainLayout.addView(view);
        }
        view.measure(0, 0);//测量view
        viewHeight = viewHeight + view.getMeasuredHeight() + viewMargin;
    }

    //开始滚动
    public void startScroll(){
        removeCallbacks(this);

        currentY = (scrollDirection == DOWN_TO_UP ? viewHeight : -screenHeight);
         post(this);

    }

    //停止滚动
    public void stopScroll(){
        removeCallbacks(this);
    }

    //设置View间距
    public void setViewMargin(int viewMargin){
        this.viewMargin = viewMargin;
    }

    //设置滚动速度
    public void setScrollSpeed(int scrollSpeed){
        this.scrollSpeed = scrollSpeed;
    }

    //设置滚动方向 默认从左向右
    public void setScrollDirection(int scrollDirection){
        this.scrollDirection = scrollDirection;
    }

    @Override
    public void run() {
        switch (scrollDirection){
            case DOWN_TO_UP:
                if(hasParent){
                    parentView.scrollTo(0, currentY);
                }else{
                    mainLayout.scrollTo(0, currentY);
                }
                currentY ++;
                if (currentY>= 1500) {
                    if(hasParent){
                        parentView.scrollTo(0, viewHeight);
                    }else{
                        mainLayout.scrollTo(0, viewHeight);
                    }
                   currentY = viewHeight;
                    //mainLayout.setVisibility(View.GONE);
                }
                break;
                /*
            case RIGHT_TO_LEFT:
                if(hasParent){
                    parentView.scrollTo(currentX, 0);
                }else{
                    mainLayout.scrollTo(currentX, 0);
                }
                currentX ++;

                if (currentX >= viewHeight) {
                    if(hasParent){
                        parentView.scrollTo(-screenWidth, 0);
                    }else{
                        mainLayout.scrollTo(-screenWidth, 0);
                    }
                    currentX = -screenWidth;
                }
                break;

                 */
            default:
                if(x==1)break;

        }
        postDelayed(this, 40 / scrollSpeed);
        x++;

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
}
