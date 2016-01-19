package ufms.br.com.ufmsapp.network;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import ufms.br.com.ufmsapp.MyApplication;

public class VolleySingleton {

    private static VolleySingleton mInstance = null;
    RequestQueue mRequest;
    ImageLoader imageLoader;

    private VolleySingleton() {
        mRequest = Volley.newRequestQueue(MyApplication.getAppContext());
        imageLoader = new ImageLoader(mRequest, new ImageLoader.ImageCache() {

            private LruCache<String, Bitmap> cache = new LruCache<>(getCacheSize());

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
    }

    public static VolleySingleton getInstance() {
        if (mInstance == null) {
            mInstance = new VolleySingleton();
        }

        return mInstance;
    }

    private int getCacheSize() {
        return (int) Runtime.getRuntime().maxMemory() / 1024 / 8;
    }

    public RequestQueue getRequestQueue() {
        return mRequest;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }
}
