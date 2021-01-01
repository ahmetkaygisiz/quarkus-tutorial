package com.akua.repository;

import com.akua.domain.User;
import com.akua.repository.manager.UserDBManager;

import javax.enterprise.context.ApplicationScoped;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class UserRepository {

    private static final String USERS_FIND_BY_ID = "SELECT * FROM users WHERE id = ?";
    private static final String USERS_FIND_ALL = "SELECT * FROM users";
    private static final String USERS_INSERT = "INSERT INTO users (id, email, password) VALUES(?, ?, ?)";
    private static final String USERS_UPDATE = "UPDATE people SET name = ?, age = ? WHERE id = ?";
    private static final String USERS_DELETE_BY_ID = "DELETE FROM users WHERE id = ?";

    private final UserDBManager manager;

    public UserRepository(UserDBManager manager){
        this.manager = manager;
    }

    public List<User> findAll(){
        try {
            return getQueryResults(USERS_FIND_ALL);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }


    public List<User> findById(UUID id) {
        try {
            return getUserById(USERS_FIND_BY_ID, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int insert(User user) {
        try {
            return executeQueryWithParams(USERS_INSERT, new Object[]{
                    user.getId(), user.getEmail(), user.getPassword()});
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int update(User user){
        try {
            return executeQueryWithParams(USERS_UPDATE, new Object[]{
                    user.getEmail(), user.getPassword(), user.getId()
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int deleteById(UUID id){
        try {
            return executeQueryWithParams(USERS_DELETE_BY_ID, new Object[]{ id });
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean executeQuery(String query) throws SQLException {
        manager.connect();
        boolean isSucceed = manager.executePS(query);
        manager.close();

        return isSucceed;
    }

    public int executeQueryWithParams(String query, Object[] params) throws SQLException{
        manager.connect();
        int isSucceed = manager.executePSWithParams(query, params);
        manager.close();

        return isSucceed;
    }

    public List<User> getQueryResults(String query) throws SQLException{
        manager.connect();
        List<User> users = manager.getResultSet(query);
        manager.close();

        return  users;
    }

    public List<User> getUserById(String query, UUID id) throws SQLException {
        manager.connect();
        List<User> user = manager.getResultSetWithParams(query, new Object[]{ id });
        manager.close();

        return user;
    }
}
