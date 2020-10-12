package mx.exazero.template.clean

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentContainerView
import mx.exazero.template.clean.core.presentation.BaseActivity

class ExaActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId())
    }

    override fun layoutId(): Int = R.layout.exa_activity

    override val fragmentContainer: FragmentContainerView
        get() = TODO("Not yet implemented")

    override fun showProgress(show: Boolean) {
        TODO("Not yet implemented")
    }

    override fun logout() {
        TODO("Not yet implemented")
    }

    override fun enableBottomNav(enable: Boolean) {
        TODO("Not yet implemented")
    }

    override fun enableSideMenu(enable: Boolean) {
        TODO("Not yet implemented")
    }
}