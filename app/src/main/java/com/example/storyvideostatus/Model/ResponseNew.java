package com.example.storyvideostatus.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class  ResponseNew implements Serializable {

	@SerializedName("love")
	public int love;

	@SerializedName("image")
	public String image;

	@SerializedName("extension")
	public String extension;

	@SerializedName("thumbnail")
	public String thumbnail;

	@SerializedName("haha")
	public int haha;

	@SerializedName("comments")
	public int comments;

	@SerializedName("like")
	public int like;

	@SerializedName("created")
	public String created;

	@SerializedName("video")
	public String video;

	@SerializedName("angry")
	public int angry;

	@SerializedName("title")
	public String title;

	@SerializedName("type")
	public String type;

	@SerializedName("userid")
	public int userid;

	@SerializedName("tags")
	public Object tags;

	@SerializedName("downloads")
	public int downloads;

	@SerializedName("review")
	public boolean review;

	@SerializedName("userimage")
	public String userimage;

	@SerializedName("woow")
	public int woow;

	@SerializedName("sad")
	public int sad;

	@SerializedName("comment")
	public boolean comment;

	@SerializedName("id")
	public int id;

	@SerializedName("user")
	public String user;

	@Override
	public String toString() {
		return "ResponseNew{" +
				"love=" + love +
				", image='" + image + '\'' +
				", extension='" + extension + '\'' +
				", thumbnail='" + thumbnail + '\'' +
				", haha=" + haha +
				", comments=" + comments +
				", like=" + like +
				", created='" + created + '\'' +
				", video='" + video + '\'' +
				", angry=" + angry +
				", title='" + title + '\'' +
				", type='" + type + '\'' +
				", userid=" + userid +
				", tags=" + tags +
				", downloads=" + downloads +
				", review=" + review +
				", userimage='" + userimage + '\'' +
				", woow=" + woow +
				", sad=" + sad +
				", comment=" + comment +
				", id=" + id +
				", user='" + user + '\'' +
				'}';
	}
}