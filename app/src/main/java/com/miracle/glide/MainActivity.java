package com.miracle.glide;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.miracle.library.Glide;
import com.miracle.library.exception.GlideException;
import com.miracle.library.listener.RequestListener;

/**
 * @author ruanyandong
 */
public class MainActivity extends AppCompatActivity {

    String url = "http://g.hiphotos.baidu.com/image/pic/item/0823dd54564e92589f2fe1019882d158cdbf4ec1.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testGlide();
    }

    private void testGlide() {
        ImageView img = findViewById(R.id.img);
        Glide
                .with(this)
                .load(url)
                .placeholder(R.mipmap.ic_launcher)
                .listener(new RequestListener() {
                    @Override
                    public void onResourceReady(Bitmap bitmap) {

                    }

                    @Override
                    public void onLoadFailed(GlideException e) {

                    }
                })
                .into(img);
    }

    @Override
    protected void onDestroy() {
        Glide.destroy();
        super.onDestroy();

    }
}
