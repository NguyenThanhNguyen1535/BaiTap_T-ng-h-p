package com.ute.bainay.repository

import com.ute.bainay.data.StudentDao
import com.ute.bainay.model.Student
import com.ute.bainay.network.RetrofitClient
import kotlinx.coroutines.flow.Flow
import java.io.IOException

class StudentRepository(private val studentDao: StudentDao) {
    // Nguồn chân lý duy nhất (Single Source of Truth)
    val allStudents: Flow<List<Student>> = studentDao.getAllStudents()

    suspend fun insert(student: Student) {
        studentDao.insertStudent(student)
    }

    suspend fun delete(student: Student) {
        studentDao.deleteStudent(student)
    }

    suspend fun update(student: Student) {
        studentDao.updateStudent(student)
    }

    suspend fun getStudentByCode(code: String): Student? {
        return studentDao.getStudentByCode(code)
    }

    // Single Source of Truth (SSOT) & Offline-First:
    // Đọc từ Retrofit và nạp vào Room Database cục bộ
    suspend fun refreshFromRemote(): Result<Unit> {
        return try {
            val remoteStudents = RetrofitClient.apiService.getStudents()
            remoteStudents.forEach { dto ->
                studentDao.insertStudent(
                    Student(
                        fullName = dto.fullName,
                        studentCode = dto.studentCode,
                        email = dto.email,
                        gpa = dto.gpa
                    )
                )
            }
            Result.success(Unit)
        } catch (e: IOException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
