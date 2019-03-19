package au.com.emerg.taxitowncars.retrofit

interface RetrofitResult<T> {
    fun success(value: T)
    fun failure()
}