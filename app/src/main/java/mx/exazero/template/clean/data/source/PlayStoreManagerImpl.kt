package mx.exazero.template.clean.data.source

import android.app.Activity
import android.content.Context
import com.android.billingclient.api.*
import kotlinx.coroutines.suspendCancellableCoroutine
import mx.exazero.template.clean.core.exception.Failure
import mx.exazero.template.clean.core.exception.PlayStoreFailure
import mx.exazero.template.clean.core.extension.resumeIfActive
import mx.exazero.template.clean.core.functional.Either
import mx.exazero.template.clean.core.functional.Either.Left
import mx.exazero.template.clean.core.functional.Either.Right
import mx.exazero.template.clean.domain.repository.PlayStoreManager
import kotlin.coroutines.resume

/**
 *  Created by JAzcorra96 on 10/11/2020
 */
class PlayStoreManagerImpl(
    private val context: Context
): PlayStoreManager {
    private var isConnected = false
    private var billingClient: BillingClient? = null

    private suspend fun checkConnection(): Boolean{
        if(isConnected && billingClient != null){
            return true
        }
        return retryConnection(2)
    }

    private suspend fun startConnection(): Boolean{
        isConnected = false
        billingClient?.endConnection()
        val newClient = BillingClient.newBuilder(context).setListener(mPurchaseListener).enablePendingPurchases().build()
        billingClient = newClient
        return suspendCancellableCoroutine {coroutine ->
            newClient.startConnection(object : BillingClientStateListener {
                override fun onBillingServiceDisconnected() {
                    isConnected = false
                    coroutine.resumeIfActive(false)
                }

                override fun onBillingSetupFinished(p0: BillingResult) {
                    isConnected = true
                    coroutine.resumeIfActive(true)
                }
            })
        }
    }

    private suspend fun retryConnection(retries: Int): Boolean{
        var currentRetries = 0
        while (currentRetries++ <= retries){
            if(startConnection()){
                return true
            }
        }
        return false
    }

    private val mPurchaseListener = PurchasesUpdatedListener { billingResult, purchasesList ->
        mInternalPurchaseListener?.onPurchasesUpdated(billingResult, purchasesList)
    }

    private var mInternalPurchaseListener: PurchasesUpdatedListener? = null

    override suspend fun getProducts(skuList: List<String>): Either<Failure, List<SkuDetails>> {
        if(!checkConnection()){
            return Left(PlayStoreFailure.ServiceUnavailable)
        }
        return  suspendCancellableCoroutine {coroutine ->
            val skuDetailsParams = SkuDetailsParams.newBuilder()
                .setType(BillingClient.SkuType.SUBS)
                .setSkusList(skuList)
                .build()
            billingClient?.querySkuDetailsAsync(skuDetailsParams) { billingResult, skuDetailsList ->
                when(billingResult.responseCode){
                    BillingClient.BillingResponseCode.OK ->{
                        if(skuDetailsList == null ){
                            coroutine.resume(Left(PlayStoreFailure.EmptyResponse))
                        }
                        else{
                            coroutine.resume(Right(skuDetailsList))
                        }
                    }
                    else -> {
                        coroutine.resume(Left(PlayStoreFailure.PurchaseError))
                    }
                }
            }
        }
    }

    override suspend fun purchaseSubscription(
        activity: Activity,
        subscription: SkuDetails,
        oldSubPurchase: Purchase?,
        @BillingFlowParams.ProrationMode prorationMode: Int
    ): Either<Failure, Purchase> {
        if(!checkConnection()){
            return Left(PlayStoreFailure.ServiceUnavailable)
        }
        return suspendCancellableCoroutine {
            //val proRationMode = if(oldSubPurchase?.orderId.orEmpty() == BuildConfig.SKU_SUB_YEARLY) BillingFlowParams.ProrationMode.DEFERRED else BillingFlowParams.ProrationMode.IMMEDIATE_AND_CHARGE_PRORATED_PRICE
            val purchaseResponse = billingClient?.launchBillingFlow(
                activity,
                BillingFlowParams.newBuilder()
                    .setSkuDetails(subscription)
                    .setOldSku(oldSubPurchase?.sku.orEmpty(), oldSubPurchase?.purchaseToken.orEmpty())
                    .setReplaceSkusProrationMode(prorationMode)
                    .build()
            )
            when(purchaseResponse?.responseCode){
                BillingClient.BillingResponseCode.OK -> {
                    mInternalPurchaseListener =
                        PurchasesUpdatedListener { billingResult, purchaseList ->
                            when(billingResult.responseCode){
                                BillingClient.BillingResponseCode.OK -> {
                                    val mPurchase = purchaseList?.getOrNull(0)
                                    if(mPurchase == null){
                                        it.resumeIfActive(Left(PlayStoreFailure.PurchaseError))
                                    }
                                    else{
                                        it.resumeIfActive(Right(mPurchase))
                                    }
                                }
                                else ->{
                                    it.resumeIfActive(Left(PlayStoreFailure.PurchaseError))
                                }
                            }
                        }
                }
                BillingClient.BillingResponseCode.USER_CANCELED -> {it.resumeIfActive(Left(PlayStoreFailure.PurchaseCanceled))}
                BillingClient.BillingResponseCode.DEVELOPER_ERROR -> {it.resumeIfActive(Left(PlayStoreFailure.DeveloperError))}
                BillingClient.BillingResponseCode.SERVICE_UNAVAILABLE -> {it.resumeIfActive(Left(PlayStoreFailure.ServiceUnavailable))}
                else -> {
                    it.resumeIfActive(Left(PlayStoreFailure.UnknownError))
                }
            }
        }
    }

    override suspend fun acknowledgePurchase(purchaseToken: String): Either<Failure, Boolean> {
        if(!checkConnection()){
            return Left(PlayStoreFailure.ServiceUnavailable)
        }
        return suspendCancellableCoroutine {
            billingClient?.acknowledgePurchase(
                AcknowledgePurchaseParams.newBuilder()
                    .setPurchaseToken(purchaseToken)
                    .build()
            ) { billingResult ->
                if(billingResult.responseCode == BillingClient.BillingResponseCode.OK){
                    it.resume(Right(true))
                }
                else{
                    it.resume(Left(PlayStoreFailure.UnknownError))
                }
            }
        }
    }

    override suspend fun getUserPurchases(): Either<Failure, List<Purchase>> {
        if(!checkConnection()){
            return Left(PlayStoreFailure.ServiceUnavailable)
        }
        return suspendCancellableCoroutine {
            val purchaseResponse = billingClient?.queryPurchases(BillingClient.SkuType.SUBS)
            when(purchaseResponse?.responseCode){
                BillingClient.BillingResponseCode.OK -> {
                    it.resume(Right(purchaseResponse.purchasesList?: emptyList()))
                }
                BillingClient.BillingResponseCode.SERVICE_UNAVAILABLE -> it.resume(Left(PlayStoreFailure.ServiceUnavailable))
                BillingClient.BillingResponseCode.DEVELOPER_ERROR -> it.resume(Left(PlayStoreFailure.DeveloperError))
                else -> it.resume(Left(PlayStoreFailure.UnknownError))
            }
        }
    }
}