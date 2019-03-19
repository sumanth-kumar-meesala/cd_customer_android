package au.com.emerg.taxitowncars.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    private var retrofit: Retrofit? = null
    private val BASE_URL = ""

    fun getService(): RetrofitService {
        if (retrofit == null) {
            retrofit = retrofit2.Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val service = retrofit!!.create(RetrofitService::class.java)

        return service
    }


}