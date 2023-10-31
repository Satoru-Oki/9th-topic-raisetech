package com.raisetech.api.sean.mapper;

import org.apache.ibatis.jdbc.SQL;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RugbyPlayerSqlProvider {

    public String referencePlayers(Map<String, Object> params) {
        return new SQL() {{
            SELECT("*");
            FROM("rugbyPlayers");
            if (params.get("height") != null) {
                WHERE("height > #{height}");
            }
            if (params.get("weight") != null) {
                WHERE("weight > #{weight}");
            }
            if (params.get("rugbyPosition") != null) {
                WHERE("rugbyPosition = #{rugbyPosition}");
            }
        }}.toString();
    }

    public String updateRugbyPlayer(Map<String, Object> params) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE rugbyPlayers SET ");

        List<String> updates = new ArrayList<>();
        if (params.get("name") != null) updates.add("name = #{name}");
        if (params.get("height") != null) updates.add("height = #{height}");
        if (params.get("weight") != null) updates.add("weight = #{weight}");
        if (params.get("rugbyPosition") != null) updates.add("rugbyPosition = #{rugbyPosition}");

        sql.append(String.join(", ", updates));
        sql.append(" WHERE id = #{id}");

        return sql.toString();
    }
}
