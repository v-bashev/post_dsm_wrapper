package su.nsk.iae.post.dsm;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @RequestMapping(value = "hello")
    public String hello(){
        return "i'm alive";
    }
}