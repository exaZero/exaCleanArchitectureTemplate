package mx.exazero.template.clean.core.extension

import android.view.View

/**
 *  Created by JAzcorra96 on 10/11/2020
 */

fun isGone(isIt: Boolean) = when(isIt){
    true -> View.GONE
    false -> View.VISIBLE
}

fun isInvisible(isIt: Boolean) = when(isIt){
    true -> View.INVISIBLE
    false -> View.VISIBLE
}