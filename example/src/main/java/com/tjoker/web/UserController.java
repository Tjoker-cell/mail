package com.tjoker.web;

import com.tjoker.model.User;
import com.tjoker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @program: tjoker
 * @description:
 * @author: 十字街头的守候
 * @create: 2021-05-25 17:32
 **/
@RestController
@RequestMapping("/usr")
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("/login")
    public String deleteById(@RequestParam("username") String username,
                                   @RequestParam("password") String password,
                                   Model model){
        String res=userService.login(username,password);
        model.addAttribute("msg",res);
        return "login";
    }
}