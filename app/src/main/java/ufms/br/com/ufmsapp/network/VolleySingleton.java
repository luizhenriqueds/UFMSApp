/*
 * Copyright [2016] [UFMS]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
