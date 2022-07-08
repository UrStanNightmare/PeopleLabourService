package ru.academicians.myhelper.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.academicians.myhelper.exception.ItemNotFoundException;
import ru.academicians.myhelper.model.AddServiceRequest;
import ru.academicians.myhelper.model.SubscribeRequest;
import ru.academicians.myhelper.repository.model.Deal;
import ru.academicians.myhelper.repository.model.User;
import ru.academicians.myhelper.service.DefaultDealsService;
import ru.academicians.myhelper.service.DefaultUserService;
import ru.academicians.myhelper.model.AddPersonRequest;
import ru.academicians.myhelper.model.OperationResultResponse;

import javax.validation.Valid;

import static ru.academicians.myhelper.defaults.DefaultKeys.*;

@Api(description = "BaseApi")
@RestController
@RequestMapping("api")
public class DefaultPostController {

    private final DefaultUserService userService;
    private final DefaultDealsService dealsService;

    @Autowired
    public DefaultPostController(DefaultUserService userService, DefaultDealsService dealsService) {
        this.userService = userService;
        this.dealsService = dealsService;
    }

    @ApiOperation(value = "An attempt to add user to db")
    @PostMapping("/add/user")
    public ResponseEntity<OperationResultResponse> addUser(@Valid @RequestBody AddPersonRequest request) {

        long id = userService.createNewUser(request);


        return new ResponseEntity<>(new OperationResultResponse("Add", ((Long) id).toString()), HttpStatus.CREATED);
    }

    @ApiOperation(value = "An attempt to add service to db")
    @PostMapping("/add/deal")
    public ResponseEntity<OperationResultResponse> addUser(@Valid @RequestBody AddServiceRequest request) {

        User user = userService.findUserById(request.getOwnerId());

        if (user == null) {
            throw new ItemNotFoundException(USER_NOT_FOUND_STRING);
        }

        long id = dealsService.createNewDeal(request, user);

        return new ResponseEntity<>(new OperationResultResponse("Add", ((Long) id).toString()), HttpStatus.CREATED);
    }

    @ApiOperation(value = "An attempt to subscribe user to deal")
    @PostMapping("/subscribe/deal")
    public ResponseEntity<OperationResultResponse> subscribeUser(@Valid @RequestBody SubscribeRequest request) {

        long subscriberId = request.getSubscriberId();
        long dealId = request.getDealId();

        User user = userService.findUserById(subscriberId);

        if (user == null) {
            throw new ItemNotFoundException(USER_NOT_FOUND_STRING);
        }
        Deal deal = dealsService.findDealById(dealId);

        if (deal == null) {
            throw new ItemNotFoundException(DEAL_NOT_FOUND_STRING);
        }

        if (deal.getOwner() == subscriberId) {
            throw new IllegalArgumentException(USER_CANT_SUBSCRIBE_SELF_STRING);
        }

        String result = dealsService.addSubscription(dealId, subscriberId);

        return new ResponseEntity<>(
                new OperationResultResponse(
                        "Subscribe",
                        result),
                HttpStatus.CREATED);
    }
}
