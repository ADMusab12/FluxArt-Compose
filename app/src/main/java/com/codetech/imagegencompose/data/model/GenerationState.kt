package com.codetech.imagegencompose.data.model

import android.graphics.Bitmap

sealed class GenerationState {
    object Idle : GenerationState()
    object Loading : GenerationState()
    data class Success(val bitmap: Bitmap) : GenerationState()
    data class Error(val message: String) : GenerationState()
}

data class GenerationRequest(
    val inputs: String,
    val parameters: Map<String, Any> = mapOf(
        "negative_prompt" to "blurry, low quality",
        "num_inference_steps" to 50,
        "guidance_scale" to 7.5
    )
)