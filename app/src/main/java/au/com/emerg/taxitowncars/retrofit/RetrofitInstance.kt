package au.com.emerg.taxitowncars.retrofit

import au.com.emerg.taxitowncars.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    lateinit var service: RetrofitService private set
    lateinit var okHttpClient: OkHttpClient private set
    lateinit var retrofit: Retrofit private set
    lateinit var tokenHttpInterceptor: TokenHttpInterceptor private set


    @Synchronized
    fun initialize() {
        tokenHttpInterceptor = TokenHttpInterceptor()

        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.addInterceptor(tokenHttpInterceptor)
        okHttpClient = httpClientBuilder.build()

        retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(RetrofitService::class.java)
    }

    @Synchronized
    fun setToken(token: String?) {
        tokenHttpInterceptor.token = token
    }
}