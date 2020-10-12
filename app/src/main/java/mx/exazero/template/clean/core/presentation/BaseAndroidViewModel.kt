package mx.exazero.template.clean.core.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import mx.exazero.template.clean.core.exception.Failure

/**
 *  Created by JAzcorra96 on 10/11/2020
 */

abstract class BaseAndroidViewModel constructor (appContext: Application): AndroidViewModel(appContext){
    var failure: MutableLiveData<Failure> = MutableLiveData()

    protected fun handleFailure(failure: Failure){
        this.failure.value = failure
    }
}