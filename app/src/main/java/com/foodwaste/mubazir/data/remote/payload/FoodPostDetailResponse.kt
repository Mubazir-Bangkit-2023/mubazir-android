package com.foodwaste.mubazir.data.remote.payload

import com.google.gson.annotations.SerializedName

data class FoodPostDetailResponse(

	@field:SerializedName("isAvailable")
	val isAvailable: Boolean,

	@field:SerializedName("User")
	val user: User,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("lon")
	val lon: Double,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("freshness")
	val freshness: String,

	@field:SerializedName("imgUrl")
	val imgUrl: String,

	@field:SerializedName("createdAt")
	val createdAt: Long,

	@field:SerializedName("pickupTime")
	val pickupTime: Long,

	@field:SerializedName("price")
	val price: Int,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("lat")
	val lat: Double,

	@field:SerializedName("categoryId")
	val categoryId: Int
)

data class User(

	@field:SerializedName("no_hp")
	val noHp: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("fullname")
	val fullname: String,

	@field:SerializedName("photo_url")
	val photoUrl: String?,

	@field:SerializedName("email")
	val email: String
)
