package org.api_simples.repository;

import org.api_simples.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IStudentRepository extends JpaRepository<Student, Integer> {
    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);
}
