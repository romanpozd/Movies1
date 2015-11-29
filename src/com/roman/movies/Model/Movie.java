package com.roman.movies.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable{
	private String id,name,category;
	private int year;
	
	public Movie(){}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}


	protected Movie(Parcel in){
		id = in.readString();
		name = in.readString();
		category = in.readString();
		year = in.readInt();
	}
	@Override
	public int describeContents() {
		return 0;
	}


	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeString(name);
		dest.writeString(category);
		dest.writeInt(year);
	}
	
	public static final Parcelable.Creator<Movie> CREATOR = new Creator<Movie>() {
		
		@Override
		public Movie[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Movie[size];
		}
		
		@Override
		public Movie createFromParcel(Parcel source) {
			return new Movie(source);
		}
	}; 
	
	

}
