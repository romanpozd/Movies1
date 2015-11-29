package com.roman.movies;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.roman.movies.Model.Movie;
import com.roman.movies.Utils.VolleySingleton;

import android.graphics.Bitmap;

public class parseJSON extends Volley {

	// Movies list constants
	private static final String MOVIES_ARRAY = "movies";
	private static final String MOVIE_NAME = "name";
	private static final String MOVIE_YEAR = "year";
	private static final String MOVIE_ID = "id";
	private static final String MOVIE_CATEGORY = "category";
	
	// Ad constants
	private static final String AD_ARRAY = "banner";
	private static final String AD_IMAGE = "imageUrl";
	
	// Default constructor
	public parseJSON() {
	}

	// Callback that returns the full list of movies
	public interface VolleyCallBackMovies {
		void onSuccess(ArrayList<Movie> moviesList);
	}
	public interface VolleyCallBackAds {
		void onSuccess(String imgURL);
	}
	public interface VolleyImageRequest {
		void onSuccess(Bitmap bitmap);
	}

	protected void getImage(String url, final VolleyImageRequest callBackImageRequest){
		@SuppressWarnings("deprecation")
		ImageRequest request = new ImageRequest(url, 
				new Response.Listener<Bitmap>(){

					@Override
					public void onResponse(Bitmap image) {
						callBackImageRequest.onSuccess(image);
					}
			
		}, 0, 0, null, new Response.ErrorListener(){

			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		VolleySingleton.getInstance().addToQueue(request);
	}
	protected void ParseAdImage(String url, final VolleyCallBackAds callBackAds){

		JSONObject jsonObject = getDataFromCache(url);
		if (jsonObject != null)
			setAdData(jsonObject, callBackAds);
		else{
			JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
					new Response.Listener<JSONObject>(){

						@Override
						public void onResponse(JSONObject object) {
							setAdData(object, callBackAds);
						}
				
			}, new Response.ErrorListener(){
				@Override
				public void onErrorResponse(VolleyError arg0) {
					// TODO Auto-generated method stub
					
				}
				
			});
			VolleySingleton.getInstance().addToQueue(jsonObjectRequest);
		}
	}
	private void setAdData(JSONObject object, VolleyCallBackAds callBackAds){

		try {
			JSONArray jsonArray = object.getJSONArray(AD_ARRAY);
			String imageURL = jsonArray.getJSONObject(1).getString(AD_IMAGE);
			callBackAds.onSuccess(imageURL);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	protected void ParseMoviesList(String url, final VolleyCallBackMovies callbackMovies) {

		JSONObject jsonObject = getDataFromCache(url);
		if (jsonObject != null)
			setMoviesData(jsonObject, callbackMovies);
		else {
			JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
					new Response.Listener<JSONObject>() {

						@Override
						public void onResponse(JSONObject object) {
							setMoviesData(object, callbackMovies);
						}

					}, new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError arg0) {
							// TODO Auto-generated method stub
						}
					});
			VolleySingleton.getInstance().addToQueue(jsonObjectRequest);
		}
	}

	private void setMoviesData(JSONObject object, VolleyCallBackMovies callBackMovies) {
		
		ArrayList<Movie> moviesList = new ArrayList<Movie>();
		try {
			JSONArray jsonArray = object.getJSONArray(MOVIES_ARRAY);
			JSONObject tempObject;
			for (int i = 0; i < jsonArray.length(); ++i){
				tempObject = jsonArray.getJSONObject(i);
				Movie movie = new Movie();
				movie.setName(tempObject.getString(MOVIE_NAME));
				movie.setYear(tempObject.getInt(MOVIE_YEAR));
				movie.setId(tempObject.getString(MOVIE_ID));
				movie.setCategory(tempObject.getString(MOVIE_CATEGORY));
				moviesList.add(movie);
			}
			callBackMovies.onSuccess(moviesList);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private JSONObject getDataFromCache(String url){
		/*********************************************/
		/* Check if there any data saved in cache */
		/* if there is no data, parse JSONObject */
		/*********************************************/
		
		JSONObject jsonObject = null;
		Cache cache = VolleySingleton.getInstance().getRequestQueue().getCache();
		Cache.Entry entry = cache.get(url);
		if (entry != null) {
			try {
				String jsonString = new String(entry.data, "UTF-8");
				jsonObject = new JSONObject(jsonString);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return jsonObject;
	}
}
