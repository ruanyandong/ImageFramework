package com.miracle.library.listener;

import android.graphics.Bitmap;

import com.miracle.library.exception.GlideException;

/**
 * @author miracle
 * @date 2019-09-25
 * @email ruanyandongai@gmail.com
 * @blog https://ruanyandong.github.io
 */
public interface RequestListener {
    /**
     * 资源已经准备好了
     */
    void onResourceReady(Bitmap bitmap);

    /**
     *  加载异常
     * @param e
     */
    void onLoadFailed(GlideException e);

}
