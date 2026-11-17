package com.ute.bainay.network

import com.google.gson.annotations.SerializedName

// 1. Data Transfer Object (DTO)
data class StudentDto(
    @SerializedName("id") val remoteId: String,
    @SerializedName("full_name") val fullName: String,
    @SerializedName("student_code") val studentCode: String,
    @SerializedName("email") val email: String,
    @SerializedName("gpa") val gpa: Double
)
