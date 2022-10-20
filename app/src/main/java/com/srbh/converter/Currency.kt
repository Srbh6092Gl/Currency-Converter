package com.srbh.converter

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class Currency(
    @SerializedName("source")
    var source: String,
    @SerializedName("success")
    var success: Boolean,
    @SerializedName("timestamp")
    var timestamp: Int,
    @SerializedName("quotes")
    var quotes: JsonObject
)

var currency : Currency ?= null