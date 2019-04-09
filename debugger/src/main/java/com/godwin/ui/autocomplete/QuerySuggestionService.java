package com.godwin.ui.autocomplete;


import com.godwin.model.DDatabase;
import com.godwin.model.DTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Godwin on 5/18/2018 4:11 PM for plugin.
 *
 * @author : Godwin Joseph Kurinjikattu
 */
public class QuerySuggestionService implements AutoCompletionService {
    private static String[] getBasicQueryCommands() {
        return new String[]{
                //basic command
                "SELECT ",
                "SELECT * ",
                "UPDATE ",
                "DELETE ",
                "INSERT INTO ",
                "CREATE DATABASE ",
                "CREATE TABLE ",
                "ALTER TABLE ",
                "DROP TABLE ",
                "DROP DATABASE ",
                "CREATE INDEX ",
                "DROP INDEX ",
                //support
                "FROM ",
                "WHERE ",
                "SELECT DISTINCT ",
                "DISTINCT ",
                "SELECT COUNT() ",
                "COUNT() ",
                "AS ",
                "AND ",
                "OR ",
                "NOT ",
                "ORDER BY ",
                "ASC ",
                "DESC ",
                "VALUES ",
                "IS ",
                "NULL ",
                "NOT NULL ",
                "SET ",
                "LIMIT ",
                "OFFSET ",
                "SELECT MIN() ",
                "MIN() ",
                "SELECT MAX() ",
                "MAX() ",
                "AVG() ",
                "SUM() ",
                "LIKE ",
                "IN ",
                "BETWEEN ",
                "NOT BETWEEN ",
                "JOIN ",
                "INNER JOIN ",
                "LEFT JOIN ",
                "RIGHT JOIN ",
                "FULL JOIN ",
                "ON ",
                "UNION ",
                "UNION ALL ",
                "GROUP BY ",
                "HAVING ",
                "EXISTS ",
                "ANY ",
                "ALL ",
                "INTO ",
                "UNIQUE ",
                "PRIMARY KEY ",
                "FOREIGN KEY ",
                "CHECK ",
                "DEFAULT ",
        };
    }

    public static List<String> getBasicQueryCommandsAsList() {
        return Arrays.asList(getBasicQueryCommands());
    }

    public static List<String> getDbTokensAsList(DDatabase database) {
        List<String> list = new ArrayList<>(Arrays.asList(getBasicQueryCommands()));

        if (null != database) {
            list.add(database.getName());
            if (database.getTables() != null) {
                for (DTable table : database.getTables()) {
                    list.add(table.getName()+" ");
                }
            }
        }
        return list;
    }

    @Override
    public Object autoComplete(String startsWith) {
        String hit = null;
        for (String o : getBasicQueryCommands()) {
            if (o.startsWith(startsWith)) {
                // CompletionService contract states that we only
                // should return completion for unique hits.
                if (hit == null) {
                    hit = o;
                } else {
                    hit = null;
                    break;
                }
            }
        }
        return hit;
    }
}
