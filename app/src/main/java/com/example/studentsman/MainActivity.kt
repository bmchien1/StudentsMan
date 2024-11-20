package com.example.studentsman

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
  private lateinit var studentAdapter: StudentAdapter
  private lateinit var students: MutableList<StudentModel>

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    students = mutableListOf(
      StudentModel("Nguyễn Văn An", "SV001"),
      StudentModel("Trần Thị Bảo", "SV002"),
      StudentModel("Lê Hoàng Cường", "SV003"),
      StudentModel("Phạm Thị Dung", "SV004"),
      StudentModel("Đỗ Minh Đức", "SV005"),
      StudentModel("Vũ Thị Hoa", "SV006"),
      StudentModel("Hoàng Văn Hải", "SV007"),
      StudentModel("Bùi Thị Hạnh", "SV008"),
      StudentModel("Đinh Văn Hùng", "SV009"),
      StudentModel("Nguyễn Thị Linh", "SV010"),
      StudentModel("Phạm Văn Long", "SV011"),
      StudentModel("Trần Thị Mai", "SV012"),
      StudentModel("Lê Thị Ngọc", "SV013"),
      StudentModel("Vũ Văn Nam", "SV014"),
      StudentModel("Hoàng Thị Phương", "SV015"),
      StudentModel("Đỗ Văn Quân", "SV016"),
      StudentModel("Nguyễn Thị Thu", "SV017"),
      StudentModel("Trần Văn Tài", "SV018"),
      StudentModel("Phạm Thị Tuyết", "SV019"),
      StudentModel("Lê Văn Vũ", "SV020")
    )

    studentAdapter = StudentAdapter(this, students)

    findViewById<RecyclerView>(R.id.recycler_view_students).apply {
      adapter = studentAdapter
      layoutManager = LinearLayoutManager(this@MainActivity)
    }

    findViewById<Button>(R.id.btn_add_new).setOnClickListener {
      showAddStudentDialog(studentAdapter)
    }
  }

  private fun showAddStudentDialog(studentAdapter: StudentAdapter) {
    val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_student, null)
    val nameEditText = dialogView.findViewById<EditText>(R.id.et_name)
    val idEditText = dialogView.findViewById<EditText>(R.id.et_id)

    AlertDialog.Builder(this)
      .setTitle("Thêm sinh viên")
      .setView(dialogView)
      .setPositiveButton("Add") { _, _ ->
        val name = nameEditText.text.toString()
        val id = idEditText.text.toString()
        val newStudent = StudentModel(name, id)
        students.add(newStudent)
        studentAdapter.notifyItemInserted(students.size - 1)
      }
      .setNegativeButton("Cancel", null)
      .show()
  }

  fun showEditStudentDialog(student: StudentModel, position: Int, studentAdapter: StudentAdapter) {
    val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_student, null)
    val nameEditText = dialogView.findViewById<EditText>(R.id.et_name)
    val idEditText = dialogView.findViewById<EditText>(R.id.et_id)

    nameEditText.setText(student.studentName)
    idEditText.setText(student.studentId)

    AlertDialog.Builder(this)
      .setTitle("Edit Student")
      .setView(dialogView)
      .setPositiveButton("Save") { _, _ ->
        student.studentName = nameEditText.text.toString()
        student.studentId = idEditText.text.toString()
        studentAdapter.notifyItemChanged(position)
      }
      .setNegativeButton("Cancel", null)
      .show()
  }

  fun deleteStudent(student: StudentModel, position: Int, studentAdapter: StudentAdapter) {
    val removedStudent = students[position]
    students.removeAt(position)
    studentAdapter.notifyItemRemoved(position)

    Snackbar.make(findViewById(R.id.main), "Student deleted", Snackbar.LENGTH_LONG)
      .setAction("Undo") {
        students.add(position, removedStudent)
        studentAdapter.notifyItemInserted(position)
      }
      .show()
  }
}

