package com.raisetech.api.sean.mapper;

import com.raisetech.api.sean.entity.RugbyPlayer;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;
import java.util.Optional;

@Mapper
public interface RugbyPlayerMapper {

    @Insert("INSERT INTO rugby_players (id, name, height, weight, rugby_position) VALUES (#{id}, #{name}, #{height}, #{weight}, #{rugbyPosition})")
    void insertPlayerData(RugbyPlayer playerData);

    //データを重複させずにデータベースに登録する処理(insertRugbyPlayers)で使用
    @Select("SELECT * FROM rugby_players WHERE id = #{id}")
    Optional<RugbyPlayer> findPlayerById(String id);

    @SelectProvider(type = RugbyPlayerSqlProvider.class, method = "referencePlayers")
    List<RugbyPlayer> findPlayersByReference(Integer height, Integer weight, String rugbyPosition);

    @UpdateProvider(type = RugbyPlayerSqlProvider.class, method = "updateRugbyPlayer")
    void updateRugbyPlayer(@Param("id") String id,
                           @Param("name") String name,
                           @Param("height") Integer height,
                           @Param("weight") Integer weight,
                           @Param("rugbyPosition") String rugbyPosition);

    @Delete("DELETE FROM rugby_players WHERE id = #{id}")
    void deleteRugbyPlayer(String id);
}
