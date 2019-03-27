package au.com.emerg.taxitowncars.fragments

import android.Manifest
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import au.com.emerg.taxitowncars.R
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions


class MapFragment : Fragment(), OnMapReadyCallback,
    GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener, EasyPermissions.PermissionCallbacks {

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
    }

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
}