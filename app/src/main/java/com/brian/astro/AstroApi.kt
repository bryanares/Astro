package com.brian.astro

import retrofit2.Response
import retrofit2.http.GET

interface AstroApi {

    @GET("/planetary/apod?api_key=DEMO_KEY&count=10")
    suspend fun getAstros(): Response<List<Astro>>
}