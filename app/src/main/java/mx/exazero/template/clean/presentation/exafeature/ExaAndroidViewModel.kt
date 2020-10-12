package mx.exazero.template.clean.presentation.exafeature

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import mx.exazero.template.clean.core.presentation.BaseAndroidViewModel

/**
 *  Created by JAzcorra96 on 10/12/2020
 */
class ExaAndroidViewModel @ViewModelInject constructor(
    app: Application
): BaseAndroidViewModel(app) {
    private val _exaViewState = MutableLiveData<ExaViewState>()
    val exaViewState: LiveData<ExaViewState> get() = _exaViewState

}