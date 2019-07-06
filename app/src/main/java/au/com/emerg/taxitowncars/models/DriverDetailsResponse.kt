package au.com.emerg.taxitowncars.models

import au.com.emerg.taxitowncars.utils.DriverStatus
import au.com.emerg.taxitowncars.utils.ProfileStatus
import java.time.LocalDate
import java.time.OffsetDateTime

class DriverDetailsResponse{
    var id: Long? = null
    var firebaseUid: String? = null
    var firstName: String? = null
    var lastName: String? = null
    var email: String? = null
    var phoneNumber: String? = null
    var dateOfBirth: String? = null
    var profilePictureUrl: String? = null

    var licenseNumber: String? = null
    var licenseExpiryDate: LocalDate? = null
    var commercialLicenseNumber: String? = null
    var commercialLicenseExpiryDate: LocalDate? = null
    var licensePhotoUrl: String? = null
    var commercialLicensePhotoUrl: String? = null

    var driverStatus: DriverStatus? = null
    var lastOnlineDateTime: OffsetDateTime? = null
    var profileStatus: ProfileStatus? = null
    var dispatchedCarBookingPercentage: Int? = null

    var customerAppMainImageUrl: String? = null
    var colorAccent: String? = null
    var colorAccent2: String? = null

    var driverPricePackages: List<DriverPricePackageResponse>? = null
}