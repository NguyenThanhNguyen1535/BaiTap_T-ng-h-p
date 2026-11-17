package com.ute.bainay.network

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

// 2. Interface ApiService
interface StudentApiService {
    @GET("api/v1/students")
    suspend fun getStudents(): List<StudentDto>

    @POST("api/v1/students")
    suspend fun createStudent(@Body studentDto: StudentDto): StudentDto
}
