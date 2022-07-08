package kdh.boardproject.repository;


import kdh.boardproject.entity.User;
import kdh.boardproject.repository.*;
import org.junit.jupiter.api.Test;


class UserRepositoryTest {

    UserRepository userRepository;

    @Test
    public void save(){
        User user = new User();
        user.setId("spring");

        userRepository.save(user);

        User result = userRepository.findById(user.getIdx()).get();
    }
}
