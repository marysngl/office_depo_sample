package officedepo.mediapark.com.officedepo.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Mary Songal on 15.02.2017.
 */

public class DealView extends View {

    private static final int BACKGROUND_MARGIN = 20;

    private Paint trianglePaint;
    private Paint backgroundPaint;
    private Paint shadowPaint;

    public DealView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        trianglePaint = new Paint();
        backgroundPaint = new Paint();
        shadowPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int viewWidth = this.getMeasuredWidth();
        int viewHeight = this.getMeasuredHeight();

        backgroundPaint.setColor(getResources().getColor(android.R.color.black));
        backgroundPaint.setStyle(Paint.Style.FILL);
        backgroundPaint.setShader(new LinearGradient(0, 0, viewWidth, viewHeight, new int[] {Color.WHITE, Color.GRAY, Color.WHITE}, new float[] {0f, 0.5f, 1.0f}, Shader.TileMode.CLAMP));
        @SuppressLint("DrawAllocation") Path backgroundPath = new Path();
        backgroundPath.reset();
        backgroundPath.moveTo(0, 0);
        backgroundPath.lineTo(viewWidth - viewHeight / 1.1f, 0);
        backgroundPath.lineTo(viewWidth, viewHeight / 3.7f);
        backgroundPath.lineTo(viewWidth, viewHeight);
        backgroundPath.lineTo(0, viewHeight);
        backgroundPath.lineTo(0, 0);
        canvas.drawPath(backgroundPath, backgroundPaint);

        shadowPaint.setColor(getResources().getColor(android.R.color.white));
        shadowPaint.setStyle(Paint.Style.FILL);
        @SuppressLint("DrawAllocation") Path shadowPath = new Path();
        shadowPath.reset();
        shadowPath.moveTo(2, 2);
        shadowPath.lineTo(viewWidth - viewHeight / 1.1f - 2, 2);
        shadowPath.lineTo(viewWidth - 2, viewHeight / 3.7f + 2);
        shadowPath.lineTo(viewWidth - 2, viewHeight - 2);
        shadowPath.lineTo(2, viewHeight - 2);
        shadowPath.lineTo(2, 2);
        canvas.drawPath(shadowPath, shadowPaint);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        ViewGroup.MarginLayoutParams margins = ViewGroup.MarginLayoutParams.class.cast(getLayoutParams());
        margins.topMargin = BACKGROUND_MARGIN;
        margins.bottomMargin = BACKGROUND_MARGIN;
        margins.leftMargin = BACKGROUND_MARGIN;
        margins.rightMargin = BACKGROUND_MARGIN;
        setLayoutParams(margins);
    }
}
