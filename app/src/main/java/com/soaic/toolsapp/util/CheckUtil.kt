package com.soaic.toolsapp.util

import android.content.Context
import com.soaic.libcommon.utils.AppUtils
import org.json.JSONArray

class CheckUtil {

    fun checkCompany(context: Context, companyName: String): String{
        val fit = AppUtils.getAssetsToString(context, "FIT.json")
        val jsonArray = JSONArray(fit)
        var cmps = ""
        for (i in 0..(jsonArray.length()-1)){
            val cmp = jsonArray.getString(i)
            if(cmp.indexOf(companyName) >= 0){
                cmps +=  cmp + "\n"
            }
        }
        return cmps
    }
}