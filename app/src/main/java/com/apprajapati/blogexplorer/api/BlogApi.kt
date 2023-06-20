package com.apprajapati.blogexplorer.api

import com.apprajapati.blogexplorer.models.Post
import com.apprajapati.blogexplorer.models.User
import retrofit2.Call
import retrofit2.http.*

interface BlogApi {
    @GET("posts")
    suspend fun getPosts(@Query("_page") page: Int = 1, @Query("_limit") limit: Int = 10): List<Post>

    @GET("posts/{id}")
    suspend fun getPost(@Path("id") postId: Int): Post

    @GET("users/{id}")
    suspend fun getUser(@Path("id") userId: Int): User

    @GET("posts/{id}")
    fun getPostViaCallback(@Path("id") postId: Int): Call<Post>

    @GET("users/{id}")
    fun getUserViaCallback(@Path("id") userId: Int): Call<User>

    // whole object update
    @PUT("posts/{id}")
    suspend fun updatePost(@Path("id") userId: Int, @Body post: Post) : Post

    //specific update
    @PATCH("posts/{id}")
    suspend fun patchPost(@Path("id") userId: Int, @Body params: Map<String, String>) : Post

    @DELETE("posts/{id}")
    suspend fun deletePost(@Header("Auth-TOken") auth: String ,@Path("id") postId : Int)

    @Headers("Platform: Android") // Second approach to adding headers other than headerinterceptor
    @POST("posts/")
    suspend fun createPost(@Body body: Post) : Post
}