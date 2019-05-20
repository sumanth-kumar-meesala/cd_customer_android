package au.com.emerg.taxitowncars.retrofit

import au.com.emerg.taxitowncars.models.BaseResponse
import au.com.emerg.taxitowncars.models.Customer
import au.com.emerg.taxitowncars.models.NewBookingRequest
import au.com.emerg.taxitowncars.models.NewCustomerRequest
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
}