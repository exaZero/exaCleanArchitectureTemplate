package mx.exazero.template.clean.data.api

import com.google.gson.annotations.SerializedName

/**
 *  Created by JAzcorra96 on 10/11/2020
 */

data class ApiPaginated<D>(
    @SerializedName("status") val status: Int,
    @SerializedName("data") val data: D,
    @SerializedName("totalRows") val totalItems: Int,
    @SerializedName("perPage") val itemsPerPage: Int,
    @SerializedName("currentPage") val currentPage: Int,
    @SerializedName("totalPages") val totalPages: Int
)