package au.com.emerg.taxitowncars.models

import au.com.emerg.taxitowncars.utils.*
import java.math.BigDecimal

class NewBookingRequest {

    var ownerType: BookingOwnerType? = null
    var ownerId: Long? = null

    var customerType: CustomerType? = null
    var customerId: Long? = null

    var pricingType: PricingType? = null

    var onDemand: Boolean = false

    var notes: String? = null

    var notesForDriver: String? = null

    var bookingVehicleTypeId: Long? = null

    var bookingDateTime: String? = null

    var estimatedPrice: BigDecimal? = null

    var paymentType: BookingPaymentType? = null

    var locations: MutableList<BookingLocationRequest>? = null

    var specialRequirements: List<BookingSpecialRequirementRequest>? = null

    // Adds only 1 pickup and 1 drop off location
    fun addBookingLocation(bookingLocation: BookingLocationRequest) {

        if (locations == null) {
            locations = mutableListOf()
        }

        var removingLocationPosition: Int? = null

        if (bookingLocation.locationType == LocationType.PickUp) {
            for (location in locations!!) {
                if (location.locationType == LocationType.PickUp) {
                    removingLocationPosition = locations!!.indexOf(location)
                }
            }

            if (removingLocationPosition == null) {
                locations!!.add(bookingLocation)
            } else {
                locations!!.removeAt(removingLocationPosition)
                locations!!.add(bookingLocation)
            }
        }

        if (bookingLocation.locationType == LocationType.DropOff) {

            for (location in locations!!) {
                if (location.locationType == LocationType.DropOff) {
                    removingLocationPosition = locations!!.indexOf(location)
                }
            }

            if (removingLocationPosition == null) {
                locations!!.add(bookingLocation)
            } else {
                locations!!.removeAt(removingLocationPosition)
                locations!!.add(bookingLocation)
            }
        }

    }

}