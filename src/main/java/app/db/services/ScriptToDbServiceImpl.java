package app.db.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ScriptToDbServiceImpl implements ScriptToDbService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ScriptToDbServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String getScriptResult(String script) {
        String[] queries = script.split(";");

        StringBuilder queryResult = new StringBuilder();

        for (String query : queries) {
            query = query.trim();
            if (!query.isEmpty()) {
                try {
                    List<Map<String, Object>> result = jdbcTemplate.queryForList(query);

                    if (!result.isEmpty()) {
                        queryResult.append("\nResults for query: ").append(query).append("\n\n");
                        for (Map<String, Object> row : result) {
                            row.forEach((column, value) ->
                                    queryResult.append(column).append(": ").append(value).append(" | "));
                            queryResult.append("\n");
                        }
                    } else {
                        queryResult.append("No results for query: ").append(query).append("\n");
                    }
                } catch (Exception e) {
                    queryResult.append("Error executing query: ").append(query).append("\n");
                    queryResult.append("Exception: ").append(e.getMessage()).append("\n");
                }
            }
        }

        System.out.println(queryResult);
        return queryResult.toString().replace("|", "");
    }
}
