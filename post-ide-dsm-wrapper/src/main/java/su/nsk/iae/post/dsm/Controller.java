package su.nsk.iae.post.dsm;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @RequestMapping(value = "")
    public String hello() {
        return "Hello world!";
    }
}