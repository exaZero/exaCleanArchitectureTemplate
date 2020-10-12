package mx.exazero.template.clean.core.presentation

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import mx.exazero.template.clean.R
import mx.exazero.template.clean.core.exception.Failure
import mx.exazero.template.clean.core.extension.appContext
import mx.exazero.template.clean.core.extension.viewContainer

/**
 *  Created by JAzcorra96 on 10/11/2020
 */

abstract class BaseFragment(@LayoutRes layoutId: Int): Fragment(layoutId), OnFailure{

    val navController by lazy { findNavController() }
    val baseActivity by lazy { requireActivity() as BaseActivity }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if(!onBackPressed()){
                        isEnabled = false
                        requireActivity().onBackPressed()
                    }
                }

            })

        baseActivity.enableBottomNav(useBottomNav())
        baseActivity.enableSideMenu(useSideMenu())
        baseActivity.window.statusBarColor = resources.getColor(statusBarColor())
        baseActivity.window.setBackgroundDrawable(windowBackgroundDrawable())
    }

    internal fun hideKeyboard(){
        (activity as? BaseActivity)?.hideKeyBoard()
    }

    internal fun notify(@StringRes message: Int){
        Snackbar.make(viewContainer, message, Snackbar.LENGTH_SHORT).show()
    }

    internal fun notify(message: String){
        Snackbar.make(viewContainer, message, Snackbar.LENGTH_SHORT).show()
    }

    internal fun notifyWithAction(@StringRes message: Int, @StringRes actionText: Int, action: () -> Any){
        Snackbar.make(viewContainer, message, Snackbar.LENGTH_INDEFINITE).apply {
            setAction(actionText){_ -> action.invoke()}
            setActionTextColor(ContextCompat.getColor(appContext, R.color.colorAccent))
        }.show()
    }

    internal fun notifyWithAction(message: String, @StringRes actionText: Int, action: () -> Any){
        Snackbar.make(viewContainer, message, Snackbar.LENGTH_INDEFINITE).apply {
            setAction(actionText){_ -> action.invoke()}
            setActionTextColor(ContextCompat.getColor(appContext, R.color.colorAccent))
        }.show()
    }

    open fun showLoader(show: Boolean){
        baseActivity.showProgress(show)
    }

    /**
     * @return: indicates if the backPressed was handled by the fragment.
     * If false it will call activity's default navigation up, else it won't.
     */
    open fun onBackPressed(): Boolean{
        return false
    }

    open fun useSideMenu() = false
    open fun useBottomNav() = false

    open fun statusBarColor() = R.color.colorPrimary
    open fun windowBackgroundDrawable(): Drawable? = ColorDrawable(resources.getColor(R.color.colorWindowBg))

    override fun handleFailure(failure: Failure?) {
        showLoader(false)
        when (failure){
            //is Failure.ServerError -> notify(failure.errorResponse.message?:getString(R.string.failure_server_error))
            //is Failure.DatabaseError -> notify(getString(R.string.failure_database_error))
            //is Failure.NetworkConnection -> notify(getString(R.string.failure_network_connection))
            is Failure.FeatureFailure -> failure.defaultMessage?.let { notify(it) }
        }

    }

}