package com.prasanna.skoproject.presentation.user_list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.compose.collectAsLazyPagingItems
import com.prasanna.skoproject.domain.model.User
import com.prasanna.skoproject.domain.use_case.UsersUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class UserListViewModel @Inject constructor(private val useCases:UsersUseCases):ViewModel() {
    private var _userList:MutableStateFlow<PagingData<User>> = MutableStateFlow(PagingData.empty())
    val userList: StateFlow<PagingData<User>> = _userList
//    var userList:LiveData<PagingData<User>>? = null
    init {
        viewModelScope.launch {
            useCases.getUserList().cachedIn(viewModelScope).collect{
                _userList.value = it
            }
        }
    }
}