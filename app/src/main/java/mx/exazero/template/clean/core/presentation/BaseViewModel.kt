package mx.exazero.template.clean.core.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mx.exazero.template.clean.core.exception.Failure

/**
 *  Created by JAzcorra96 on 10/11/2020
 */

abstract class BaseViewModel: ViewModel() {
    var failure: MutableLiveData<Failure> = MutableLiveData()

    protected fun handleFailure(failure: Failure){
        this.failure.value = failure
    }
}