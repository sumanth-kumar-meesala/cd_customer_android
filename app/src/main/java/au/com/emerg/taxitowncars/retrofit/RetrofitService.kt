package au.com.emerg.taxitowncars.retrofit

import au.com.emerg.taxitowncars.models.*
import au.com.emerg.taxitowncars.utils.CustomerStatus
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RetrofitService {
    @POST("/appCustomer/signUp")
    fun signUp(@Body body: NewCustomerRequest): Call<BaseResponse<Any>>

    @POST("/appCustomer/addBooking")
    fun addBooking(
        @Body newBookingRequest: NewBookingRequest
    ): Call<BaseResponse<Long>>

    @GET("/appCustomer/getByFirebaseUid")
    fun getUserInfo(
        @Query("uid") uid: String
    ): Call<BaseResponse<Customer>>

    @GET("/appCustomer/locationReport")
    fun locationReport(
        @Query("appCustomerStatus") appCustomerStatus: CustomerStatus,
        @Query("appCustomerId") appCustomerId: Long,
        @Query("longitude") longitude: Double,
        @Query("latitude") latitude: Double,
        @Query("heading") heading: Float
    ): Call<BaseResponse<Any>>

    @GET("/appCustomer/getDriverLocation")
    fun getDriverLocation(
        @Query("appCustomerId") appCustomerId: Long
    ): Call<BaseResponse<DriverLocationResponse>>
}