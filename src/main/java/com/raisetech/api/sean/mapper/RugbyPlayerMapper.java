package com.raisetech.api.sean.mapper;

import com.raisetech.api.sean.entity.RugbyPlayer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface RugbyPlayerMapper {

    @SelectProvider(type = RugbyPlayerSqlProvider.class, method = "referencePlayers")
    List<RugbyPlayer> findPlayersByReference(Integer height, Integer weight, String rugbyPosition);
}

