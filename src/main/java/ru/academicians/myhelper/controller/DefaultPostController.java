package ru.academicians.myhelper.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.academicians.myhelper.service.DefaultUserService;
import ru.academicians.myhelper.model.AddPersonRequest;
import ru.academicians.myhelper.model.OperationResultResponse;

import javax.validation.Valid;

@Api(description = "BaseApi")
@RestController
@RequestMapping("api")
public class DefaultPostController {

    private final DefaultUserService userService;

    @Autowired
    public DefaultPostController(DefaultUserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "Test availability")
    @GetMapping("/test")
    public ResponseEntity<String> test(){

        return new ResponseEntity<>("Greetings from back)", HttpStatus.I_AM_A_TEAPOT);
    }

    @ApiOperation(value = "An attempt to add user to db")
    @PostMapping("/add")
    public ResponseEntity<OperationResultResponse> addUser(@Valid @RequestBody AddPersonRequest request){

        String result = userService.createNewUser(request);

        return new ResponseEntity<>(new OperationResultResponse("Add", result), HttpStatus.CREATED);
    }
}
