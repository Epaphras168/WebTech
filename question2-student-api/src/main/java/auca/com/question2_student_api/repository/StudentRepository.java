package auca.com.question2_student_api.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import auca.com.question2_student_api.model.Student;


@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
