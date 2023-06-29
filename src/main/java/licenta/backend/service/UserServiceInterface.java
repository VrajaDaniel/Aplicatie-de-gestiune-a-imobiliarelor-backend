package licenta.backend.service;

import licenta.backend.dto.user.UserResponseBody;
import licenta.backend.model.User;
import licenta.backend.model.exception.UserException;

public interface UserServiceInterface {
    User saveUser(User user) throws UserException;

    User getUserById(Long id) throws UserException;

    User getUserByEmail(String email) throws UserException;

    UserResponseBody getUserByPostId(Long postId);
}