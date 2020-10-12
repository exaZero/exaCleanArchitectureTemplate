package mx.exazero.template.clean.core.extension

import kotlinx.coroutines.CancellableContinuation
import kotlin.coroutines.resume

/**
 *  Created by JAzcorra96 on 10/11/2020
 */

fun <T> CancellableContinuation<T>.resumeIfActive(value: T): Unit = if (this.isActive) this.resume(value) else Unit