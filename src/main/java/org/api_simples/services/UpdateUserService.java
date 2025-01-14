package org.api_simples.services;

import org.api_simples.entity.Student;
import org.api_simples.repository.IStudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Map;
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

    public ResponseEntity<?> partialUpdateUser(Integer id, Map<String, Object> updates) {
        return studentRepository.findById(id).map(existingStudent -> {
            updates.forEach((field, value) -> {
                try {
                    Field declaredField = Student.class.getDeclaredField(field);
                    declaredField.setAccessible(true);
                    declaredField.set(existingStudent, value);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new RuntimeException("Invalid field: " + field, e);
                }
            });

            studentRepository.save(existingStudent);
            return ResponseEntity.ok("Student updated successfully");
        }).orElse(ResponseEntity.status(404).body("Student not found"));
    }
}
