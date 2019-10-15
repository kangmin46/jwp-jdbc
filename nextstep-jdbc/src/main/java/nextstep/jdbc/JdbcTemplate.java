package nextstep.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JdbcTemplate<T> {
    private static final Logger logger = LoggerFactory.getLogger(JdbcTemplate.class);

    private DataSource dataSource;

    public JdbcTemplate(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void executeSql(String sql, Object... arg) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            for (int i = 0; i < arg.length; i++) {
                preparedStatement.setObject(i + 1, arg[i]);
            }
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            logger.error("{}", e);
        }
    }

    public List<T> queryForList(String sql, RowMapper<T> rowMapper) {
        return (List<T>) abc(sql, rowMapper, new ListStrategy<T>());
    }

    private Object abc(String sql, RowMapper<T> rowMapper, Strategy<T> strategy) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            return strategy.execute(rowMapper,resultSet);

        } catch (SQLException e) {
            logger.error("{}", e);
        }
        return null;
    }
}