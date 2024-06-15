package com.cashrich.crypto.repository;

import com.cashrich.crypto.entity.ApiReqResp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiReqRespRespository extends JpaRepository<ApiReqResp, Long> {
}