package hello.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Date: 24/02/15
 * Time: 11:54
 *
 * @author Geoffroy Warin (http://geowarin.github.io)
 */
@Controller
public class LoginController {

    @RequestMapping("/login")
    public String authenticate() {
        return "login";
    }
}
