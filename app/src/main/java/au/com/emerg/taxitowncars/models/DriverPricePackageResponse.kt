package au.com.emerg.taxitowncars.models

import java.math.BigDecimal
import java.time.OffsetDateTime

class DriverPricePackageResponse {
    var id: Long? = null

    var vehicleType: VehicleTypeResponse? = null

    var driverId: Long? = null

    var rateOnePricePerKm: BigDecimal? = null

    var rateTwoStartsAtKm: BigDecimal? = null

    var rateTwoPricePerKm: BigDecimal? = null

    var pricePerMinute: BigDecimal? = null

    var baseCost: BigDecimal? = null

    var minimumPrice: BigDecimal? = null

    var createdAt: OffsetDateTime? = null

    var updatedAt: OffsetDateTime? = null
}