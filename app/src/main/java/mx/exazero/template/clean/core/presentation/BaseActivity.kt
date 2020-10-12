package mx.exazero.template.clean.core.presentation

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import mx.exazero.template.clean.core.exception.Failure

/**
 *  Created by JAzcorra96 on 10/11/2020
 */

abstract class BaseActivity: AppCompatActivity(), OnFailure {

    abstract fun layoutId(): Int
    abstract val fragmentContainer: FragmentContainerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    internal fun hideKeyBoard(){
        val view = this.findViewById<View>(android.R.id.content)
        if (view != null) {
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun handleFailure(failure: Failure?) {
        //TODO("Not yet implemented")
    }

    abstract fun showProgress(show: Boolean)
    //abstract fun navToSubscription()
    abstract fun logout()

    abstract fun enableBottomNav(enable: Boolean)
    abstract fun enableSideMenu(enable: Boolean)

}