package au.com.emerg.taxitowncars.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import au.com.emerg.taxitowncars.R
import au.com.emerg.taxitowncars.models.AppCustomerSettingsResponse
import au.com.emerg.taxitowncars.models.BaseResponse
import au.com.emerg.taxitowncars.retrofit.RetrofitCallback
import au.com.emerg.taxitowncars.retrofit.RetrofitInstance
import au.com.emerg.taxitowncars.retrofit.RetrofitResult
import au.com.emerg.taxitowncars.utils.PreferenceUtils
import com.bumptech.glide.Glide

class HomeFragment : BaseFragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoading()

        val iv_bg = view.findViewById<ImageView>(R.id.iv_bg)
        val btn_my_bookings = view.findViewById<AppCompatButton>(R.id.btn_my_bookings)
        val btn_create_booking = view.findViewById<AppCompatButton>(R.id.btn_create_booking)
        val btn_my_profile = view.findViewById<AppCompatButton>(R.id.btn_my_profile)
        val btn_my_driver = view.findViewById<AppCompatButton>(R.id.btn_my_driver)

        btn_my_bookings.setOnClickListener(this)
        btn_create_booking.setOnClickListener(this)
        btn_my_profile.setOnClickListener(this)
        btn_my_driver.setOnClickListener(this)

        val customerId = PreferenceUtils.getCustomerId(context!!)

        RetrofitInstance.service.getAppCustomerSettings(customerId)
            .enqueue(RetrofitCallback(object : RetrofitResult<BaseResponse<AppCustomerSettingsResponse>> {
                override fun success(value: BaseResponse<AppCustomerSettingsResponse>) {

                    val data = value.data
                    Glide.with(context!!).load(data?.customLogoUrl).into(iv_bg)
                    btn_my_bookings.setTextColor(Color.parseColor(data?.colorAccent2))
                    btn_create_booking.setTextColor(Color.parseColor(data?.colorAccent2))
                    btn_my_profile.setTextColor(Color.parseColor(data?.colorAccent2))
                    btn_my_driver.setTextColor(Color.parseColor(data?.colorAccent2))

                    btn_my_bookings.setBackgroundColor(Color.parseColor(data?.colorAccent))
                    btn_create_booking.setBackgroundColor(Color.parseColor(data?.colorAccent))
                    btn_my_profile.setBackgroundColor(Color.parseColor(data?.colorAccent))
                    btn_my_driver.setBackgroundColor(Color.parseColor(data?.colorAccent))

                    PreferenceUtils.setAccent1(Color.parseColor(data?.colorAccent), context!!)
                    PreferenceUtils.setAccent2(Color.parseColor(data?.colorAccent2), context!!)

                    hideLoading()
                }

                override fun failure() {
                    hideLoading()
                }

            }))
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_my_bookings -> {

            }

            R.id.btn_create_booking -> {
                addFragment(NewBookingFragment())
            }

            R.id.btn_my_profile -> {
            }

            R.id.btn_my_driver -> {
                addFragment(DriverProfileFragment())
            }
        }
    }

    private fun showFragment(fragment: Fragment) {
        val fM = childFragmentManager
        val fT = fM.beginTransaction()
        fT.show(fragment).commit()

    }

    private fun addFragment(fragment: Fragment?) {
        if (fragment != null) {
            val manager = activity?.supportFragmentManager
            val ft = manager?.beginTransaction()

            val tag = fragment::class.java.simpleName
            ft?.addToBackStack(tag)
            ft?.add(R.id.frame_layout, fragment, tag)
            ft?.commitAllowingStateLoss()
        }
    }
}