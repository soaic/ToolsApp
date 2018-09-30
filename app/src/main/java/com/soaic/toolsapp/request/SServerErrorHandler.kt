package com.soaic.toolsapp.request

import com.soaic.libcommon.network.interceptor.ServerErrorInterceptor
import com.soaic.toolsapp.response.BaseResponse

class SServerErrorHandler : ServerErrorInterceptor{

    private lateinit var serverError: Throwable

    override fun getServerError(): Throwable {
        return serverError
    }

    override fun <T : Any?> isServerError(t: T): Boolean {
        if(t is BaseResponse){
            if(t.code == 500){
                serverError = Exception()
                return true
            } else if (t.code == 404){
                serverError = Exception()
                return true
            }
        }
        return false
    }
}