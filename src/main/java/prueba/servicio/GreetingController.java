package prueba.servicio;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    private final List<Greeting> greetings = new ArrayList<>();

    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        Greeting newGreeting = new Greeting(counter.incrementAndGet(), String.format(template, name));
        greetings.add(newGreeting);
        return newGreeting;
    }

    @GetMapping("/greetings")
    public List<Greeting> getAllGreetings() {
        return greetings;
    }

    @PostMapping("/greeting")
    public Greeting createGreeting(@RequestBody Greeting request) {
        Greeting newGreeting = new Greeting(counter.incrementAndGet(), request.content());
        greetings.add(newGreeting);
        return newGreeting;
    }

    @PutMapping("/greeting/{id}")
    public Greeting updateGreeting(@PathVariable long id, @RequestBody Greeting request) {
        for (int i = 0; i < greetings.size(); i++) {
            if (greetings.get(i).id() == id) {
                Greeting updated = new Greeting(id, request.content());
                greetings.set(i, updated);
                return updated;
            }
        }
        return null;
    }

    @DeleteMapping("/greeting/{id}")
    public void deleteGreeting(@PathVariable long id) {
        greetings.removeIf(g -> g.id() == id);
    }
}
