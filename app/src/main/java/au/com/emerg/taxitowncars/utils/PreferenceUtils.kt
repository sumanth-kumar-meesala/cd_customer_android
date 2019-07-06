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

        fun setCustomerId(customerId: Long, context: Context) {
            PreferenceManager.getDefaultSharedPreferences(context).edit().putLong("customerId", customerId)
                .apply()
        }

        fun getCustomerId(context: Context): Long {
            return PreferenceManager.getDefaultSharedPreferences(context).getLong("customerId", 0)
        }

        fun setOwnerId(ownerId: Long, context: Context) {
            PreferenceManager.getDefaultSharedPreferences(context).edit().putLong("ownerId", ownerId)
                .apply()
        }

        fun getOwnerId(context: Context): Long {
            return PreferenceManager.getDefaultSharedPreferences(context).getLong("ownerId", 0)
        }

        fun setAccent1(color: Int, context: Context) {
            PreferenceManager.getDefaultSharedPreferences(context).edit().putInt("accent1", color)
                .apply()
        }

        fun getAccent1(context: Context): Int {
            return PreferenceManager.getDefaultSharedPreferences(context).getInt("accent1", 0)
        }

        fun setAccent2(color: Int, context: Context) {
            PreferenceManager.getDefaultSharedPreferences(context).edit().putInt("accent2", color)
                .apply()
        }

        fun getAccent2(context: Context): Int {
            return PreferenceManager.getDefaultSharedPreferences(context).getInt("accent2", 0)
        }

        fun setStatus(status: Boolean, context: Context) {
            PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean("status", status)
                .apply()
        }

        fun getStatus(context: Context): Boolean {
            return PreferenceManager.getDefaultSharedPreferences(context).getBoolean("status", false)
        }
    }
}