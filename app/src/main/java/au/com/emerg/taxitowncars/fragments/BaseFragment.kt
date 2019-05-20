package au.com.emerg.taxitowncars.fragments

import android.widget.Toast
import androidx.fragment.app.Fragment
import au.com.emerg.taxitowncars.R
import au.com.emerg.taxitowncars.views.Loading

abstract class BaseFragment : Fragment() {

    private var loading: Loading? = null

    fun showToast(resourceId: Int) {
        Toast.makeText(context, resourceId, Toast.LENGTH_SHORT).show()
    }

    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun errorToast() {
        Toast.makeText(context, R.string.error_message, Toast.LENGTH_SHORT).show()
    }

    fun showLoading() {
        if (loading == null && activity != null) {
            loading = Loading(activity!!)
        }

        loading?.show()
    }

    fun hideLoading() {
        loading?.dismiss()
    }
}