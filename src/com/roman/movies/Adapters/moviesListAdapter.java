package com.roman.movies.Adapters;

import java.util.List;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.roman.movies.R;
import com.roman.movies.Model.Movie;
import com.roman.movies.Utils.VolleySingleton;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class moviesListAdapter extends RecyclerView.Adapter<moviesListAdapter.CustomViewHolder>{
	
	// Volley image loader
	private ImageLoader mImageLoader = VolleySingleton.getInstance().getImageLoader();
	
	private List<Movie> moviesList; 
	
	OnItemClickListener mOnItemClickListener;
	
	public moviesListAdapter(List<Movie> moviesList){
		this.moviesList = moviesList;
	}
	
	@Override
	public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_singlerow, viewGroup, false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
	}
	
	@Override
	public void onBindViewHolder(CustomViewHolder customViewHolder, int position){
		Movie movie = moviesList.get(position);
		customViewHolder.movieTitle.setText("Title: " + movie.getName());
		customViewHolder.movieCategory.setText("Category: " + movie.getCategory());
		customViewHolder.movieYear.setText("Release: " + String.valueOf(movie.getYear()));
		
	}
	
	@Override
	public int getItemCount(){
		return moviesList.size();
	}
	
	public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		protected TextView movieTitle, movieYear, movieCategory;
		
		public CustomViewHolder(View itemView){
			super(itemView);
			this.movieTitle = (TextView)itemView.findViewById(R.id.tv_title);
			this.movieYear = (TextView)itemView.findViewById(R.id.tv_year);
			this.movieCategory = (TextView)itemView.findViewById(R.id.tv_category);
		}

		@SuppressWarnings("deprecation")
		@Override
		public void onClick(View v) {
			if (mOnItemClickListener != null)
				mOnItemClickListener.onItemClick(v, getPosition());
		}
		
	}
	
	public interface OnItemClickListener{
		public void onItemClick(View view, int position);
	}
	
	public void SetOnItemClickListener(final OnItemClickListener mItemClickListener){
		mOnItemClickListener = mItemClickListener;
	}
	
}
