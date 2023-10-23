package com.raisetech.api.sean.mapper;

import com.raisetech.api.sean.entity.RugbyPlayer;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Optional;

@Mapper
public interface RugbyPlayerMapper {

    @Insert("INSERT INTO rugbyPlayers (id, name, height, weight, rugbyPosition) VALUES (#{id}, #{name}, #{height}, #{weight}, #{rugbyPosition})")
    void insertPlayerData(RugbyPlayer playerData);

    //データを重複させずにデータベースに登録する処理(insertRugbyPlayers)で使用
    @Select("SELECT * FROM rugbyPlayers WHERE id = #{id}")
    Optional<RugbyPlayer> findPlayerById(String id);

    @SelectProvider(type = RugbyPlayerSqlProvider.class, method = "referencePlayers")
    List<RugbyPlayer> findPlayersByReference(Integer height, Integer weight, String rugbyPosition);
}
