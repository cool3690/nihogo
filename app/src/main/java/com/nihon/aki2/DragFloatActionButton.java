package com.nihon.aki2;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DragFloatActionButton extends FloatingActionButton {



    private int parentHeight;
    private int parentWidth;
    int dx=0,dy=0;
    public DragFloatActionButton(Context context) {
        super(context);

    }

    public DragFloatActionButton(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public DragFloatActionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }


    private int lastX;
    private int lastY;
    int rawX; int rawY;
    private boolean isDrag;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
       rawX = (int) event.getRawX();
         rawY = (int) event.getRawY();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                setPressed(true);
                isDrag=false;
                getParent().requestDisallowInterceptTouchEvent(true);
                lastX=rawX;
                lastY=rawY;
                ViewGroup parent;
                if(getParent()!=null){
                    parent= (ViewGroup) getParent();
                    parentHeight=parent.getHeight();
                    parentWidth=parent.getWidth();

                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(parentHeight<=10||parentWidth<=10){
                    isDrag=false;
                    break;
                }else {
                    isDrag=true;
                }
                 dx=rawX-lastX;
                 dy=rawY-lastY;

                //这里修复一些华为手机无法触发点击事件
                int distance= (int) Math.sqrt(dx*dx+dy*dy);
                if(distance==0){
                    isDrag=false;
                    break;
                }
                float x=getX()+dx;
                float y=getY()+dy;

                //检测是否到达边缘 左上右下
                x=x<0?0:x>parentWidth-getWidth()?parentWidth-getWidth():x;
                y=getY()<0?0:getY()+getHeight()>parentHeight?parentHeight-getHeight():y;

                setX(x);
                setY(y);
                lastX=rawX;
                lastY=rawY;
               // mytoast(rawX+",  "+rawY);
               // Log.i("aa","isDrag="+isDrag+"getX="+getX()+";getY="+getY()+";parentWidth="+parentWidth);
                break;
            case MotionEvent.ACTION_UP:
                if(!isNotDrag()){
                    //恢复按压效果
                    setPressed(false);
                   // mytoast(lastX+","+lastY+"\n"+rawX+","+rawY+"\n"+dx+", "+dy);

                    if(rawX>=parentWidth/2){
                        //靠右吸附
                        /**/
                        animate().setInterpolator(new DecelerateInterpolator())
                                .setDuration(500)
                                .xBy(parentWidth-getWidth()-getX())//-getWidth()-getX()
                                .start();

                    }else {
                        //靠左吸附
                      /*
                        ObjectAnimator oa=ObjectAnimator.ofFloat(this,"y",getY()-10,300);
                        ObjectAnimator ob=ObjectAnimator.ofFloat(this,"x",getX()-10,110);

                        oa.setInterpolator(new DecelerateInterpolator());
                        oa.setDuration(500);
                        oa.start();


                        AnimatorSet as = new AnimatorSet();
                        as.playTogether(oa, ob);
                        as.start();
                        */
                        ObjectAnimator oa = ObjectAnimator.ofFloat(this, "x", getX(), 0);
                        oa.setInterpolator(new DecelerateInterpolator());
                        oa.setDuration(500);
                        oa.start();
                    }


                }
                break;
        }
        //如果是拖拽则消s耗事件，否则正常传递即可。
        return !isNotDrag() || super.onTouchEvent(event);
    }

    private boolean isNotDrag(){
      //  mytoast(Math.abs(getHeight()-getY())+"");
        return !isDrag&&(getX()==0
                ||(getX()==parentWidth-getWidth()) || (parentWidth-getWidth()<=getX()+5 &&parentWidth-getWidth()>=getX()-5)||
                (Math.abs(getHeight()-getY())<=5 ));
    }
    private void mytoast(String str)
    {
        Toast toast=Toast.makeText(getContext(), str, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}