package com.roman.movies.Utils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import android.app.Application;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

public class VolleySingleton extends Application {

	private final static String TAG = "GET";
	private RequestQueue mRequestQueue;
	private ImageLoader mImageLoader;
	private static VolleySingleton mInstance;

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		mImageLoader = new ImageLoader(this.mRequestQueue, new ImageLoader.ImageCache() {
			private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);
			@Override
			public void putBitmap(String url, Bitmap bitmap) {
				mCache.put(url, bitmap);
			}
			
			@Override
			public Bitmap getBitmap(String url) {
				return mCache.get(url);
			}
		});
	}

	public static synchronized VolleySingleton getInstance() {
		return mInstance;
	}

	public RequestQueue getRequestQueue() {
		return mRequestQueue;
	}

	public <T> void addToQueue(Request<T> request) {
		request.setTag(TAG);
		getRequestQueue().add(request);
	}

	public void cancelRequest() {
		mRequestQueue.cancelAll(TAG);
	}
	public ImageLoader getImageLoader(){
		return this.mImageLoader;
	}

}
