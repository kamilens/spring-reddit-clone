package reddit.clone.reddit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reddit.clone.reddit.domain.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @Modifying
    @Query("update User u set u.password=:password where u.username=:username")
    void changePassword(@Param("username") String username, @Param("password") String password);

    @Modifying
    @Query("update User u set u.email=:email where u.username=:username")
    void changeEmail(@Param("username") String username, @Param("email") String email);

/*
    @Modifying
    @Query("update User u set u.username=:newUsername where u.username=:currentUsername")
    void changeUsername(@Param("currentUsername") String currentUsername, @Param("newUsername") String newUsername);
*/

}
