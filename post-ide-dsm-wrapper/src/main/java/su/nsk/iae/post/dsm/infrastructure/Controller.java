package su.nsk.iae.post.dsm.infrastructure;

import org.springframework.web.bind.annotation.*;
import su.nsk.iae.post.dsm.application.Executor;
import su.nsk.iae.post.dsm.application.Logger;

import java.util.LinkedHashMap;

@RestController
public class Controller {

    @RequestMapping(value = "")
    public String hello() {
        return "Hello world!";
    }

    @PostMapping(value = "run")
    public String run(@RequestBody LinkedHashMap<String, Object> request) {
        Logger.info(Controller.class, "/run for request: " + request.toString());
        return Executor.execute(request);
    }

    @GetMapping(value = "stop")
    public void stop() {
        Logger.info(Controller.class, "/stop");
        System.exit(0);
    }
}