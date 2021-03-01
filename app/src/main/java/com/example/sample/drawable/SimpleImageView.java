package com.example.sample.drawable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;

// public class SimpleImageView extends View
// {
//     // 定义两个常量，这两个常量指定该图片横向、纵向上都被划分为20格
//     private final static int WIDTH = 20;
//     private final static int HEIGHT = 20;
//     // 记录该图片上包含441个顶点
//     private final static int COUNT = (WIDTH + 1) * (HEIGHT + 1);
//     // 定义一个数组，保存Bitmap上的21 * 21个点的坐标
//     private float[] verts = new float[COUNT * 2];
//     // 定义一个数组，记录Bitmap上的21 * 21个点经过扭曲后的坐标
//     // 对图片进行扭曲的关键就是修改该数组里元素的值
//     private float[] orig = new float[COUNT * 2];
//     private Bitmap bitmap;
//
//     SimpleImageView(Context context, int drawableId)
//     {
//         super(context);
//         setFocusable(true);
//         // 根据指定资源加载图片
//         bitmap = BitmapFactory.decodeResource(getResources(), drawableId);
//         // 获取图片宽度、高度
//         float bitmapWidth = bitmap.getWidth();
//         float bitmapHeight = bitmap.getHeight();
//         int index = 0;
//         for (int y = 0; y <= HEIGHT; y++) {
//             float fy = bitmapHeight * y / HEIGHT;
//             for (int x = 0; x <= WIDTH; x++) {
//                 float fx = bitmapWidth * x / WIDTH;
//                 // 初始化orig、verts数组。初始化后，orig、verts
//                 // 两个数组均匀地保存了21 * 21个点的x,y坐标
//                 verts[index * 2] = fx;
//                 orig[index * 2] = verts[index * 2];
//                 verts[index * 2 + 1] = fy;
//                 orig[index * 2 + 1] = verts[index * 2 + 1];
//                 index += 1;
//             }
//         }
//         // 设置背景色
//         setBackgroundColor(Color.WHITE);
//     }
//
//     @Override
//     public void onDraw(Canvas canvas)
//     {
//         // 对bitmap按verts数组进行扭曲
//         // 从第一个点（由第5个参数0控制）开始扭曲
//         canvas.drawBitmapMesh(bitmap, WIDTH, HEIGHT, verts, 0, null, 0, null);
//     }
//
//     // 工具方法，用于根据触摸事件的位置计算verts数组里各元素的值
//     private void warp(float cx, float cy)
//     {
//         for (int i = 0; i < COUNT * 2; i += 2) {
//             float dx = cx - orig[i];
//             float dy = cy - orig[i + 1];
//             float dd = dx * dx + dy * dy;
//             // 计算每个坐标点与当前点（cx, cy）之间的距离
//             float d = (float) Math.sqrt(dd);
//             // 计算扭曲度，距离当前点（cx, cy）越远，扭曲度越小
//             float pull = 200000 / (dd * d);
//             // 对verts数组（保存bitmap上21 * 21个点经过扭曲后的坐标）重新赋值
//             if (pull >= 1) {
//                 verts[i] = cx;
//                 verts[i + 1] = cy;
//             } else {
//                 // 控制各顶点向触摸事件发生点偏移
//                 verts[i] = orig[i] + dx * pull;
//                 verts[i + 1] = orig[i + 1] + dy * pull;
//             }
//         }
//         // 通知View组件重绘
//         invalidate();
//     }
//
//     @Override
//     public boolean onTouchEvent(MotionEvent event)
//     {
//         // 调用warp方法根据触摸屏事件的坐标点来扭曲verts数组
//         warp(event.getX(), event.getY());
//         return true;
//     }
// }

public class SimpleImageView extends View {

    private Bitmap mBitmap;
    private int meshWidth = 20;
    private int meshHeight = 20;
    private int count = (meshWidth + 1) * (meshHeight + 1);
    private float[] origin = new float[count * 2];
    private float[] verts = new float[count * 2];

    SimpleImageView(Context context, int resId) {
        super(context);
        setFocusable(true);
        mBitmap = BitmapFactory.decodeResource(getResources(), resId);
        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();
        int index = 0;
        for (int i = 0; i <= meshHeight; ++i) {
            int y = i * height / meshHeight;
            for (int j = 0; j <= meshWidth; ++j) {
                int x = j * width / meshWidth;
                origin[index * 2] = x;
                verts[index * 2] = x;
                origin[index * 2 + 1] = y;
                verts[index * 2 + 1] = y;
                ++index;
            }
        }
        setBackgroundColor(Color.WHITE);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        for (int i = 0; i < count *2; i+=2) {
            float dx = x - origin[i ];
            float dy = y - origin[i + 1];
            float dd = dx * dx + dy * dy;
            float d = (float) Math.sqrt(dd);
            float pull = 200000 / (dd * d);
            if (pull >= 1) {
                verts[i] = x;
                verts[i +1] = y;
            } else {
                verts[i] = origin[i] + dx*pull;
                verts[i+1] = origin[i+1] + dy*pull;
            }
        }
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // super.onDraw(canvas);
        canvas.drawBitmapMesh(mBitmap, meshWidth, meshHeight, verts, 0, null, 0, null);
    }
}
