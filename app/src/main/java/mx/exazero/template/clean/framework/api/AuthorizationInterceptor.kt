package mx.exazero.template.clean.framework.api

import okhttp3.Interceptor
import okhttp3.Response

/**
 *  Created by JAzcorra96 on 10/11/2020
 */
class AuthorizationInterceptor: Interceptor {
    private var authToken: String? = null
    override fun intercept(chain: Interceptor.Chain): Response {
        return if(authToken!=null){
            val original = chain.request()
            val builder = original.newBuilder()
                .method(original.method, original.body)
            builder.header("Authorization", "Bearer $authToken")
            chain.proceed(builder.build())
        } else{
            chain.proceed(chain.request())
        }
    }

    fun setAuthToken(token: String?){
        authToken = token
    }
}