package com.cashrich.crypto.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "api_req_resp")
public class ApiReqResp extends BaseEntity {
    private String apiUrl;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String request;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String response;
    private String responseStatus;
    private Integer responseCode;
    private Integer responseEntityCode;
    private String apiMethod;
    private String apiType;
    private Long reqCompleteTime;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String ipAddress;
}