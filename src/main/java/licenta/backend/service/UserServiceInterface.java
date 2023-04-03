package licenta.backend.service;

import licenta.backend.model.User;
import licenta.backend.model.exception.UserException;

import java.util.List;

public interface UserServiceInterface {
    User saveUser(User user) throws UserException;

    void deleteUser(Long id) throws UserException;

    User updateUser(Long id, User user) throws UserException;

    List<User> getAllUsers();

    User getUserById(Long id) throws UserException;

    User getUserByEmail(String email) throws UserException;

}