package mx.exazero.template.clean.core.extension

import android.content.Context
import android.view.View
import mx.exazero.template.clean.core.presentation.BaseActivity
import mx.exazero.template.clean.core.presentation.BaseFragment

/**
 *  Created by JAzcorra96 on 10/11/2020
 */

val BaseFragment.viewContainer: View get() = (activity as BaseActivity).fragmentContainer

val BaseFragment.appContext: Context get() = activity?.applicationContext!!