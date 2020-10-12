package mx.exazero.template.clean.presentation.exafeature

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import mx.exazero.template.clean.core.presentation.BaseViewModel

/**
 *  Created by JAzcorra96 on 10/11/2020
 */
class ExaViewModel @ViewModelInject constructor(

): BaseViewModel() {
    private val _exaViewState = MutableLiveData<ExaViewState>()
    val exaViewState: LiveData<ExaViewState> get() = _exaViewState
}