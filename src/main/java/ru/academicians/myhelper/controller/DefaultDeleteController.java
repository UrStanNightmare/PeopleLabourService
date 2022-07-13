package ru.academicians.myhelper.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.academicians.myhelper.model.DetailedUserInfoResponse;
import ru.academicians.myhelper.model.OperationResultResponse;
import ru.academicians.myhelper.model.SubscribeRequest;
import ru.academicians.myhelper.service.DefaultDealsService;
import ru.academicians.myhelper.service.DefaultUserService;
import ru.academicians.myhelper.utils.CustomTokenIdCatcher;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static ru.academicians.myhelper.defaults.DefaultKeys.CAN_T_DELETE_DEAL;

@Api(description = "BaseApi")
@RestController
@RequestMapping("api")
public class DefaultDeleteController {
    private final DefaultUserService userService;
    private final DefaultDealsService dealsService;
    private final CustomTokenIdCatcher customTokenIdCatcher;

    @Autowired
    public DefaultDeleteController(DefaultUserService userService, DefaultDealsService defaultDealsService, CustomTokenIdCatcher customTokenIdCatcher) {
        this.userService = userService;
        this.dealsService = defaultDealsService;
        this.customTokenIdCatcher = customTokenIdCatcher;
    }

    @ApiOperation(value = "An attempt to delete user")
    @DeleteMapping("/user/{id}/delete")
    public ResponseEntity<OperationResultResponse> deleteUser(
            @PathVariable(name = "id") @NotNull long id,
            @RequestHeader(name="Authorization") String token) {

        if (!customTokenIdCatcher.isIdInTokenEquals(token, id)){
            throw new IllegalArgumentException(CAN_T_DELETE_DEAL);
        }

        int deleted = userService.deleteUserAndSubscriptionData(id);

        return new ResponseEntity<>(
                new OperationResultResponse("Delete", "Deleted " + deleted + " el-s")
                , HttpStatus.OK);
    }

    @ApiOperation(value = "An attempt to delete deal")
    @DeleteMapping("/deal/{id}/delete")
    public ResponseEntity<OperationResultResponse> deleteDeal(
            @PathVariable(name = "id") @NotNull long id,
            @RequestHeader(name="Authorization") String token) {

        long idFromToken = customTokenIdCatcher.getIdFromToken(token);

        int deleted = dealsService.deleteDealCascade(id, idFromToken);

        return new ResponseEntity<>(
                new OperationResultResponse("Delete", "Deleted " + deleted + " el-s")
                , HttpStatus.OK);
    }

    @ApiOperation(value = "An attempt to delete deal")
    @DeleteMapping("/unsubscribe/deal")
    public ResponseEntity<OperationResultResponse> deleteDealSubscription(
            @Valid @RequestBody SubscribeRequest request,
            @RequestHeader(name="Authorization") String token) {

        if (!customTokenIdCatcher.isIdInTokenEquals(token, request.getSubscriberId())){
            throw new IllegalArgumentException(CAN_T_DELETE_DEAL);
        }

        DetailedUserInfoResponse user = userService.getDetailedUserInfoById(request.getSubscriberId());

        String result = dealsService.unsubscribeUser(request.getDealId(), user);

        return new ResponseEntity<>(
                new OperationResultResponse("Unsubscribe", result)
                , HttpStatus.OK);
    }
}
