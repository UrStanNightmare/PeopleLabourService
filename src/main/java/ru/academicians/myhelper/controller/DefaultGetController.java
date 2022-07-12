package ru.academicians.myhelper.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.academicians.myhelper.model.AllDealsResponse;
import ru.academicians.myhelper.model.DealInfoResponse;
import ru.academicians.myhelper.model.DetailedUserInfoResponse;
import ru.academicians.myhelper.service.DefaultDealsService;
import ru.academicians.myhelper.service.DefaultUserService;

import javax.validation.constraints.NotNull;

@Validated
@Api(description = "BaseApi")
@RestController
@RequestMapping("api")
public class DefaultGetController {
    private final DefaultUserService userService;
    private final DefaultDealsService dealsService;

    @Autowired
    public DefaultGetController(DefaultUserService userService, DefaultDealsService defaultDealsService) {
        this.userService = userService;
        this.dealsService = defaultDealsService;
    }

    @ApiOperation(value = "Test availability")
    @GetMapping("/test")
    public ResponseEntity<String> test() {

        return new ResponseEntity<>("Greetings from back)", HttpStatus.I_AM_A_TEAPOT);
    }

    @ApiOperation(value = "Get user information")
    @GetMapping("/user/{id}/info")
    public ResponseEntity<DetailedUserInfoResponse> getUserInfo(@PathVariable(name = "id") @NotNull long id) {

        DetailedUserInfoResponse detailedResponse = userService.getDetailedUserInfoById(id);

        return new ResponseEntity<>(detailedResponse, HttpStatus.OK);
    }

    @ApiOperation(value = "Get deal information")
    @GetMapping("/deal/{id}/info")
    public ResponseEntity<DealInfoResponse> getDealInfo(@PathVariable(name = "id") @NotNull long id) {

        DealInfoResponse dealInformation = dealsService.getDealInformation(id);

        return new ResponseEntity<>(dealInformation, HttpStatus.OK);
    }

    @ApiOperation(value = "Get all deal information")
    @GetMapping("/deal/all")
    public ResponseEntity<AllDealsResponse> getAllDealsInfo() {

        AllDealsResponse allDealsResponse = dealsService.getAllDealsInfo();

        return new ResponseEntity<>(allDealsResponse, HttpStatus.OK);
    }

}
