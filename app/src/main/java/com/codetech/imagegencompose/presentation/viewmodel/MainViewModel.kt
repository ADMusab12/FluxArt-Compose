package com.codetech.imagegencompose.presentation.viewmodel

import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codetech.imagegencompose.data.api.HuggingFaceApi
import com.codetech.imagegencompose.data.model.GenerationRequest
import com.codetech.imagegencompose.data.model.GenerationState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MainViewModel: ViewModel() {
    private val _generationState = MutableStateFlow<GenerationState>(GenerationState.Idle)
    val generationState: StateFlow<GenerationState> = _generationState

    private val HF_TOKEN = "Bearer your_api_key"

    private val api: HuggingFaceApi by lazy {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()

        Retrofit.Builder()
            .baseUrl("https://router.huggingface.co/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HuggingFaceApi::class.java)
    }

    fun generateImage(prompt: String){
        if (prompt.isBlank()){
            _generationState.value = GenerationState.Error("Prompt cannot be empty")
            return
        }

        viewModelScope.launch {
            _generationState.value = GenerationState.Loading

            try {
                val response = api.generateImage(
                    token = HF_TOKEN,
                    request = GenerationRequest(inputs = prompt)
                )

                if (response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null){
                        val bytes = responseBody.bytes()
                        val bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.size)
                        if (bitmap != null){
                            _generationState.value = GenerationState.Success(bitmap)
                        }else{
                            _generationState.value = GenerationState.Error("Failed to decode image")
                        }
                    }else{
                        _generationState.value = GenerationState.Error("Empty response body")
                    }
                }else{
                    _generationState.value = GenerationState.Error("API Error: ${response.code()} ${response.message()}")
                    Log.e("checkError", "generateImage: API Error: ${response.code()} ${response.message()}")
                }
            }catch (e: Exception){
                _generationState.value = GenerationState.Error("Network Error: ${e.localizedMessage}")
            }
        }
    }
}