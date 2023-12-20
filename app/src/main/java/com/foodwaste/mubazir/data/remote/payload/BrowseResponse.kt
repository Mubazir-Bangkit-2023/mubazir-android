package com.foodwaste.mubazir.data.remote.payload

import com.google.gson.annotations.SerializedName


data class BrowseResponse(

	@field:SerializedName("posts")
	val posts: List<FoodPostResponse>
)


data class FoodPostResponse(

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("price")
	val price: Int,

	@field:SerializedName("imgUrl")
	val imgUrl: String,

	@field:SerializedName("distance")
	val distance: Int,

	@field:SerializedName("lon")
	val lon: Double,

	@field:SerializedName("lat")
	val lat: Double,

	@field:SerializedName("categoryId")
	val categoryId: Int,

)
