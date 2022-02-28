package com.coderbychance.congestedtextview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class CongestedTextView extends AppCompatTextView {

    Rect bounds;
    public CongestedTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    private void init(Context context) {
        this.measure(0, 0);

        String s = (String)this.getText();
        bounds = new Rect();
        Paint textPaint = this.getPaint();

        textPaint.getTextBounds(s,0,s.length(),bounds);
        int baseLine = this.getBaseline();

        bounds.top = baseLine + bounds.top;
        bounds.bottom = baseLine+bounds.bottom;

        int startPadding = this.getPaddingStart();
        bounds.left += startPadding;

        bounds.right = (int) textPaint.measureText(s, 0, s.length()) + startPadding;
        setCongested(true);

        // At this point, (x, y) of the text within the TextView is (bounds.left, bounds.top)
        // Draw the bounding rectangle.

    }

    public void setCongested(Boolean isCongested){
        if (!isCongested){
            return;
        }

        Bitmap bitmap = Bitmap.createBitmap(this.getMeasuredWidth(),
                this.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        Paint rectPaint = new Paint();
        rectPaint.setColor(Color.RED);
        rectPaint.setStyle(Paint.Style.STROKE);
        rectPaint.setStrokeWidth(1);
//        canvas.drawRect(bounds, rectPaint);




        LinearLayout.LayoutParams layoutParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParam.height = bounds.height();
        layoutParam.width = bounds.width();
        this.setLayoutParams(layoutParam);

        this.setPadding(-bounds.left,-bounds.top,0,0);
    }

}
