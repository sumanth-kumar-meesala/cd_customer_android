package au.com.emerg.taxitowncars.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatSpinner
import au.com.emerg.taxitowncars.R
import au.com.emerg.taxitowncars.listeners.IDateTimeListener
import au.com.emerg.taxitowncars.models.BaseResponse
import au.com.emerg.taxitowncars.models.BookingLocationRequest
import au.com.emerg.taxitowncars.models.NewBookingRequest
import au.com.emerg.taxitowncars.retrofit.RetrofitCallback
import au.com.emerg.taxitowncars.retrofit.RetrofitInstance
import au.com.emerg.taxitowncars.retrofit.RetrofitResult
import au.com.emerg.taxitowncars.utils.*
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.ui.PlacePicker
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.joda.time.LocalDateTime
import java.math.BigDecimal

class NewBookingFragment : BaseFragment(), IDateTimeListener {

    companion object {
        val PICKUP_PLACE_PICKER_REQUEST = 4535
        val DROPOFF_PLACE_PICKER_REQUEST = 4643
    }

    lateinit var til_pickup_address: TextInputLayout
    lateinit var til_drop_off_address: TextInputLayout
    lateinit var til_date_time: TextInputLayout
    lateinit var til_price: TextInputLayout
    lateinit var et_pickup_address: TextInputEditText
    lateinit var et_drop_off_address: TextInputEditText
    lateinit var et_date_time: TextInputEditText
    lateinit var et_price: TextInputEditText
    lateinit var et_notes: TextInputEditText
    lateinit var spinner_payment: AppCompatSpinner
    lateinit var spinner_car_type: AppCompatSpinner

    var newBookingRequest = NewBookingRequest()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_new_booking, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        til_pickup_address = view.findViewById(R.id.til_pickup_address)
        til_drop_off_address = view.findViewById(R.id.til_drop_off_address)
        til_date_time = view.findViewById(R.id.til_date_time)
        til_price = view.findViewById(R.id.til_price)

        et_pickup_address = view.findViewById(R.id.et_pickup_address)
        et_pickup_address.setOnClickListener {
            val builder = PlacePicker.IntentBuilder()
            startActivityForResult(builder.build(activity), PICKUP_PLACE_PICKER_REQUEST)
        }

        et_drop_off_address = view.findViewById(R.id.et_drop_off_address)
        et_drop_off_address.setOnClickListener {
            val builder = PlacePicker.IntentBuilder()
            startActivityForResult(builder.build(activity), DROPOFF_PLACE_PICKER_REQUEST)
        }

        et_date_time = view.findViewById(R.id.et_date_time)

        et_date_time.setOnClickListener {
            DateTimeUtil.selectDate(context as Context, this)
        }

        et_price = view.findViewById(R.id.et_price)
        et_notes = view.findViewById(R.id.et_notes)
        spinner_payment = view.findViewById(R.id.spinner_payment)
        spinner_car_type = view.findViewById(R.id.spinner_car_type)


