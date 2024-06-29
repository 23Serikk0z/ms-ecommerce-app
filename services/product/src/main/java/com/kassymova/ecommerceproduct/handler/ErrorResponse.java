package com.kassymova.ecommerceproduct.handler;

import java.util.Map;

public record ErrorResponse (
        Map<String, String> errors
) {

}
