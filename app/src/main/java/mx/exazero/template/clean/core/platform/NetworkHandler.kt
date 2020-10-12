package mx.exazero.template.clean.core.platform

import android.content.Context
import mx.exazero.template.clean.core.extension.networkInfo
import javax.inject.Inject
import javax.inject.Singleton

/**
 *  Created by JAzcorra96 on 10/11/2020
 */

class NetworkHandler @Inject constructor(private val context: Context){
    val isConnected get() = context.networkInfo?.isConnectedOrConnecting
}