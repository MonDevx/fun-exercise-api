package com.javabootcamp.fintechbank.accounts;

import jakarta.validation.constraints.NotNull;

public record TransferRequest(

        @NotNull
        Double amount,
        @NotNull
        String remark
) {
}
