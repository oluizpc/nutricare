package com.nutri.nutricare.shared.exception;

import java.util.Map;

public record ErrorResponse(
        int status,
        String mensagem,
        Map<String, String> campos
) {
    public ErrorResponse(int status, String mensagem) {
        this(status, mensagem, null);
    }
}
