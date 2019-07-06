package au.com.emerg.taxitowncars.fragments

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import au.com.emerg.taxitowncars.R
import au.com.emerg.taxitowncars.models.BaseResponse
import au.com.emerg.taxitowncars.models.DriverDetailsResponse
import au.com.emerg.taxitowncars.retrofit.RetrofitCallback
import au.com.emerg.taxitowncars.retrofit.RetrofitInstance
import au.com.emerg.taxitowncars.retrofit.RetrofitResult
import au.com.emerg.taxitowncars.retrofit.RetrofitService
import au.com.emerg.taxitowncars.utils.PreferenceUtils
import com.bumptech.glide.Glide

class DriverProfileFragment : BaseFragment() {

    var driverDetailsResponse: DriverDetailsResponse? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_driver_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val customerId = PreferenceUtils.getCustomerId(context!!)
        showLoading()

        val iv_profile = view.findViewById<ImageView>(R.id.iv_profile)
        val iv_bg = view.findViewById<ImageView>(R.id.iv_bg)

        val tv_first_name = view.findViewById<TextView>(R.id.tv_first_name)
        val tv_last_name = view.findViewById<TextView>(R.id.tv_last_name)
        val tv_phone_number = view.findViewById<TextView>(R.id.tv_phone_number)
        val tv_email = view.findViewById<TextView>(R.id.tv_email)
        val btn_call = view.findViewById<AppCompatButton>(R.id.btn_call)
        val btn_message = view.findViewById<AppCompatButton>(R.id.btn_message)

        btn_call.setTextColor(PreferenceUtils.getAccent2(context!!))
        btn_call.setBackgroundColor(PreferenceUtils.getAccent1(context!!))
        btn_call.setOnClickListener {
            callDriver()
        }

        btn_message.setTextColor(PreferenceUtils.getAccent2(context!!))
        btn_message.setBackgroundColor(PreferenceUtils.getAccent1(context!!))
        btn_message.setOnClickListener {
            smsDriver()
        }

        RetrofitInstance.service.getOwner(customerId)
            .enqueue(RetrofitCallback(object : RetrofitResult<BaseResponse<DriverDetailsResponse>> {
                override fun success(value: BaseResponse<DriverDetailsResponse>) {
                    hideLoading()
                    driverDetailsResponse = value.data
                    Glide.with(context!!).load(driverDetailsResponse?.profilePictureUrl).into(iv_profile)
                    tv_first_name.text = driverDetailsResponse?.firstName
                    tv_last_name.text = driverDetailsResponse?.lastName
                    tv_phone_number.text = driverDetailsResponse?.phoneNumber
                    tv_email.text = driverDetailsResponse?.email
                    Glide.with(context!!).load(driverDetailsResponse?.customerAppMainImageUrl).into(iv_bg)
                }

                override fun failure() {
                    hideLoading()
                    showToast(R.string.error_message)
                }

            }));
    }

    private fun smsDriver() {
        val smsIntent = Intent().apply {
            action = Intent.ACTION_SENDTO
            data = Uri.parse("smsto: ${driverDetailsResponse?.phoneNumber}")
            putExtra("sms_body", "Hi ${driverDetailsResponse?.firstName} ${driverDetailsResponse?.lastName},")
        }
        if (smsIntent.resolveActivity(context?.packageManager!!) != null) {
            startActivity(smsIntent)
        }
    }

    private fun callDriver() {
        val callIntent = Intent().apply {
            action = Intent.ACTION_DIAL
            data = Uri.parse("tel:${driverDetailsResponse?.phoneNumber}")
        }
        startActivity(callIntent)
    }
}