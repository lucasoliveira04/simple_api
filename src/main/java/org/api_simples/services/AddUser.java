package org.api_simples.services;

import org.api_simples.dto.StudentDto;
import org.api_simples.entity.Student;
import org.api_simples.repository.IStudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AddUser {
    private final IStudentRepository studentRepository;
    private final UserValidationService userValidationService;

    public AddUser(IStudentRepository studentRepository, UserValidationService userValidationService) {
        this.studentRepository = studentRepository;
        this.userValidationService = userValidationService;
    }
    public ResponseEntity<?> addUser(Student student){
        ResponseEntity<?> validationResponse = validateUser(student);
        if (validationResponse != null) {
            return validationResponse;
        }

        studentRepository.save(student);

        StudentDto studentDto = new StudentDto(
                student.getId(),
                student.getName(),
                student.getEmail(),
                student.getPhone()
        );


        return ResponseEntity.status(HttpStatus.OK).body(studentDto);
    }

    private ResponseEntity<?> validateUser(Student student){
        ResponseEntity<?> emailValidationResponse = userValidationService.validationUser("email", student);
        if (emailValidationResponse.getStatusCode() == HttpStatus.CONFLICT) {
            return emailValidationResponse;
        }

        ResponseEntity<?> phoneValidationResponse = userValidationService.validationUser("phone", student);
        if (phoneValidationResponse.getStatusCode() == HttpStatus.CONFLICT) {
            return phoneValidationResponse;
        }

        return null;
    }

}
