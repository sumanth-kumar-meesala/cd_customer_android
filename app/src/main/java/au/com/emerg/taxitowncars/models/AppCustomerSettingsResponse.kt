package au.com.emerg.taxitowncars.models

import au.com.emerg.taxitowncars.utils.AppOwnerType

class AppCustomerSettingsResponse {
    var appOwnerType: AppOwnerType? = null
    var appOwnerId: Long? = null
    var customLogoUrl: String? = null
    var colorAccent: String? = null
    var colorAccent2: String? = null

    var settingsAvailable = false
}