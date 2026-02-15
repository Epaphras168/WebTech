package auca.com.question2_student_api.controller;


import auca.com.question2_student_api.model.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private List<Student> studentList = new ArrayList<>();

    public StudentController() {
        // Create 5 sample students
        studentList.add(new Student(1L, "John", "Doe", "john@example.com", "Computer Science", 3.8));
        studentList.add(new Student(2L, "Jane", "Smith", "jane@example.com", "Mathematics", 3.9));
        studentList.add(new Student(3L, "Bob", "Johnson", "bob@example.com", "Computer Science", 3.2));
        studentList.add(new Student(4L, "Alice", "Williams", "alice@example.com", "Physics", 3.5));
        studentList.add(new Student(5L, "Charlie", "Brown", "charlie@example.com", "Computer Science", 3.6));
    }

    // GET /api/students - all students
    @GetMapping
    public List<Student> getAllStudents() {
        return studentList;
    }

    // GET /api/students/{studentId} - by ID
    @GetMapping("/{studentId}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long studentId) {
        Student student = studentList.stream()
                .filter(s -> s.getStudentId().equals(studentId))
                .findFirst()
                .orElse(null);
        if (student != null) {
            return ResponseEntity.ok(student);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // GET /api/students/major/{major}
    @GetMapping("/major/{major}")
    public List<Student> getStudentsByMajor(@PathVariable String major) {
        return studentList.stream()
                .filter(s -> s.getMajor().equalsIgnoreCase(major))
                .collect(Collectors.toList());
    }

    // GET /api/students/filter?gpa={minGpa}
    @GetMapping("/filter")
    public List<Student> filterStudentsByGpa(@RequestParam Double gpa) {
        return studentList.stream()
                .filter(s -> s.getGpa() >= gpa)
                .collect(Collectors.toList());
    }

    // POST /api/students
    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody Student newStudent) {
        // Generate new ID
        Long newId = studentList.stream().mapToLong(Student::getStudentId).max().orElse(0) + 1;
        newStudent.setStudentId(newId);
        studentList.add(newStudent);
        return ResponseEntity.status(HttpStatus.CREATED).body(newStudent);
    }

    // PUT /api/students/{studentId}
    @PutMapping("/{studentId}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long studentId, @RequestBody Student updatedStudent) {
        // Find the student
        for (int i = 0; i < studentList.size(); i++) {
            Student s = studentList.get(i);
            if (s.getStudentId().equals(studentId)) {
                // Update fields (keeping the same ID)
                updatedStudent.setStudentId(studentId);
                studentList.set(i, updatedStudent);
                return ResponseEntity.ok(updatedStudent);
            }
        }
        return ResponseEntity.notFound().build();
    }
}