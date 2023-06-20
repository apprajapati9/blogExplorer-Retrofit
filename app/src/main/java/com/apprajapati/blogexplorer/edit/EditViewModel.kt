package com.apprajapati.blogexplorer.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apprajapati.blogexplorer.api.RetrofitInstance
import com.apprajapati.blogexplorer.models.Post
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "EditViewModel"
class EditViewModel : ViewModel() {
    private val _post: MutableLiveData<Post?> = MutableLiveData()
    val post: LiveData<Post?>
        get() = _post

    private val _currentStatus = MutableLiveData<ResultStatus>(ResultStatus.IDLE)
    val currentStatus: LiveData<ResultStatus>
        get() = _currentStatus

    private val _isDeleteSuccess = MutableLiveData<Boolean>(false)
    val isDeleteSuccess : LiveData<Boolean>
        get() = _isDeleteSuccess

    fun updatePost(postId: Int, newPostData: Post) {

            _currentStatus.value = ResultStatus.WORKING
            viewModelScope.launch {
                try {
                    _post.value = null
                    val updatedPost = RetrofitInstance.api.updatePost(postId, newPostData)
                    _post.value = updatedPost
                    _currentStatus.value = ResultStatus.SUCCESS
                }catch (e: Exception){
                    _currentStatus.value = ResultStatus.ERROR
                }
            }
        }



    fun patchPost(postId: Int, title: String, body: String) {
            viewModelScope.launch {
                try {
                    _currentStatus.value = ResultStatus.WORKING
                    _post.value = null
                    val patchedPost = RetrofitInstance.api.patchPost(
                        postId,
                        mapOf("title" to title, "body" to body)
                    )

                    _post.value = patchedPost
                    _currentStatus.value = ResultStatus.SUCCESS
                }catch (e: Exception){
                    _currentStatus.value = ResultStatus.ERROR
                }
            }
    }

    fun deletePost(postId: Int) {
        viewModelScope.launch {
            try{
                _currentStatus.value = ResultStatus.WORKING
                _post.value = null
                RetrofitInstance.api.deletePost("ajay-token-123", postId)
                _isDeleteSuccess.value = true
                _currentStatus.value = ResultStatus.SUCCESS

            }
            catch (e: Exception){
                _currentStatus.value = ResultStatus.ERROR
                _isDeleteSuccess.value = false
            }
        }
    }

    /*
        Example of how to use your own corouting, even when you are not using MVVM, you can do this
        with method below.
     */
    private fun createPost(){
        CoroutineScope(Dispatchers.IO).launch {
            _currentStatus.value = ResultStatus.WORKING
            _post.value = null
            try{
                RetrofitInstance.api.createPost(Post(2,22, "Ajay title", "AJay body"))
                _currentStatus.value = ResultStatus.SUCCESS
            }catch (e:Exception){
                _currentStatus.value = ResultStatus.ERROR
            }
        }
    }
}