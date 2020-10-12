package mx.exazero.template.clean.core.exception

/**
 *  Created by JAzcorra96 on 10/11/2020
 */
class PlayStoreFailure {
    object ServiceDisconnected : Failure.FeatureFailure()
    object ServiceUnavailable : Failure.FeatureFailure()
    object ProductAlreadyOwned : Failure.FeatureFailure()
    object DeveloperError : Failure.FeatureFailure()
    object UnknownError : Failure.FeatureFailure()
    object PurchaseError : Failure.FeatureFailure()
    object EmptyResponse : Failure.FeatureFailure()
    object PurchaseCanceled: Failure.FeatureFailure()
}