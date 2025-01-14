package org.api_simples.controller;

import org.api_simples.dto.StudentDto;
import org.api_simples.entity.Student;
import org.api_simples.services.AddUser;
import org.api_simples.services.DeleteUserService;
import org.api_simples.services.ListAllUsersServices;
import org.api_simples.services.UpdateUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/student")
public class StudentController {
    private final AddUser addUser;
    private final ListAllUsersServices listAllUsersServices;
    private final UpdateUserService updateUserService;
    private final DeleteUserService deleteUserService;

    public StudentController(AddUser addUser, ListAllUsersServices listAllUsersServices, UpdateUserService updateUserService, DeleteUserService deleteUserService) {
        this.addUser = addUser;
        this.listAllUsersServices = listAllUsersServices;
        this.updateUserService = updateUserService;
        this.deleteUserService = deleteUserService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<StudentDto>> getAllUsers(){
        ResponseEntity<List<StudentDto>> users = listAllUsersServices.listAllUsers();
        return ResponseEntity.ok(users.getBody());
    }

    @PostMapping("/")
    public ResponseEntity<?> add(@RequestBody Student student){
        return addUser.addUser(student);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody Student updatedStudent){
        return updateUserService.updateUser(id, updatedStudent);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patch(@PathVariable Integer id, @RequestBody Map<String, Object> updates){
        return updateUserService.partialUpdateUser(id, updates);
    }

    @DeleteMapping("/all")
    public ResponseEntity<?> deleteAllUser(){
        return deleteUserService.deleteAllUser();
    }

    @DeleteMapping("/null")
    public ResponseEntity<?> deleteAllUserWithNullI(){
        return deleteUserService.deleteUsersWithNullFields();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Integer id){
        return deleteUserService.deleteUserById(id);
    }

}
