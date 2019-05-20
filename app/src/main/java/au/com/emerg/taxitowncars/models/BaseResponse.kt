package au.com.emerg.taxitowncars.models

class BaseResponse<T> {
    var data: T? = null
    var message: String? = null
    var success: Boolean? = null
}