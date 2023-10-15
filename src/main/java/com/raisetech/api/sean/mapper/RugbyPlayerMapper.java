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
            "<if test='posi != null'>AND posi = #{posi}</if> " +
            "</script>")
    List<RugbyPlayer> findByReference(Integer height, Integer weight, String posi);
}
