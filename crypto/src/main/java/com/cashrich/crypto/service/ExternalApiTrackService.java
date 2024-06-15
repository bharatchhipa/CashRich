package com.cashrich.crypto.service;

import com.cashrich.crypto.entity.ExternalApiTrack;
import com.cashrich.crypto.repository.ExternalApiTrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExternalApiTrackService {

    @Autowired
    private ExternalApiTrackRepository externalApiTrackRepository;

    public void save(ExternalApiTrack externalApiTrack){
        externalApiTrackRepository.save(externalApiTrack);
    }



}
