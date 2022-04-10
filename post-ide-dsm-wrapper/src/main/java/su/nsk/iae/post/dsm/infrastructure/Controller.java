package su.nsk.iae.post.dsm.infrastructure;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import su.nsk.iae.post.dsm.application.Executor;

@RestController
public class Controller {

    @RequestMapping(value = "")
    public String hello() {
        return "Hello world!";
    }

    @PostMapping(value = "run")
    public String run(@RequestBody DsmRequestBody requestBody) {
        return Executor.execute(requestBody.toDomain());
    }
}