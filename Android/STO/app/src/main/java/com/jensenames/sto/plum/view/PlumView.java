package com.jensenames.sto.plum.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceView;

import com.jensenames.sto.R;
import com.jensenames.sto.windows.MainActivity;

import java.util.ArrayList;

public class PlumView extends SurfaceView {

    private Paint backgroundPaint;
    private Paint path1Paint;
    private Paint path2Paint;

    private float[][][] points;

    private ArrayList<Integer> path;
    private boolean isFirst;

    public PlumView(Context context) {
        super(context);
        initView();
    }

    public PlumView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        this.setBackgroundColor(Color.WHITE);
        backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.parseColor("#FF00FF"));
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setAntiAlias(true);
        backgroundPaint.setDither(true);

        path1Paint = new Paint();
        path1Paint.setColor(Color.parseColor("#D3FF00"));
        path1Paint.setStyle(Paint.Style.STROKE);
        path1Paint.setAntiAlias(true);
        path1Paint.setDither(true);

        path2Paint = new Paint();
        path2Paint.setColor(Color.parseColor("#74FF00"));
        path2Paint.setStyle(Paint.Style.STROKE);
        path2Paint.setAntiAlias(true);
        path2Paint.setDither(true);
    }

    public void setPath(ArrayList<Integer> path, boolean isFirst) {
        this.path = path;
        this.isFirst = isFirst;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawMap(canvas);
        if(MainActivity.isProcess() && path != null)
            drawPath(canvas);
    }

    private void drawMap(Canvas canvas) {
        int radius = Math.min(this.getWidth() / 10, this.getHeight() / 15);
        int length = Math.min((this.getWidth() - 2 * radius) / 2, (this.getHeight() - 2 * radius) / 3);
        backgroundPaint.setStrokeWidth(Math.min(getResources().getDimension(R.dimen.max_circle_stroke_width), radius / 15));
        path1Paint.setStrokeWidth(Math.min(getResources().getDimension(R.dimen.max_stroke_width), radius / 10));
        path2Paint.setStrokeWidth(Math.min(getResources().getDimension(R.dimen.max_stroke_width), radius / 10));

        if(points == null) {
            points = new float[8][8][];
            points[0][1] = new float[] {this.getWidth() / 2, this.getHeight() / 2 + length / 2 * 3 - radius / 2, this.getWidth() / 2, this.getHeight() / 2 + length / 2 + radius / 2};
            points[1][4] = new float[] {this.getWidth() / 2, this.getHeight() / 2 + length / 2 - radius / 2, this.getWidth() / 2, this.getHeight() / 2 - length / 2 + radius / 2};
            points[4][7] = new float[] {this.getWidth() / 2, this.getHeight() / 2 - length / 2 - radius / 2, this.getWidth() / 2, this.getHeight() / 2 - length / 2 * 3 + radius / 2};
            points[2][5] = new float[] {this.getWidth() / 2 - (float)(length * Math.sqrt(3) / 2), this.getHeight() / 2 - radius / 2, this.getWidth() / 2 - (float)(length * Math.sqrt(3) / 2), this.getHeight() / 2 - length + radius / 2};
            points[3][6] = new float[] {this.getWidth() / 2 + (float)(length * Math.sqrt(3) / 2), this.getHeight() / 2 - radius / 2, this.getWidth() / 2 + (float)(length * Math.sqrt(3) / 2), this.getHeight() / 2 - length + radius / 2};
            points[1][2] = new float[] {this.getWidth() / 2 - (float)(radius * Math.sqrt(3) / 4), this.getHeight() / 2 + length / 2 - radius / 4, this.getWidth() / 2 - (float)(length * Math.sqrt(3) / 2) + (float)(radius * Math.sqrt(3) / 4), this.getHeight() / 2 + radius / 4};
            points[1][3] = new float[] {this.getWidth() / 2  + (float)(radius * Math.sqrt(3) / 4), this.getHeight() / 2 + length / 2 - radius / 4, this.getWidth() / 2 + (float)(length * Math.sqrt(3) / 2) - (float)(radius * Math.sqrt(3) / 4), this.getHeight() / 2 + radius / 4};
            points[2][4] = new float[] {this.getWidth() / 2 - (float)(length * Math.sqrt(3) / 2) + (float)(radius * Math.sqrt(3) / 4), this.getHeight() / 2 - radius / 4, this.getWidth() / 2 - (float)(radius * Math.sqrt(3) / 4), this.getHeight() / 2 - length / 2 + radius / 4};
            points[3][4] = new float[] {this.getWidth() / 2 + (float)(length * Math.sqrt(3) / 2) - (float)(radius * Math.sqrt(3) / 4), this.getHeight() / 2 - radius / 4, this.getWidth() / 2 + (float)(radius * Math.sqrt(3) / 4), this.getHeight() / 2 - length / 2 + radius / 4};
            points[4][5] = new float[] {this.getWidth() / 2 - (float)(radius * Math.sqrt(3) / 4), this.getHeight() / 2 - length / 2 - radius / 4, this.getWidth() / 2 - (float)(length * Math.sqrt(3) / 2) + (float)(radius * Math.sqrt(3) / 4), this.getHeight() / 2 - length + radius / 4};
            points[4][6] = new float[] {this.getWidth() / 2 + (float)(radius * Math.sqrt(3) / 4), this.getHeight() / 2 - length / 2 - radius / 4, this.getWidth() / 2 + (float)(length * Math.sqrt(3) / 2) - (float)(radius * Math.sqrt(3) / 4), this.getHeight() / 2 - length + radius / 4};
            points[5][7] = new float[] {this.getWidth() / 2 - (float)(length * Math.sqrt(3) / 2) + (float)(radius * Math.sqrt(3) / 4), this.getHeight() / 2 - length - radius / 4, this.getWidth() / 2 - (float)(radius * Math.sqrt(3) / 4), this.getHeight() / 2 - length / 2 * 3 + radius / 4};
            points[6][7] = new float[] {this.getWidth() / 2 + (float)(length * Math.sqrt(3) / 2) - (float)(radius * Math.sqrt(3) / 4), this.getHeight() / 2 - length - radius / 4, this.getWidth() / 2 + (float)(radius * Math.sqrt(3) / 4), this.getHeight() / 2 - length / 2 * 3 + radius / 4};
        }

        canvas.drawCircle(this.getWidth() / 2, this.getHeight() / 2 + length / 2 * 3, radius / 2, backgroundPaint);//0
        canvas.drawCircle(this.getWidth() / 2, this.getHeight() / 2 + length / 2, radius / 2, backgroundPaint);//1
        canvas.drawCircle(this.getWidth() / 2, this.getHeight() / 2 - length / 2, radius / 2, backgroundPaint);//4
        canvas.drawCircle(this.getWidth() / 2, this.getHeight() / 2 - length / 2 * 3, radius / 2, backgroundPaint);//7
        canvas.drawCircle(this.getWidth() / 2 - (float)(length * Math.sqrt(3) / 2), this.getHeight() / 2, radius / 2, backgroundPaint);//2
        canvas.drawCircle(this.getWidth() / 2 + (float)(length * Math.sqrt(3) / 2), this.getHeight() / 2, radius / 2, backgroundPaint);//3
        canvas.drawCircle(this.getWidth() / 2 - (float)(length * Math.sqrt(3) / 2), this.getHeight() / 2 - length, radius / 2, backgroundPaint);//5
        canvas.drawCircle(this.getWidth() / 2 + (float)(length * Math.sqrt(3) / 2), this.getHeight() / 2 - length, radius / 2, backgroundPaint);//6

        canvas.drawLine(points[0][1][0], points[0][1][1],points[0][1][2], points[0][1][3], backgroundPaint);//0-1
        canvas.drawLine(points[1][4][0], points[1][4][1],points[1][4][2], points[1][4][3], backgroundPaint);//1-4
        canvas.drawLine(points[4][7][0], points[4][7][1],points[4][7][2], points[4][7][3], backgroundPaint);//4-7
        canvas.drawLine(points[2][5][0], points[2][5][1],points[2][5][2], points[2][5][3], backgroundPaint);//2-5
        canvas.drawLine(points[3][6][0], points[3][6][1],points[3][6][2], points[3][6][3], backgroundPaint);//3-6
        canvas.drawLine(points[1][2][0], points[1][2][1],points[1][2][2], points[1][2][3], backgroundPaint);//1-2
        canvas.drawLine(points[1][3][0], points[1][3][1],points[1][3][2], points[1][3][3], backgroundPaint);//1-3
        canvas.drawLine(points[2][4][0], points[2][4][1],points[2][4][2], points[2][4][3], backgroundPaint);//2-4
        canvas.drawLine(points[3][4][0], points[3][4][1],points[3][4][2], points[3][4][3], backgroundPaint);//3-4
        canvas.drawLine(points[4][5][0], points[4][5][1],points[4][5][2], points[4][5][3], backgroundPaint);//4-5
        canvas.drawLine(points[4][6][0], points[4][6][1],points[4][6][2], points[4][6][3], backgroundPaint);//4-5
        canvas.drawLine(points[5][7][0], points[5][7][1],points[5][7][2], points[5][7][3], backgroundPaint);//5-7
        canvas.drawLine(points[6][7][0], points[6][7][1],points[6][7][2], points[6][7][3], backgroundPaint);//6-7
    }

    private void drawPath(Canvas canvas) {
        int min1 = Math.min(path.get(0), path.get(1));
        int max1 = Math.max(path.get(0), path.get(1));

        if(path.size() == 3) {
            int min2 = Math.min(path.get(1), path.get(2));
            int max2 = Math.max(path.get(1), path.get(2));

            if(isFirst)
                canvas.drawLine(points[min1][max1][0], points[min1][max1][1], points[min1][max1][2], points[min1][max1][3], path1Paint);

            canvas.drawLine(points[min2][max2][0], points[min2][max2][1], points[min2][max2][2], points[min2][max2][3], path2Paint);
        }
        path = null;
    }
}
