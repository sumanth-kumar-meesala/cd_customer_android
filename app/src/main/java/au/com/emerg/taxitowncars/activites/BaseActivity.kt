package au.com.emerg.taxitowncars.activites

import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import au.com.emerg.taxitowncars.R
import au.com.emerg.taxitowncars.views.Loading

abstract class BaseActivity : AppCompatActivity() {

    private var loading: Loading? = null

    override fun finish() {
        super.finish()
        overridePendingTransitionExit()
    }

    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
        overridePendingTransitionEnter()
    }

    private fun overridePendingTransitionEnter() {
        overridePendingTransition(
            R.anim.slide_from_right,
            R.anim.slide_to_left
        )
    }

    private fun overridePendingTransitionExit() {
        overridePendingTransition(
            R.anim.slide_from_left,
            R.anim.slide_to_right
        )
    }

    fun showToast(resourceId: Int) {
        Toast.makeText(this, resourceId, Toast.LENGTH_SHORT).show()
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun errorToast() {
        Toast.makeText(this, R.string.error_message, Toast.LENGTH_SHORT).show()
    }

    fun showLoading() {
        if (loading == null) {
            loading = Loading(this)
        }

        loading?.show()
    }

    fun hideLoading() {
        loading?.dismiss()
    }
}