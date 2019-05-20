package au.com.emerg.taxitowncars.models

import au.com.emerg.taxitowncars.utils.AppCustomerAssociationType
import au.com.emerg.taxitowncars.utils.ProfileStatus
import au.com.emerg.taxitowncars.utils.ProfileStatusByCD
import java.util.*

class Customer : Base() {
    var firebaseUid: String? = null

    var firstName: String? = null

    var lastName: String? = null

    var email: String? = null

    var phoneNumber: String? = null

    var dateOfBirth: Date? = null

    var profilePictureUrl: String? = null

    var lastOnlineDateTime: Date? = null

    var profileStatus: ProfileStatus = ProfileStatus.Active

    var profileStatusByCD: ProfileStatusByCD = ProfileStatusByCD.Active

    var associatedCountryStateId: Long? = null

    var associatedCountryState: CountryState? = null

    var ownerAssociationType: AppCustomerAssociationType = AppCustomerAssociationType.Driver

    var ownerAssociationId: Long? = null
}