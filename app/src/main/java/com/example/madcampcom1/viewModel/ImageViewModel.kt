package com.example.madcampcom1.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.madcampcom1.data.local.entity.ImageEntity
import com.example.madcampcom1.data.repository.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor(private val repository: ImageRepository) : ViewModel(){
    private val _imageList = MutableStateFlow<List<ImageEntity>>(emptyList())
    val imageList = _imageList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllImagess().distinctUntilChanged().collect { listOfImages ->
                if (listOfImages.isNullOrEmpty()) {
                    Log.d("Empty", ":Empty list")
                } else {
                    _imageList.value = listOfImages
                }

            }
        }
    }
    fun addImage(image: ImageEntity) = viewModelScope.launch {
        repository.addImage(image)
    }
    fun deleteImage(image: ImageEntity) = viewModelScope.launch {
        repository.removeImage(image)
    }
}