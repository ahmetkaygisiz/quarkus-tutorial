package com.akua.repository.manager;

import com.akua.domain.User;

import javax.enterprise.context.ApplicationScoped;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class UserDBManager extends DBManager{

    public List<User> getResultSet(String query) throws SQLException {
        PreparedStatement ps = getPreparedStatement(query);
        ResultSet rs = ps.executeQuery();

        return resultSetToOArrayList(rs);
    }

    public List<User> getResultSetWithParams(String query, Object[] params) throws  SQLException {
        ResultSet rs = getPreparedStatementWithParams(query, params).executeQuery();

        return resultSetToOArrayList(rs);
    }

    public List<User> resultSetToOArrayList(ResultSet rs) throws SQLException {
        List<User> dataList = new ArrayList<>();

        while( rs.next() ){
            User user = new User();

            user.setId((UUID) rs.getObject("id"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));

            dataList.add(user);
        }
        rs.close();

        return dataList;
    }
}
