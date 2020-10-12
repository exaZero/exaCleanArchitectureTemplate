package mx.exazero.template.clean.core.presentation

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import mx.exazero.template.clean.R

/**
 *  Created by JAzcorra96 on 10/11/2020
 */

abstract class BaseDialogFragment: DialogFragment() {
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog?.window?.attributes?.windowAnimations = R.style.DialogAnimFade
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.DialogFragmentStyle)
    }
}