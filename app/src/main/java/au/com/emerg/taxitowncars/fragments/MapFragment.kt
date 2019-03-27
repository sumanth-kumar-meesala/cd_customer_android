package au.com.emerg.taxitowncars.fragments

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import au.com.emerg.taxitowncars.R
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment


class MapFragment : Fragment(), OnMapReadyCallback,
    GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener {

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
            if (ContextCompat.checkSelfPermission(
                    context!!,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) === PackageManager.PERMISSION_GRANTED
            ) {
                buildGoogleApiClient()
                map.isMyLocationEnabled = true
            }
        } else {
            buildGoogleApiClient()
            map.isMyLocationEnabled = true
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
}