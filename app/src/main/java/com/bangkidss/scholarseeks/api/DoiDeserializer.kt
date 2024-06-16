package com.bangkidss.scholarseeks.api

import android.util.Log
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class DoiDeserializer : JsonDeserializer<String> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): String {
        return if (json != null && json.isJsonPrimitive && json.asJsonPrimitive.isString) {
            val value = json.asString
            Log.d("DoiDeserializer", "DOI value: $value")
            if (value == "NaN") "" else value
        } else {
            ""
        }
    }
}