package ru.academicians.myhelper.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;
import ru.academicians.myhelper.model.OperationResultResponse;
import ru.academicians.myhelper.service.DefaultDealsService;
import ru.academicians.myhelper.service.DefaultUserService;
import ru.academicians.myhelper.utils.CustomTokenIdCatcher;

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
            OAuth2Authentication auth,
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
            OAuth2Authentication auth,
            @PathVariable(name = "id") @NotNull long id,
            @RequestHeader(name="Authorization") String token) {

        long idFromToken = customTokenIdCatcher.getIdFromToken(token);

        int deleted = dealsService.deleteDealCascade(id, idFromToken);

        return new ResponseEntity<>(
                new OperationResultResponse("Delete", "Deleted " + deleted + " el-s")
                , HttpStatus.OK);
    }
}
