package com.ute.bainay

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ute.bainay.data.AppDatabase
import com.ute.bainay.repository.StudentRepository
import com.ute.bainay.viewmodel.StudentViewModel
import com.ute.bainay.viewmodel.StudentViewModelFactory

class MainActivity : AppCompatActivity() {
    private val database by lazy { AppDatabase.getDatabase(applicationContext) }
    private val repository by lazy { StudentRepository(database.studentDao()) }
    val viewModel: StudentViewModel by viewModels {
        StudentViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Kích hoạt khởi tạo ViewModel sẵn sàng trong ViewModelStore
        viewModel.allStudents
        setContentView(R.layout.activity_main)
    }
}
