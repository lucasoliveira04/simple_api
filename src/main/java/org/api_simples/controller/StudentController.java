package org.api_simples.controller;

import org.api_simples.dto.StudentDto;
import org.api_simples.entity.Student;
import org.api_simples.services.AddUser;
import org.api_simples.services.ListAllUsersServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {
    @Autowired
    private AddUser addUser;
    @Autowired
    private ListAllUsersServices listAllUsersServices;

    @GetMapping("/list")
    public ResponseEntity<List<StudentDto>> getAllUsers(){
        ResponseEntity<List<StudentDto>> users = listAllUsersServices.listAllUsers();
        return ResponseEntity.ok(users.getBody());
    }

    @PostMapping("/")
    public ResponseEntity<?> add(@RequestBody Student student){
        return addUser.addUser(student);
    }
}
