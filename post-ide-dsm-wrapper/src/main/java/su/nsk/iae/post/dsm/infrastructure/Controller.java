package su.nsk.iae.post.dsm.infrastructure;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import su.nsk.iae.post.dsm.application.Executor;
import java.util.LinkedHashMap;

@RestController
public class Controller {

    @RequestMapping(value = "")
    public String hello() {
        return "Hello world!";
    }

    @PostMapping(value = "run")
    public String run(@RequestBody LinkedHashMap<String, Object> request) {
        System.out.printf("/run for request: " + request.toString());
        return Executor.execute(request);
    }
}