package com.cashrich.crypto.entity;

import com.cashrich.crypto.enums.ExternalServiceType;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "external_api_track")
public class ExternalApiTrack extends BaseEntity {
    private String apiUrl;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String request;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String response;
    private String responseStatus;
    @Enumerated(EnumType.STRING)
    private ExternalServiceType serviceName;
    private Long reqCompleteTime;
    private String ipAddress;
    private String platform;
    private String os;
}
