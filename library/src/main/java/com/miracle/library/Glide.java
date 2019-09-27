package com.miracle.library;

import android.content.Context;
import com.miracle.library.manager.RequestManager;

/**
 * @author miracle
 * @date 2019-09-25
 * @email ruanyandongai@gmail.com
 * @blog https://ruanyandong.github.io
 */
public class Glide {

    public static RequestManager with(Context context){
        return RequestManager.getInstance().get(context);
    }

    public static void destroy(){
        RequestManager.getInstance().stop();
    }

}
