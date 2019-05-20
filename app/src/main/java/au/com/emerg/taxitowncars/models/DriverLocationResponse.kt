package au.com.emerg.taxitowncars.models

import au.com.emerg.taxitowncars.utils.CDEntityType
import java.time.OffsetDateTime

class DriverLocationResponse {

    var id: Long? = null
    var entityType: CDEntityType? = null
    var entityId: Long? = null
    var latitude: Double? = null
    var longitude: Double? = null
    var heading: Double? = null
    var updatedAt: OffsetDateTime? = null
}