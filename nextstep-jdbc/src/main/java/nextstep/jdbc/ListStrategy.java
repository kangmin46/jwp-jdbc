package nextstep.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListStrategy<T> implements Strategy {

    @Override
    public Object execute(RowMapper rowMapper, ResultSet resultSet) throws SQLException {
        List<T> lists = new ArrayList<>();
        while (resultSet.next()) {
            T object = (T) rowMapper.mapRow(resultSet);
            lists.add(object);
        }
        return lists;
    }
}
