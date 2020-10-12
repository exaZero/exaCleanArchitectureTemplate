package mx.exazero.template.clean.data.api

import com.google.gson.annotations.SerializedName

/**
 *  Created by JAzcorra96 on 10/11/2020
 */
data class ApiError(
    @SerializedName("error") val errorMessage: String
)