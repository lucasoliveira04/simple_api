package org.api_simples.services;

import org.api_simples.dto.StudentDto;
import org.api_simples.entity.Student;
import org.api_simples.entity.StudentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListAllUsersServices {

    private final StudentRepository studentRepository;

    public ListAllUsersServices(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public ResponseEntity<List<StudentDto>> listAllUsers(){
        List<Student> students = studentRepository.findAll();
        List<StudentDto> studentDtos = students.stream()
                .sorted((s1, s2) -> s1.getId().compareTo(s2.getId()))
                .map(student -> new StudentDto(
                        student.getId(),
                        student.getName(),
                        student.getEmail(),
                        student.getPhone()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(studentDtos);
    }
}
