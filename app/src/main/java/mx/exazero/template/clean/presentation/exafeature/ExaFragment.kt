package mx.exazero.template.clean.presentation.exafeature

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import mx.exazero.template.clean.R
import mx.exazero.template.clean.core.extension.failure
import mx.exazero.template.clean.core.extension.observe
import mx.exazero.template.clean.core.presentation.BaseFragment

/**
 *  Created by JAzcorra96 on 10/11/2020
 */
@AndroidEntryPoint
class ExaFragment: BaseFragment(R.layout.exa_fragment) {
    //private val args by navArgs<ExaFragmentArgs>()
    private val exaViewModel by viewModels<ExaViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exaViewModel.apply {
            observe(exaViewState, ::onViewStateChanged)
            failure(failure, ::handleFailure)
        }
    }

    private fun onViewStateChanged(viewState: ExaViewState?) {
        when(viewState){
            else -> TODO("Not Implemented")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}