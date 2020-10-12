package mx.exazero.template.clean.domain.repository

import android.app.Activity
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.SkuDetails
import mx.exazero.template.clean.core.exception.Failure
import mx.exazero.template.clean.core.functional.Either

/**
 *  Created by JAzcorra96 on 10/11/2020
 */
interface PlayStoreManager {
    suspend fun getProducts(skuList: List<String>): Either<Failure, List<SkuDetails>>
    suspend fun purchaseSubscription(activity: Activity, subscription: SkuDetails, oldSubPurchase: Purchase?, @BillingFlowParams.ProrationMode prorationMode: Int = BillingFlowParams.ProrationMode.IMMEDIATE_WITH_TIME_PRORATION): Either<Failure, Purchase>
    suspend fun acknowledgePurchase(purchaseToken: String): Either<Failure, Boolean>
    suspend fun getUserPurchases(): Either<Failure, List<Purchase>>
}