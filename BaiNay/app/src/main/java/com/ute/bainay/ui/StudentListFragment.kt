package com.ute.bainay.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ute.bainay.data.AppDatabase
import com.ute.bainay.databinding.FragmentStudentListBinding
import com.ute.bainay.repository.StudentRepository
import com.ute.bainay.viewmodel.StudentViewModel
import com.ute.bainay.viewmodel.StudentViewModelFactory

class StudentListFragment : Fragment() {
    // Áp dụng chuẩn Backing Property quản lý vòng đời kép
    private var _binding: FragmentStudentListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: StudentViewModel by activityViewModels {
        StudentViewModelFactory(
            StudentRepository(
                AppDatabase.getDatabase(requireContext().applicationContext).studentDao()
            )
        )
    }
    private lateinit var adapter: StudentAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStudentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = StudentAdapter { student -> viewModel.delete(student) }
        binding.rvStudents.layoutManager = LinearLayoutManager(requireContext())
        binding.rvStudents.adapter = adapter

        // Quan sát LiveData với viewLifecycleOwner
        viewModel.allStudents.observe(viewLifecycleOwner) { students ->
            adapter.submitList(students)
            binding.emptyLayout.visibility = if (students.isEmpty()) View.VISIBLE else View.GONE
        }

        binding.fabAdd.setOnClickListener {
            AddStudentDialogFragment().show(childFragmentManager, "ADD_STUDENT")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Bắt buộc giải phóng để chống rò rỉ bộ nhớ
    }
}
