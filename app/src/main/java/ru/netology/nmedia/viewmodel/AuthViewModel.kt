package ru.netology.nmedia.viewmodel

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.netology.nmedia.auth.AppAuth
import ru.netology.nmedia.auth.AuthState
import ru.netology.nmedia.db.AppDb
import ru.netology.nmedia.model.AuthStates
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryImpl
import ru.netology.nmedia.util.SingleLiveEvent
import java.io.IOException

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    val data: LiveData<AuthState> = AppAuth.getInstance()
        .authStateFlow
        .asLiveData(Dispatchers.Default)
    val authenticated: Boolean
        get() = AppAuth.getInstance().authStateFlow.value.id != 0L
    private val repository: PostRepository =
        PostRepositoryImpl(AppDb.getInstance(context = application).postDao())

    private val _dataState = MutableLiveData<AuthStates>()
    val dataState: LiveData<AuthStates>
        get() = _dataState

 val logger = SingleLiveEvent<Unit>()

fun logUser(log: String, pass: String) = viewModelScope.launch{
    logger.value = Unit
    try {
        _dataState.value = AuthStates(loading = true)
        repository.logUser(log,pass)
    } catch (e: IOException) {
       _dataState.value = AuthStates(error = true)
    } catch (e: Exception) {
        _dataState.value = AuthStates(error = true)
    }
    }


}