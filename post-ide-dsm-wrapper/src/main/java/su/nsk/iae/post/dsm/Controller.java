package su.nsk.iae.post.dsm;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import su.nsk.iae.post.dsm.data.DsmRequestBody;

@RestController
public class Controller {

    @RequestMapping(value = "")
    public String hello() {
        return "Hello world!";
    }

    @PostMapping(value = "run")
    public String run(DsmRequestBody requestBody) {
        return Executor.execute(requestBody);
    }
}