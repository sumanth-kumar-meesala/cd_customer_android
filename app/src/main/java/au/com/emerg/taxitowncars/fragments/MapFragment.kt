package au.com.emerg.taxitowncars.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.util.LongSparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import au.com.emerg.taxitowncars.R
import au.com.emerg.taxitowncars.models.BaseResponse
import au.com.emerg.taxitowncars.models.DriverLocationResponse
import au.com.emerg.taxitowncars.retrofit.RetrofitCallback
import au.com.emerg.taxitowncars.retrofit.RetrofitInstance
import au.com.emerg.taxitowncars.retrofit.RetrofitResult
import au.com.emerg.taxitowncars.utils.CustomerStatus
import au.com.emerg.taxitowncars.utils.LatLngInterpolator
import au.com.emerg.taxitowncars.utils.MarkerAnimation
import au.com.emerg.taxitowncars.utils.PreferenceUtils
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions


class MapFragment : Fragment(), OnMapReadyCallback,
    GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener, EasyPermissions.PermissionCallbacks {
    private lateinit var handler: Handler
    private lateinit var retrofitInstance: RetrofitInstance
    private lateinit var markersMap: LongSparseArray<Marker>

    companion object {
        const val RC_LOCATION = 2198
    }

    private lateinit var map: GoogleMap
    private lateinit var googleApiClient: GoogleApiClient

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.fragment_map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        markersMap = LongSparseArray()
        handler = Handler()

        if (PreferenceUtils.getStatus(context!!)) {
            handler.post(runnableCode)
        }
    }

    private val runnableCode = object : Runnable {

        override fun run() {
            if (context != null) {
                if (ActivityCompat.checkSelfPermission(
                        context!!.applicationContext,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) !== PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        context!!.applicationContext,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) !== PackageManager.PERMISSION_GRANTED
                ) {
                    return
                }

                val lm = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                val location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                val customerId = PreferenceUtils.getCustomerId(context!!)


                if (location != null && customerId != 0L) {
                    sendUserLocation(customerId, location)

                    showDriverLocation(customerId)
                }

                handler.postDelayed(this, 4000)
            }

        }
    }

    private fun sendUserLocation(
        customerId: Long,
        location: Location
    ) {

        val longitude = location.longitude
        val latitude = location.latitude
        val heading = location.bearing

        RetrofitInstance.service.locationReport(
            CustomerStatus.Online,
            customerId,
            longitude,
            latitude,
            heading
        ).enqueue(RetrofitCallback(object : RetrofitResult<BaseResponse<Any>> {
            override fun success(value: BaseResponse<Any>) {
            }

            override fun failure() {
            }
        }))
    }

    private fun showDriverLocation(customerId: Long) {
        RetrofitInstance.service.getDriverLocation(
            customerId
        ).enqueue(RetrofitCallback(object : RetrofitResult<BaseResponse<DriverLocationResponse>> {
            override fun success(value: BaseResponse<DriverLocationResponse>) {
                if (value.data != null) {
                    val data = value.data as DriverLocationResponse
                    val latLng = LatLng(data.latitude!!, data.longitude!!)

                    if (markersMap.get(data.id!!) == null) {
                        val markerOptions = MarkerOptions()
                        markerOptions.position(latLng)
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_car))
                        val marker = map.addMarker(markerOptions)
                        markersMap.put(data.id!!, marker)
                    } else {
                        val marker = markersMap.get(data.id!!)
                        MarkerAnimation.animateMarkerToGB(marker, latLng, LatLngInterpolator.Spherical())
                    }
                }
            }

            override fun failure() {
            }
        }))
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap?) {
        map = googleMap!!
        map.mapType = GoogleMap.MAP_TYPE_NORMAL
        map.uiSettings.isCompassEnabled = true
        map.uiSettings.isMyLocationButtonEnabled = true

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            accessLocation()
        } else {
            buildGoogleApiClient()
            map.isMyLocationEnabled = true
            enableGPS()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    @SuppressLint("MissingPermission")
    @AfterPermissionGranted(RC_LOCATION)
    private fun accessLocation() {
        val perms = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)

        if (EasyPermissions.hasPermissions(context as Context, *perms)) {
            buildGoogleApiClient()
            map.isMyLocationEnabled = true
            enableGPS()
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.location_rationale), RC_LOCATION, *perms)
        }
    }

    @Synchronized
    private fun buildGoogleApiClient() {
        googleApiClient = GoogleApiClient.Builder(context as Context)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build()
        googleApiClient.connect()
    }

    override fun onConnected(bundle: Bundle?) {
    }

    override fun onConnectionSuspended(code: Int) {
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
    }

    override fun onPermissionsGranted(requestCode: Int, list: List<String>) {
        accessLocation()
    }

    override fun onPermissionsDenied(requestCode: Int, list: List<String>) {
    }

    fun enableGPS() {
        val lm = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        var gps_enabled = true

        try {
            gps_enabled = lm!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
        } catch (ex: Exception) {
        }

        if (!gps_enabled) {
            AlertDialog.Builder(context as Context)
                .setMessage(resources.getString(R.string.gps_not_enabled))
                .setPositiveButton(
                    resources.getString(R.string.enable)
                ) { _, _ ->
                    val myIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    activity?.startActivity(myIntent)
                }
                .setNegativeButton(getString(R.string.cancel), null)
                .show()
        }
    }

    fun statusUpdated(checked: Boolean) {
        if (checked) {
            handler.post(runnableCode)
        } else {
            handler.removeCallbacks(runnableCode)
        }
    }
}