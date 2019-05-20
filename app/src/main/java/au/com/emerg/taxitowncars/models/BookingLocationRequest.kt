package au.com.emerg.taxitowncars.models

import au.com.emerg.taxitowncars.utils.LocationType

class BookingLocationRequest {
    var locationType: LocationType? = null
    var locationPriority: Int? = null

    var name: String? = null
    var fullAddress: String? = null

    var suburb: String? = null

    var latitude: Double? = null
    var longitude: Double? = null

    var placeTypes: String? = null

    var atAirport: Boolean? = null

    var flightNumber: String? = null


    fun suburbOrName(): String? {
        return suburb ?: name
    }
}