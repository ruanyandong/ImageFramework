package com.miracle.library.task;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.miracle.library.RequestBuilder;
import com.miracle.library.exception.GlideException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author miracle
 * @date 2019-09-26
 * @email ruanyandongai@gmail.com
 * @blog https://ruanyandong.github.io
 */
public class DispatcherTask extends Thread{

    private Handler handler;
    private LinkedBlockingQueue<RequestBuilder> requestQueue;

    public DispatcherTask(LinkedBlockingQueue<RequestBuilder> requestQueue) {
        this.requestQueue = requestQueue;
        // 获取主线程的Looper，保证UI刷新
        handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void run() {
        super.run();
        // 只要线程没中断，一直轮询
        while (!isInterrupted()){
            try {
                // 从队列中请求构建对象
                RequestBuilder requestBuilder = requestQueue.take();
                // 设置占位图
                placeholder(requestBuilder);
                // 从网络加载图片
                Bitmap bitmap = loadFromNet(requestBuilder);
                // 最终显示图片到控件
                finalShow(requestBuilder,bitmap);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void finalShow(final RequestBuilder requestBuilder, final Bitmap bitmap) {
        if (requestBuilder.getImageView() != null && bitmap != null
        && requestBuilder.getImageView().getTag().equals(requestBuilder.getMd5FileName())){
            handler.post(new Runnable() {
                @Override
                public void run() {
                    requestBuilder.getImageView().setImageBitmap(bitmap);
                    if (requestBuilder.getListener() != null){
                        requestBuilder.getListener().onResourceReady(bitmap);
                    }
                }
            });
        }
    }

    private Bitmap loadFromNet(RequestBuilder requestBuilder) {
        InputStream is = null;
        Bitmap bitmap = null;
        try {
            URL url = new URL(requestBuilder.getUrl());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
        } catch (Exception e) {
            e.printStackTrace();
            if (requestBuilder.getListener() != null){
                requestBuilder.getListener().onLoadFailed(new GlideException(e.getMessage()));
            }
        }finally {
            try {
                if (is != null){
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    private void placeholder(final RequestBuilder requestBuilder) {
        if (requestBuilder.getPlaceholder() > 0 && requestBuilder.getImageView() != null){
            // 在主线程加载
            handler.post(new Runnable() {
                @Override
                public void run() {
                    requestBuilder.getImageView().setImageResource(requestBuilder.getPlaceholder());
                }
            });
        }
    }
}
