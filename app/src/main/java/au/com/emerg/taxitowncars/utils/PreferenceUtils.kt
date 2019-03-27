package au.com.emerg.taxitowncars.utils

import android.content.Context
import android.preference.PreferenceManager

class PreferenceUtils {
    companion object {
        fun setName(name: String?, context: Context) {
            PreferenceManager.getDefaultSharedPreferences(context).edit().putString("name", name).apply()
        }

        fun getName(context: Context): String {
            return PreferenceManager.getDefaultSharedPreferences(context).getString("name", "Unknown")!!
        }

        fun setEmail(name: String?, context: Context) {
            PreferenceManager.getDefaultSharedPreferences(context).edit().putString("email", name).apply()
        }

        fun getEmail(context: Context): String {
            return PreferenceManager.getDefaultSharedPreferences(context).getString("email", "Unknown")!!
        }
    }
}