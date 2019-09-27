package com.miracle.library;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.miracle.library.listener.RequestListener;
import com.miracle.library.manager.RequestManager;
import com.miracle.library.utils.Md5FileNameGenerator;

import java.lang.ref.SoftReference;

/**
 * @author miracle
 * @date 2019-09-25
 * @email ruanyandongai@gmail.com
 * @blog https://ruanyandong.github.io
 */
public class RequestBuilder {

    private final Context context;
    /**
     * 网络图片地址
     */
    private String url;
    /**
     * 图片文件名重新改名
     */
    private String md5FileName;

    /**
     * 占位符的资源id
     */
    private int placeholder;

    /**
     *  软引用
     */
    private SoftReference<ImageView> sf;

    private RequestListener listener;

    public RequestBuilder(Context context) {
        this.context = context;

    }

    public RequestBuilder load(String url) {
        this.url = url;
        // 改文件名，方便存储本地文件名，避免乱码问题
        this.md5FileName = Md5FileNameGenerator.generate(url);
        return this;
    }

    public RequestBuilder placeholder(int placeholder){
        this.placeholder = placeholder;
        return this;
    }

    public RequestBuilder listener(RequestListener listener){
        this.listener = listener;
        return this;
    }
    /**
     * 参数的校验和初始化工作
     */
    public void into(ImageView imageView){
        // mark
        imageView.setTag(md5FileName);
        this.sf = new SoftReference<>(imageView);

        if (TextUtils.isEmpty(url)){
            // 抛出异常
        }
        if (placeholder <= 0){
            // 自由拓展
        }
        // 将包装好的请求对象体，添加到请求管理类的队列中
        RequestManager.getInstance().addRequestQueue(this).dispatcher();

    }

    public Context getContext() {
        return context;
    }

    public String getUrl() {
        return url;
    }

    public String getMd5FileName() {
        return md5FileName;
    }

    public int getPlaceholder() {
        return placeholder;
    }

    public ImageView getImageView() {
        return sf.get();
    }

    public RequestListener getListener() {
        return listener;
    }
}
