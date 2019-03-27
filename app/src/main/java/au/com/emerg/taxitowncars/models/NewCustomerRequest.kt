package au.com.emerg.taxitowncars.models

import au.com.emerg.taxitowncars.utils.AppCustomerAssociationType
import java.time.LocalDate

class NewCustomerRequest {

    var firstName: String? = null

    var lastName: String? = null

    var email: String? = null

    var phoneNumber: String? = null

    var dateOfBirth: LocalDate? = null

    var password: String? = null

    var associatedCountryStateId: Long? = null

    var ownerAssociationId: Long? = null

    var ownerAssociationType: AppCustomerAssociationType = AppCustomerAssociationType.Driver

}