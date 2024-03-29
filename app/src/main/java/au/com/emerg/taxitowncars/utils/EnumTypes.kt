package au.com.emerg.taxitowncars.utils


enum class AppCustomerAssociationType {
    Driver,
    Company
}

enum class LocationType {
    PickUp,
    DropOff
}

enum class BookingOwnerType {
    Company,
    Driver
}

enum class CustomerType {
    Internal,
    App,
    QuickBooking
}

enum class PricingType {
    Distance,
    Hourly
}

enum class BookingPaymentType {
    Cash,
    Card,
    Docket,
    Account,
    CabCharge,
    EftposFivePercent,
    EftposTenPercent
}


enum class ProfileStatus {
    Active,
    Inactive
}

enum class ProfileStatusByCD {
    Active,
    Blocked
}

enum class CustomerStatus {
    Online,
    Offline
}

enum class CDEntityType {
    Driver,
    AppCustomer
}

enum class AppOwnerType {
    Driver,
    Company
}

enum class DriverStatus {
    Online,
    OnJob,
    Offline
}

