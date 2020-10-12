package mx.exazero.template.clean.core.presentation

import mx.exazero.template.clean.core.exception.Failure

/**
 *  Created by JAzcorra96 on 10/11/2020
 */
interface OnFailure {
    fun handleFailure(failure: Failure?)
}