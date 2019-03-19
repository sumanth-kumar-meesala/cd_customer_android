package au.com.emerg.taxitowncars.retrofit

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RetrofitCallback<T> : Callback<T> {

    lateinit var retrofitResult: RetrofitResult<T>

    fun RetrofitCallback(retrofitResult: RetrofitResult<T>) {
        this.retrofitResult = retrofitResult
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        retrofitResult.failure()
    }

    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (response.isSuccessful) {
            retrofitResult.success(response.body()!!)
        } else {
            retrofitResult.failure()
        }
    }

}