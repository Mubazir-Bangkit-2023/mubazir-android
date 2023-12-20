package com.foodwaste.mubazir.presentation.myfoodpost

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foodwaste.mubazir.domain.model.FoodPostDetail
import com.foodwaste.mubazir.domain.usecase.DeletePostUseCase
import com.foodwaste.mubazir.domain.usecase.GetUserPostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class MyFoodPostViewModel @Inject constructor(
    private val getUserPostsUseCase: GetUserPostsUseCase,
    private val deletePostUseCase: DeletePostUseCase
) : ViewModel() {

    private val _snackbar = MutableSharedFlow<String>()
    val snackbar = _snackbar.asSharedFlow()

    private val _postList = MutableStateFlow<List<FoodPostDetail>>(emptyList())
    val postList = _postList.asStateFlow()

    init {
        getUserPosts()
    }

    private fun getUserPosts() {
        viewModelScope.launch {
            getUserPostsUseCase().onFailure {
                Timber.e(it)
                _snackbar.emit("Failed to get data")
            }.onSuccess {
                _postList.value = it
            }
        }
    }

    fun deletePost(id: String) {
        viewModelScope.launch {
            deletePostUseCase(id).onFailure {
                Timber.e(it)
                _snackbar.emit("Failed to delete post")
            }.onSuccess {
                _snackbar.emit("Post deleted")
                getUserPosts()
            }
        }
    }

}