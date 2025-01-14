package org.api_simples.services;

import org.api_simples.entity.Student;
import org.api_simples.repository.IStudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeleteUserService {

    private final IStudentRepository studentRepository;

    public DeleteUserService(IStudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public ResponseEntity<?> deleteAllUser(){
        studentRepository.deleteAll();
        return ResponseEntity.ok("All users for deleting with success");
    }

    public ResponseEntity<String> deleteUsersWithNullFields(){
        List<Student> studentsWithNull = studentRepository.findAllWithNullFields();

        if (studentsWithNull.isEmpty()){
            return ResponseEntity.ok("Not found user with field NULL");
        }

        studentRepository.deleteAll(studentsWithNull);

        return ResponseEntity.ok(studentsWithNull.size() + " users with fields NULL they were deleted.");
    }

    public ResponseEntity<String> deleteUserById(Integer id){
        String message;

        if (!studentRepository.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with this ID : " + id);
        }

        studentRepository.deleteById(id);

        return ResponseEntity.ok("User with ID " + id + " he was deleted with success");
    }
}
