package by.moon.viewbot.service.beanservice;

import by.moon.viewbot.bean.User;
import by.moon.viewbot.enums.CurrentStep;
import by.moon.viewbot.enums.Role;
import by.moon.viewbot.exceptions.UserNotFoundException;
import by.moon.viewbot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Chat;

import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;

    public User findByChatId(Long chatId) throws UserNotFoundException {
        return userRepository.findByChatId(chatId).orElseThrow(UserNotFoundException::new);
    }

    public User create(Chat chat){
        User newUser = new User();
        newUser.setChatId(chat.getId());
        newUser.setUsername(chat.getUserName());
        newUser.setFirstName(chat.getFirstName());
        newUser.setLastName(chat.getLastName());
        newUser.setCurrentStep(CurrentStep.START);
        newUser.setRole(Role.USER);
        return userRepository.save(newUser);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User save(User user){
        return userRepository.save(user);
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