        view.findViewById<MaterialButton>(R.id.btn_create).setOnClickListener {
            createNewBooking()
        }
    }

    private fun createNewBooking() {
        val pickUpAddress = et_pickup_address.text.toString().trim()
        val dropOffAddress = et_drop_off_address.text.toString().trim()
        val dateTime = et_date_time.text.toString().trim()
        val price = et_price.text.toString().trim()
        val notes = et_notes.text.toString().trim()

        til_pickup_address.error = null
        til_drop_off_address.error = null
        til_date_time.error = null

        if (pickUpAddress.isEmpty()) {
            til_pickup_address.error = getString(R.string.select_pickup_address)
        } else if (dropOffAddress.isEmpty()) {
            til_drop_off_address.error = getString(R.string.select_drop_off_address)
        } else if (dateTime.isEmpty()) {
            til_date_time.error = getString(R.string.select_date_time)
        } else {
            newBookingRequest.notes = notes

            val paymentTypes = arrayListOf(
                BookingPaymentType.Card,
                BookingPaymentType.Cash,
                BookingPaymentType.Docket,
                BookingPaymentType.Account,
                BookingPaymentType.CabCharge,
                BookingPaymentType.EftposFivePercent,
                BookingPaymentType.EftposTenPercent
            )
            newBookingRequest.paymentType = paymentTypes[spinner_payment.selectedItemId.toInt()]
            newBookingRequest.estimatedPrice = BigDecimal(100)

            newBookingRequest.ownerType = BookingOwnerType.Driver
            newBookingRequest.ownerId = 16

            newBookingRequest.pricingType = PricingType.Distance

            if (newBookingRequest.customerId == null) {
                newBookingRequest.customerType = CustomerType.QuickBooking
            }

            val vehicleIds = longArrayOf(7, 8, 19, 31, 20, 28)
            newBookingRequest.bookingVehicleTypeId = vehicleIds.get(spinner_car_type.selectedItemId.toInt())
            newBookingRequest.customerId = PreferenceUtils.getCustomerId(context!!)

            showLoading()

            RetrofitInstance.service.addBooking(newBookingRequest).enqueue(
                RetrofitCallback(object : RetrofitResult<BaseResponse<Long>> {

                    override fun success(value: BaseResponse<Long>) {

                        hideLoading()
                        showToast(R.string.booking_created_successfully)
                        et_pickup_address.text = null
                        et_drop_off_address.text = null
                        et_date_time.text = null
                        spinner_payment.setSelection(0)
                        spinner_car_type.setSelection(0)
                        et_price.text = null
                        et_notes.text = null
                    }

                    override fun failure() {
                        hideLoading()
                        showToast(R.string.error_message)
                    }
                })
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK) return

        if (requestCode == PICKUP_PLACE_PICKER_REQUEST) {
            val place = PlacePicker.getPlace(context, data)
            if (place.address != null && !place.address!!.isEmpty()) {
                et_pickup_address.setText("(${place.name}) - ${place.address}", TextView.BufferType.NORMAL)

                val pickUpLocation = BookingLocationRequest().apply {
                    this.locationType = LocationType.PickUp
                    this.name = place.name.toString()
                    this.fullAddress = place.address.toString()
                    this.latitude = place.latLng.latitude
                    this.longitude = place.latLng.longitude
                    this.atAirport = place.placeTypes.contains(Place.TYPE_AIRPORT)
                    this.locationPriority = 1
                }

                newBookingRequest.addBookingLocation(pickUpLocation)
            } else {
                val builder = AlertDialog.Builder(context as Context)
                builder.setMessage(getString(R.string.select_valid_location))
                builder.setNegativeButton(R.string.ok) { dialog, which ->
                    dialog.dismiss()
                }
                builder.create().show()
            }
        }

        if (requestCode == DROPOFF_PLACE_PICKER_REQUEST) {

            val place = PlacePicker.getPlace(context, data)
            if (place.address != null && !place.address!!.isEmpty()) {
                et_drop_off_address.setText("(${place.name}) - ${place.address}", TextView.BufferType.NORMAL)

                val dropOffLocation = BookingLocationRequest().apply {
                    this.locationType = LocationType.DropOff
                    this.name = place.name.toString()
                    this.fullAddress = place.address.toString()
                    this.latitude = place.latLng.latitude
                    this.longitude = place.latLng.longitude
                    this.atAirport = place.placeTypes.contains(Place.TYPE_AIRPORT)
                    this.locationPriority = 1
                }

                newBookingRequest.addBookingLocation(dropOffLocation)
            } else {
                val builder = AlertDialog.Builder(context as Context)
                builder.setMessage(getString(R.string.select_valid_location))
                builder.setNegativeButton(R.string.ok) { dialog, which ->
                    dialog.dismiss()
                }
                builder.create().show()
            }
        }
    }

    override fun onDateTimeSet(dateTime: LocalDateTime) {
        newBookingRequest.bookingDateTime = dateTime.toString()
        val formattedDateTime = DateTimeUtil.formatDateToString(dateTime)
        et_date_time.setText(formattedDateTime)
    }
}