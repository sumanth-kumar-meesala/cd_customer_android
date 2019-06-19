package au.com.emerg.taxitowncars.activites

import android.os.Bundle
import androidx.fragment.app.Fragment
import au.com.emerg.taxitowncars.R
import au.com.emerg.taxitowncars.fragments.HomeFragment

class HomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        addFragment(HomeFragment())
    }

    private fun showFragment(fragment: Fragment) {
        val fM = supportFragmentManager
        val fT = fM.beginTransaction()
        fT.show(fragment).commit()

    }

    private fun addFragment(fragment: Fragment?) {
        if (fragment != null) {
            val manager = supportFragmentManager
            val ft = manager.beginTransaction()

            val tag = fragment::class.java.simpleName
            ft.addToBackStack(tag)
            ft.add(R.id.frame_layout, fragment, tag)
            ft.commitAllowingStateLoss()
        }
    }
}
