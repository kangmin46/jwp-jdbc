package nextstep.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Strategy<T> {
    Object execute(RowMapper<T> rowMapper, ResultSet resultSet) throws SQLException;
}
