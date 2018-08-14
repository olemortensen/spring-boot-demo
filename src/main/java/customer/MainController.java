package customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping(path = "/demo")
public class MainController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChildRepository childRepository;

    @PostMapping(path = "/user")
    public @ResponseBody UserDto createUser(@RequestBody UserDto userDto) {
        User repoUser = new User();
        repoUser.setName(userDto.getName());
        repoUser.setEmail(userDto.getEmail());
        for (ChildDto childDto : userDto.getChildren()) {
            Child repoChild = new Child();
            repoChild.setAge(childDto.getAge());
            repoChild.setGender(childDto.getGender());
            repoChild.setName(childDto.getName());
            repoUser.addChild(repoChild);
        }
        userRepository.save(repoUser);
        return userDto;
    }


    @GetMapping(path = "/all")
    public @ResponseBody List<UserDto> getAllUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .map(e -> {
                    UserDto dto = new UserDto();
                    dto.setEmail(e.getEmail());
                    dto.setName(e.getName());
                    return dto;
                }).collect(Collectors.toList());

    }
}