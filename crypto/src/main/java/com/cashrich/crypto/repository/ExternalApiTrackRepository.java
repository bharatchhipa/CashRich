package com.cashrich.crypto.repository;

import com.cashrich.crypto.entity.ExternalApiTrack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExternalApiTrackRepository extends JpaRepository<ExternalApiTrack,Long> {

}
