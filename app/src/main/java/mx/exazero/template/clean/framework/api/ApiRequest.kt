package mx.exazero.template.clean.framework.api

import com.google.gson.Gson
import com.google.gson.JsonParseException
import mx.exazero.template.clean.core.exception.Failure
import mx.exazero.template.clean.core.functional.Either
import mx.exazero.template.clean.core.functional.Either.Left
import mx.exazero.template.clean.core.functional.Either.Right
import mx.exazero.template.clean.data.api.ApiError
import retrofit2.Call
import timber.log.Timber

/**
 *  Created by JAzcorra96 on 10/11/2020
 */
interface ApiRequest {
    fun <T, R> makeRequest(call: Call<T>, transform: (T) -> R, default: T): Either<Failure, R> {
        return try {
            val response = call.execute()
            when (response.isSuccessful) {
                true -> Right(transform((response.body() ?: default)))
                false -> {
                    //Try parse error body as ApiError
                    val apiErrorMessage = try {
                        Gson().fromJson(response.errorBody()?.string()?:"", ApiError::class.java).errorMessage
                    } catch (e: JsonParseException){
                        "Server Error"
                    }
                    when(response.code()){
                        401 -> Left(Failure.Unauthorized)
                        else -> Left(Failure.ServerError(response.code(), apiErrorMessage))
                    }
                }
            }
        } catch (exception: Throwable) {
            Timber.d(exception,exception.message?:"exception message null")
            Left(Failure.ServerError(-1, "Unknown Request Error"))
        }
    }
}