package com.raisetech.api.sean.mapper;

import com.raisetech.api.sean.entity.RugbyPlayer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface RugbyPlayerMapper {

    @Select("SELECT * FROM rugbyPlayers")
    List<RugbyPlayer> findAll();

    @Select("SELECT * FROM rugbyPlayers WHERE height > #{height}")
    List<RugbyPlayer> findByHeightHigherThan(int height);
}
