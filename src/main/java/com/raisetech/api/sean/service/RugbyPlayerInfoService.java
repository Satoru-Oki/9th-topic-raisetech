package com.raisetech.api.sean.service;

import com.raisetech.api.sean.entity.RugbyPlayer;
import com.raisetech.api.sean.mapper.RugbyPlayerMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RugbyPlayerInfoService  {

    private RugbyPlayerMapper rugbyPlayerMapper;

    public RugbyPlayerInfoService(RugbyPlayerMapper rugbyPlayerMapper) {
        this.rugbyPlayerMapper = rugbyPlayerMapper;
    }

    public List<RugbyPlayer> findAll() {
        return rugbyPlayerMapper.findAll();
    }

    public List<RugbyPlayer> findByHeightHigherThan(int height) {
        return  rugbyPlayerMapper.findByHeightHigherThan(height);
    }

}
