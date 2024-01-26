package com.javabootcamp.fintechbank.accounts;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AccountRequest(
        @NotNull
        String type,
        @NotNull
        String name,
        @NotNull
        Double balance
) {
}
