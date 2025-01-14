package org.api_simples.repository;

import org.api_simples.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IStudentRepository extends JpaRepository<Student, Integer> {
    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    @Query("SELECT s FROM Student s where s.name is null or s.email is null or s.phone is null")
    List<Student> findAllWithNullFields();
}
