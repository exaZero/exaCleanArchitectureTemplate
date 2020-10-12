package mx.exazero.template.clean.core.exception

import androidx.annotation.StringRes

/**
 *  Created by JAzcorra96 on 10/11/2020
 */

/**
 * Base Class for handling errors/failures/exceptions.
 * Every feature specific failure should extend [FeatureFailure] class.
 */
sealed class Failure {
    data class ServerError (val code: Int, val serverMessage: String) : Failure()
    object NetworkConnection : Failure()
    object DatabaseError : Failure()
    object Unauthorized: Failure()

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure(@StringRes val defaultMessage: Int? = null) : Failure()
}