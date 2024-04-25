package se.jaouhari.shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.jaouhari.shop.entities.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

    Optional<User>findById(int id);
    Optional<User> findByUsername(String username);
    List<User> findUserByUsername(String name);
    List<User> findByUsernameAndPassword(String username, String password);

}
