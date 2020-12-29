package com.stp.demo.Controller;

import com.stp.demo.model.Card;
import com.stp.demo.model.User;
import com.stp.demo.postRequest.Data;
import com.stp.demo.service.CardService;
import com.stp.demo.service.InstitutionService;
import com.stp.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class UserController {

    private final UserService userService;
    private final CardService cardService;
    private final InstitutionService institutionService;

    @Autowired
    public UserController(UserService userService, CardService cardService, InstitutionService institutionService) {
        this.userService = userService;
        this.cardService = cardService;
        this.institutionService = institutionService;
    }

    @CrossOrigin(origins = "http://localhost:4000")
    @GetMapping("/user/getUsers")
    public List<User> findAllUsers() {
        List<User> user = userService.customSelect();
        return user;
    }


    @CrossOrigin(origins = "http://localhost:4000")
    @GetMapping(value = "/user-create/{name}/{password}/{role}")
    public String createUser(@PathVariable("name") String name,
                             @PathVariable("password") String password,
                             @PathVariable("role") String role) {
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.setRole(role);
        userService.saveUser(user);
        return "/user";
    }


    @CrossOrigin(origins = "http://localhost:4000")
    @GetMapping("/user-delete/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        User user = userService.findById(id);
        System.out.println(user.getId_user());
        List<Card> cards = new ArrayList<Card>();
        cards = cardService.selectCardByUserId(user.getId_user());

        for (Card card : cards) {
            cardService.deleteById(card.getId_card());
        }
        userService.deleteById(id);
    }

    @CrossOrigin(origins = "http://localhost:4000")
    @GetMapping("/user-info/{id}/get")
    public Map<String, Object> userInfoForm(@PathVariable("id") Long id) {
        User user = userService.getUserInfo(id);
        List<Card> card = cardService.selectCardFromUser(id);
        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("card", card);
        return map;
    }

    @GetMapping("/user-update/{id}/getInfo")
    public User updateUserForm(@PathVariable("id") Long id) {
        User user = userService.getUserInfo(id);
        return user;
    }

    @CrossOrigin(origins = "http://localhost:4000")
    @RequestMapping(value = "/user-update/confirmed", method = RequestMethod.POST)
    public void updateUser(@RequestBody Data data) {
        userService.updUserName(data.getName(), data.getId());
    }

}

