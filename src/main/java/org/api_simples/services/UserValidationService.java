package org.api_simples.services;

import lombok.Getter;
import lombok.Setter;
import org.api_simples.entity.Student;
import org.api_simples.repository.IStudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserValidationService {
    private final IStudentRepository studentRepository;

    public UserValidationService(IStudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    private boolean isEmailAlreadyUsed(String email){
        return studentRepository.existsByEmail(email);
    }

    private boolean isPhoneAlreadyUsed(String phone){
        return studentRepository.existsByPhone(phone);
    }

    public ResponseEntity<?> validationUser(String validationType, Student student) {
        if (validationType == null || (!validationType.equals("email") && !validationType.equals("phone"))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tipo de validação inválido.");
        }

        if (validationType.equals("email")) {
            if (isEmailAlreadyUsed(student.getEmail())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(message(validationType));
            }
        }

        if (validationType.equals("phone")) {
            if (isPhoneAlreadyUsed(student.getPhone())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(message(validationType));
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body("Validação bem-sucedida.");
    }


    private String message(String validationType) {
        return String.format("Usuário com este %s já está cadastrado", validationType);
    }

}
