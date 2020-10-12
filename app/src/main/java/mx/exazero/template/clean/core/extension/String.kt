package mx.exazero.template.clean.core.extension

import android.util.Patterns

/**
 *  Created by JAzcorra96 on 10/11/2020
 */

fun String.isEmail(): Boolean = Patterns.EMAIL_ADDRESS.matcher(this).matches()