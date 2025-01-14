package org.api_simples.services;

import org.api_simples.entity.Student;
import org.api_simples.repository.IStudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateUserService {
    private final IStudentRepository studentRepository;

    public UpdateUserService(IStudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public ResponseEntity<?> updateUser(Integer id, Student updateStudent){
        Optional<Student> existingStudent = studentRepository.findById(id);

        if (existingStudent.isPresent()){
            Student student = existingStudent.get();
            student.setName(updateStudent.getName());
            student.setEmail(updateStudent.getEmail());
            student.setPhone(updateStudent.getPhone());
            studentRepository.save(student);
            return ResponseEntity.ok("Usuario atualizado com sucesso");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado!");
        }
    }
}
