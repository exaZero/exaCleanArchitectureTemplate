package mx.exazero.template.clean.presentation.exafeature

import mx.exazero.template.clean.core.exception.Failure

/**
 *  Created by JAzcorra96 on 10/11/2020
 */
sealed class ExaFailure {
    object SampleFailure: Failure.FeatureFailure()
}