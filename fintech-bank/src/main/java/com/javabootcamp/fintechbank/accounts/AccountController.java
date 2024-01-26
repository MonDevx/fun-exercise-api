package com.javabootcamp.fintechbank.accounts;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/accounts")
@RestController
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Operation(summary = "list all accounts")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "list all accounts",
                    content = {
                            @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = AccountResponse.class)))
                    })
    })
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<AccountResponse> getAccounts() {
        return accountService.getAccounts();
    }

    @Operation(summary = "withdraw from an account")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "withdraw money from specific account",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AccountResponse.class))
                    })
    })
    @RequestMapping(value = "/{accountNo}/deposit", method = RequestMethod.POST)
    public AccountResponse depositAccount(
            @PathVariable(name = "accountNo") Integer accountNo,
            @RequestBody @Valid DepositRequest depositRequest
    ) {
        return accountService.depositAccount(accountNo, depositRequest);
    }
    @Operation(summary = "บักคนซั่วจั่งอ้าย มันเอาเหล้ายาปลาปิ้งเป็นใหญ่")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "withdraw money from specific account",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AccountResponse.class))
                    })
    })
    @RequestMapping(value = "/{accountNo}/withdraw", method = RequestMethod.POST)
    public AccountResponse withDrawAccount(
            @PathVariable(name = "accountNo") Integer accountNo,
            @RequestBody @Valid WithdrawRequest withdrawRequest
    ) {
        return accountService.withDrawAccount(accountNo, withdrawRequest);
    }
    @Operation(summary = "My sugar daddy, หมดใจเลยที่ฟ้าให้พ่อ")
    @RequestMapping(value = "/{accountNo}", method = RequestMethod.GET)
    @ApiResponse(responseCode = "200", description = "รักจริงไม่ได้หลอก แค่อยากจะขอให้พ่อช่วยฟ้าหน่อย",    content = {
            @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AccountResponse.class))
    })
    public AccountResponse findAccountByAccountNo(
            @PathVariable(name = "accountNo") Integer accountNo
    ) {
        return accountService.findAccountByAccountNo(accountNo);
    }
    @Operation(summary = "จนมาเห็นกับตา จนพาใจมาเจ็บ")
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "ฉีกบ่มีหม่องเย็บ หัวใจที่ให้เจ้า",   content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountResponse.class))
            })
    })
    public AccountResponse createAccount(
            @RequestBody @Valid AccountRequest accountRequest
    ) {
            return accountService.createAccount(accountRequest);

    }

    @Operation(summary = "บักคนซั่วจั่งอ้าย มันเอาเหล้ายาปลาปิ้งเป็นใหญ่")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "withdraw money from specific account",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AccountResponse.class))
                    })
    })
    @RequestMapping(value = "/{accountNo}/transfer/{targetAccountNo}", method = RequestMethod.POST)
    public AccountResponse transferAccount(
            @PathVariable(name = "accountNo") Integer accountNo,
            @PathVariable(name = "targetAccountNo") Integer targetAccountNo,
            @RequestBody @Valid TransferRequest transferRequest
    ) {
        return accountService.transferAccount(accountNo,targetAccountNo, transferRequest);
    }
}

