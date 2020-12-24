package com.akua.repository.manager;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@ApplicationScoped
public class DBManager {

    @Inject
    private DataSource dataSource;

    private Connection conn;

    public void connect() throws SQLException {
        conn = dataSource.getConnection();
    }

    public void close() throws SQLException {
        if ( conn != null)
            conn.close();
    }

    public PreparedStatement getPreparedStatement(String query) throws SQLException {
        return conn.prepareStatement(query);
    }

    public PreparedStatement getPreparedStatementWithParams(String query, Object[] params) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(query);

        for (int i = 0; i< params.length; i++) {
            ps.setObject(i + 1, params[i]);
        }

        return ps;
    }

    public boolean executePS(String query) throws SQLException {
        PreparedStatement ps = getPreparedStatement(query);
        boolean status = ps.execute();
        ps.close();

        return status;
    }

    public boolean executePSWithParams(String query, Object[] params) throws SQLException {
        PreparedStatement ps = getPreparedStatementWithParams(query, params);
        boolean status = ps.execute();
        ps.close();

        return status;
    }
}

