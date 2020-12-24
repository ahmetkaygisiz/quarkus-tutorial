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
            List<User> result = getQueryResults(USERS_FIND_ALL);
            return result;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }


    public List<User> findById(UUID id) {
        try {
            List<User> user = getUserById(USERS_FIND_BY_ID, id);
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insert(User user) {
        try {
            return executeQueryWithParams(USERS_INSERT, new Object[]{
                    user.getId(), user.getEmail(), user.getPassword()
            });
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean update(User user){
        try {
            return executeQueryWithParams(USERS_UPDATE, new Object[]{
                    user.getEmail(), user.getPassword(), user.getId()
            });
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean deleteById(UUID id){
        try {
            return executeQueryWithParams(USERS_DELETE_BY_ID, new Object[]{ id });
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean executeQuery(String query) throws SQLException {
        manager.connect();
        boolean isSucceed = manager.executePS(query);
        manager.close();

        return isSucceed;
    }

    public boolean executeQueryWithParams(String query, Object[] params) throws SQLException{
        manager.connect();
        boolean isSucceed = manager.executePSWithParams(query, params);
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
