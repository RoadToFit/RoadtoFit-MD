package com.example.roadtofit.ui.body

import android.content.ContentResolver
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roadtofit.data.model.UserPreference
import com.example.roadtofit.data.response.BodyResponse
import com.example.roadtofit.data.utils.Event
import kotlinx.coroutines.launch
import java.io.IOException
import android.util.Base64
import android.util.Log
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileNotFoundException

class BodyViewModel(private val repo: UserPreference): ViewModel() {

    val bodyResponse: LiveData<BodyResponse> = repo.bodyResponse
    val isLoading: LiveData<Boolean> = repo.isLoading

    fun uploadImage(image: ByteArray) {
        viewModelScope.launch {
            val requestImagePart = image.toRequestBody("image/*".toMediaTypeOrNull())
            val imagePart = MultipartBody.Part.createFormData("image", "image.jpg", requestImagePart)
            repo.uploadImage(imagePart)
        }
    }


}