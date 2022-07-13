package ru.academicians.myhelper.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.academicians.myhelper.model.*;
import ru.academicians.myhelper.repository.model.Deal;
import ru.academicians.myhelper.service.DefaultDealsService;
import ru.academicians.myhelper.service.DefaultUserService;
import ru.academicians.myhelper.utils.CustomTokenIdCatcher;

import javax.validation.Valid;

import static ru.academicians.myhelper.defaults.DefaultKeys.CAN_T_CREATE_NEW_DEAL;
import static ru.academicians.myhelper.defaults.DefaultKeys.USER_CANT_SUBSCRIBE_ANOTHER_USER_STRING;

@Api(description = "BaseApi")
@RestController
@Validated
@RequestMapping("api")
public class DefaultPostController {

    private final DefaultUserService userService;
    private final DefaultDealsService dealsService;
    private final CustomTokenIdCatcher customTokenIdCatcher;

    @Autowired
    public DefaultPostController(DefaultUserService userService, DefaultDealsService dealsService, CustomTokenIdCatcher customTokenIdCatcher) {
        this.userService = userService;
        this.dealsService = dealsService;
        this.customTokenIdCatcher = customTokenIdCatcher;
    }

    @ApiOperation(value = "An attempt to add user to db")
    @PostMapping(value = "/add/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OperationResultResponse> addUser(@Valid @RequestBody AddPersonRequest request) {

        long id = userService.createNewUser(request);

        return new ResponseEntity<>(new OperationResultResponse("Add", ((Long) id).toString()), HttpStatus.CREATED);
    }

    @ApiOperation(value = "An attempt to add deal to db")
    @PostMapping(value = "/add/deal", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OperationResultResponse> addDeal(
            @Valid @RequestBody AddServiceRequest request,
            @RequestHeader(name="Authorization") String token) {

        if (!customTokenIdCatcher.isIdInTokenEquals(token, request.getOwnerId())){
            throw new IllegalArgumentException(CAN_T_CREATE_NEW_DEAL);
        }

        DetailedUserInfoResponse detailedUserInfoById = userService.getDetailedUserInfoById(request.getOwnerId());

        Long id = dealsService.createNewDeal(request, detailedUserInfoById);

        return new ResponseEntity<>(new OperationResultResponse("Add", id.toString()), HttpStatus.CREATED);
    }

    @ApiOperation(value = "An attempt to subscribe user to deal")
    @PostMapping(value = "/subscribe/deal", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OperationResultResponse> subscribeUser(
            @Valid @RequestBody SubscribeRequest request,
            @RequestHeader(name="Authorization") String token) {
        if (!customTokenIdCatcher.isIdInTokenEquals(token, request.getSubscriberId())){
            throw new IllegalArgumentException(USER_CANT_SUBSCRIBE_ANOTHER_USER_STRING);
        }

        long subscriberId = request.getSubscriberId();
        long dealId = request.getDealId();

        DetailedUserInfoResponse detailedUserInfoById = userService.getDetailedUserInfoById(subscriberId);

        Deal deal = dealsService.findDealById(dealId);

        String result = dealsService.addSubscription(deal, detailedUserInfoById.getId());

        return new ResponseEntity<>(
                new OperationResultResponse(
                        "Subscribe",
                        result),
                HttpStatus.CREATED);
    }
}
