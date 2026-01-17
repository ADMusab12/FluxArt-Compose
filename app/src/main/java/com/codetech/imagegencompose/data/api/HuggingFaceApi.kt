package com.codetech.imagegencompose.data.api

import com.codetech.imagegencompose.data.model.GenerationRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface HuggingFaceApi {
    @POST("hf-inference/models/black-forest-labs/FLUX.1-schnell")
    suspend fun generateImage(
        @Header("Authorization") token: String,
        @Body request: GenerationRequest
    ) : Response<ResponseBody>
}