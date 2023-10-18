package com.raisetech.api.sean.mapper;

import org.apache.ibatis.jdbc.SQL;

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
}
