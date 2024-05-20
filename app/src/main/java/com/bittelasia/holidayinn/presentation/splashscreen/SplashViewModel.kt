package com.bittelasia.holidayinn.presentation.splashscreen

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bittelasia.holidayinn.data.repository.stbpref.manager.STBDataStore.readSTB
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    app: Application
): ViewModel() {

    private val _onBoardingCompleted = MutableStateFlow(false)
    val onBoardingCompleted: StateFlow<Boolean> = _onBoardingCompleted
    init {
        viewModelScope.launch{
            app.readSTB(Dispatchers.IO){
                _onBoardingCompleted.value = it.FIRST_RUN == "1"
            }
        }
    }
}