package ma.anas.sisba.Repositories;

import ma.anas.sisba.Entities.TestObject;
import ma.anas.sisba.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, String> {
    @Query("select u from User u where Lower(u.email)=:email")
    Optional<User> findByEmail(@Param("email") String email);
}
