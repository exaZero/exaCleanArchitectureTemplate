package mx.exazero.template.clean.core.platform

import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonParseException
import mx.exazero.template.clean.domain.model.User

/**
 *  Created by JAzcorra96 on 10/11/2020
 */

class AuthManager(private val context: Context) {
    companion object {
        private const val AUTH_PREFERENCES = "AuthPreferences"

        private const val USER_OBJECT = "pkey_user_object"
        private const val AUTH_TOKEN = "pkey_token"

        private const val FCM_USER_TOKEn = "pkey_fcm_user_token"
        private const val FCM_DEVICE_TOKEN = "pkey_fcm_device_token"
    }

    private val prefs = context.getSharedPreferences(AUTH_PREFERENCES, Context.MODE_PRIVATE)
    private val gson = Gson()

    val isAuthenticated: Boolean
        get() = accessToken != null
    val currentUser: User?
        get() = prefs.getString(USER_OBJECT, null)?.let { Gson().fromJson(it, User::class.java) }
    val userFcmToken: String?
        get() = prefs.getString(FCM_USER_TOKEn, null)
    val deviceFcmToken: String?
        get() = prefs.getString(FCM_DEVICE_TOKEN, null)
    var accessToken: String?
        get() = prefs.getString(AUTH_TOKEN, null)
        private set(value) { prefs.edit().putString(AUTH_TOKEN, value).apply() }

    fun setAuthToken(token: String?){
        accessToken = token
    }

    fun setUser(user: User){
        prefs.edit().putString(USER_OBJECT, Gson().toJson(user)).apply()
    }

    fun updateUser(user: User){
        if(currentUser?.id?:-1 == user.id){
            setUser(user)
        }
    }

    fun setUserFcmToken(token: String?){
        prefs.edit().putString(FCM_USER_TOKEn, token).apply()
    }

    fun setDeviceFcmToken(token: String?){
        prefs.edit().putString(FCM_DEVICE_TOKEN, token).apply()
    }

    fun clearUser(){
        prefs.edit().putString(USER_OBJECT, null).apply()
    }

}