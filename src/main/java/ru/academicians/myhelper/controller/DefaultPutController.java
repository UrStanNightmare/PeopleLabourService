package ru.academicians.myhelper.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;
import ru.academicians.myhelper.model.OperationResultResponse;
import ru.academicians.myhelper.model.UpdateUserDataRequest;
import ru.academicians.myhelper.service.DefaultDealsService;
import ru.academicians.myhelper.service.DefaultUserService;
import ru.academicians.myhelper.utils.CustomTokenIdCatcher;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static ru.academicians.myhelper.defaults.DefaultKeys.CAN_T_UPDATE;

@Api(description = "BaseApi")
@RestController
@RequestMapping("api")
public class DefaultPutController {

    private final DefaultUserService userService;
    private final DefaultDealsService dealsService;
    private final CustomTokenIdCatcher customTokenIdCatcher;

    public DefaultPutController(DefaultUserService userService, DefaultDealsService dealsService, CustomTokenIdCatcher customTokenIdCatcher) {
        this.userService = userService;
        this.dealsService = dealsService;
        this.customTokenIdCatcher = customTokenIdCatcher;
    }

    @ApiOperation(value = "Get user information")
    @PutMapping("/user/{id}/update")
    public ResponseEntity<OperationResultResponse> updateUserInfo(
            OAuth2Authentication auth, @PathVariable(name = "id")
            @NotNull long id,
            @RequestHeader(name="Authorization") String token,
            @Valid @RequestBody UpdateUserDataRequest request) {

        if (!customTokenIdCatcher.isIdInTokenEquals(token, id)){
            throw new IllegalArgumentException(CAN_T_UPDATE);
        }

        String result = userService.updateUserWithData(id, request);

        return new ResponseEntity<>(
                new OperationResultResponse("Update", "Updated: " + result)
                , HttpStatus.OK);
    }
}
