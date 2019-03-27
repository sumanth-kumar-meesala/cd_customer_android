package au.com.emerg.taxitowncars.activites

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import au.com.emerg.taxitowncars.R
import au.com.emerg.taxitowncars.fragments.MapFragment
import au.com.emerg.taxitowncars.fragments.NewBookingFragment
import au.com.emerg.taxitowncars.utils.PreferenceUtils
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    internal var mapFragment: MapFragment? = null
    internal var newBookingFragment: NewBookingFragment? = null
    internal var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        toggle.syncState()

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        val tv_name = navigationView.getHeaderView(0).findViewById<TextView>(R.id.tv_name)
        val tv_email = navigationView.getHeaderView(0).findViewById<TextView>(R.id.tv_email)

        tv_name.text = PreferenceUtils.getName(this)
        tv_email.text = PreferenceUtils.getEmail(this)

        mapFragment = MapFragment()
        newBookingFragment = NewBookingFragment()

        addFragment(mapFragment)
        addFragment(newBookingFragment)

        val bottom_nav_view = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottom_nav_view.setOnNavigationItemSelectedListener(object :
            BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(@NonNull menuItem: MenuItem): Boolean {
                val fM = supportFragmentManager

                when (menuItem.itemId) {
                    R.id.nav_map -> showFragment(mapFragment as Fragment)
                    R.id.nav_new_booking -> showFragment(newBookingFragment as Fragment)
                    R.id.nav_bookings -> showFragment(newBookingFragment as Fragment)
                }

                return true
            }
        })
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.nav_logout -> {
                PreferenceUtils.setName(null, this)
                PreferenceUtils.setEmail(null, this)
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }

        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    private fun showFragment(fragment: Fragment) {
        val fM = supportFragmentManager
        val fT = fM.beginTransaction()
        fT.hide(mapFragment as Fragment)
        fT.hide(newBookingFragment as Fragment)
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

    override fun onBackPressed() {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed()
                return
            }

            this.doubleBackToExitPressedOnce = true
            showToast(R.string.click_back_again)

            Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
        }
    }
}
