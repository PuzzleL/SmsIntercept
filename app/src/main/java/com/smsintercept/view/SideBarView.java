package com.smsintercept.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;


/**
 * Created by user on 2016/1/26.
 */
public class SideBarView extends View{
    private Context mContext;
    private TextView mTextDialog;
    private int choose = -1;
    private Paint paint = new Paint();
    private String[] letters = {"#", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};

    private float velocityY=0;

    private OnTouchLetterChangeListener listener;

    public SideBarView(Context context) {
        super(context);
        this.mContext = context;
    }

    public SideBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    public interface OnTouchLetterChangeListener {
        public void onTounchLetterChange(String letter);
    }

    public void setOnTouchLetterChangeListener(OnTouchLetterChangeListener listener){
        this.listener = listener;
    }

    public TextView getTextDialog() {
        return mTextDialog;
    }

    public void setTextDialog(TextView dialog){
        this.mTextDialog = dialog;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight();
        int width = getWidth();
        int singleHeight = height / letters.length;
        for(int i = 0; i < letters.length; i++){
            paint.setColor(Color.parseColor("#cc000000"));
            paint.setTextSize(24);
            paint.setAntiAlias(true);
            if(i == choose){
                paint.setColor(Color.parseColor("#000000"));
            }
            float x = width / 2 - paint.measureText(letters[i]) / 2;
            float y = singleHeight * i + singleHeight;
            canvas.drawText(letters[i], x, y, paint);
            paint.reset();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        int action = event.getAction();
        float minVelocity = 0;      //最小滑动速度
        velocityY = velocityY==0?event.getY():velocityY;
        float Y = event.getY();
        int selectedIndex = (int) (Y / getHeight() * letters.length);
        switch (action){
            case MotionEvent.ACTION_DOWN:
                choose = selectedIndex;
                if(selectedIndex < 0
                        || selectedIndex > letters.length){
                    return false;
                }
                if(null != listener){
                    listener.onTounchLetterChange(letters[selectedIndex]);
                }
                if(null != mTextDialog){
                    mTextDialog.setText(letters[selectedIndex]);
                    mTextDialog.setVisibility(View.VISIBLE);
                }
                invalidate();
            case MotionEvent.ACTION_MOVE:
                choose = selectedIndex;
                if(selectedIndex < 0
                    || selectedIndex > letters.length){
                    return false;
                }
                if(null != listener){
                    listener.onTounchLetterChange(letters[selectedIndex]);
                }
                if(null != mTextDialog){
                    mTextDialog.setText(letters[selectedIndex]);
                    mTextDialog.setVisibility(View.VISIBLE);
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                choose = -1;
                velocityY = 0;
                if(null != mTextDialog){
                    mTextDialog.setVisibility(View.GONE);
                }
                break;
        }
        return true;
    }
}
