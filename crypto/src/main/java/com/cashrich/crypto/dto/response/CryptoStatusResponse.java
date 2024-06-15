package com.cashrich.crypto.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CryptoStatusResponse {
    private String timestamp;
    private String error_code;
    private String error_message;
    private String elapsed;
    private String credit_count;
    private String notice;
}
