package ru.academicians.myhelper.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.academicians.myhelper.exception.ItemNotFoundException;
import ru.academicians.myhelper.model.*;
import ru.academicians.myhelper.repository.model.Deal;
import ru.academicians.myhelper.repository.model.User;
import ru.academicians.myhelper.service.DefaultDealsService;
import ru.academicians.myhelper.service.DefaultUserService;

import javax.validation.Valid;

import static ru.academicians.myhelper.defaults.DefaultKeys.*;

@Api(description = "BaseApi")
@RestController
@Validated
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
    @PostMapping(value = "/add/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OperationResultResponse> addUser(@Valid @RequestBody AddPersonRequest request) {

        long id = userService.createNewUser(request);


        return new ResponseEntity<>(new OperationResultResponse("Add", ((Long) id).toString()), HttpStatus.CREATED);
    }

    @ApiOperation(value = "An attempt to add service to db")
    @PostMapping(value = "/add/deal", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OperationResultResponse> addUser(@Valid @RequestBody AddServiceRequest request) {

        User user = userService.findUserById(request.getOwnerId());

        if (user == null) {
            throw new ItemNotFoundException(USER_NOT_FOUND_STRING);
        }

        long id = dealsService.createNewDeal(request, user);

        return new ResponseEntity<>(new OperationResultResponse("Add", ((Long) id).toString()), HttpStatus.CREATED);
    }

    @ApiOperation(value = "An attempt to subscribe user to deal")
    @PostMapping(value = "/subscribe/deal", produces = MediaType.APPLICATION_JSON_VALUE)
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

        if (dealsService.isSubscriptionExists(dealId, subscriberId)) {
            throw new IllegalArgumentException(USER_ALREADY_SUBSCRIBED_STRING);
        }

        String result = dealsService.addSubscription(dealId, subscriberId);

        return new ResponseEntity<>(
                new OperationResultResponse(
                        "Subscribe",
                        result),
                HttpStatus.CREATED);
    }

    @ApiOperation(value = "An attempt to log in")
    @PostMapping(value = "/public/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> tryToAuthUser(@RequestBody @Valid AuthorizationRequest request) {
       return new ResponseEntity<>("Login", HttpStatus.OK);

    }
}
