package mx.exazero.template.clean.core.interactor

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import mx.exazero.template.clean.core.exception.Failure
import mx.exazero.template.clean.core.functional.Either

/**
 *  Created by JAzcorra96 on 10/11/2020
 */

abstract class UseCaseLive<out Type, Progress, in Params> where Type : Any, Progress : Any {
    abstract suspend fun run(params: Params): Either<Failure, Type>

    private lateinit var reportTo: ((Progress) -> Unit)

    operator fun invoke(params: Params,
                        onProgress: ((Progress) -> Unit),
                        onResult: (Either<Failure, Type>) -> Unit = {}){
        reportTo = onProgress
        val job = GlobalScope.async(Dispatchers.IO) { run(params) }
        GlobalScope.launch(Dispatchers.Main) { onResult(job.await()) }
    }

    protected fun emitProgress(progress: Progress){
        GlobalScope.launch(Dispatchers.Main) {
            reportTo(progress)
        }
    }
}