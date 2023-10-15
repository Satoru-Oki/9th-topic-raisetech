package com.raisetech.api.sean.mapper;

import com.raisetech.api.sean.entity.RugbyPlayer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface RugbyPlayerMapper {

    @Select("SELECT * FROM rugbyPlayers")
    List<RugbyPlayer> findAll();

    @Select("<script>" +
            "SELECT * FROM rugbyPlayers " +
            "WHERE 1=1 " +
            "<if test='height != null'>AND height > #{height}</if> " +
            "<if test='weight != null'>AND weight > #{weight}</if> " +
            "</script>")
    List<RugbyPlayer> findByHeightOrWeight(Integer height, Integer weight);

    @Select("SELECT * FROM rugbyPlayers WHERE posi = #{posi}")
    List<RugbyPlayer> findByPosition(String posi);
}
