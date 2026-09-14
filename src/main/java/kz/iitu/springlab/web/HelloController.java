package kz.iitu.springlab.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
public class HelloController {

    @Value("${app.owner:unknown}")
    private String owner;

    @GetMapping("/hello")
    public Greeting hello(@RequestParam(defaultValue = "world") String name) {
        return new Greeting("Hello, " + name + "!", owner, LocalDateTime.now());
    }

    @GetMapping("/info")
    public Info info() {
        return new Info(owner,
                System.getProperty("java.version"),
                Runtime.getRuntime().availableProcessors());
    }

    @GetMapping("/stats")
    public StatsResult stats(@RequestParam(required = false) String numbers) {
        if (numbers == null || numbers.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Query parameter 'numbers' is required, e.g. ?numbers=1,2,3");
        }

        List<Double> values;
        try {
            values = Arrays.stream(numbers.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .map(Double::parseDouble)
                    .toList();
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "All values in 'numbers' must be valid numbers, got: " + numbers);
        }

        if (values.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "'numbers' must contain at least one value");
        }

        double min = values.stream()
                .mapToDouble(Double::doubleValue)
                .min()
                .orElseThrow();

        double max = values.stream()
                .mapToDouble(Double::doubleValue)
                .max()
                .orElseThrow();

        double avg = values.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElseThrow();

        return new StatsResult(values, min, max, avg, values.size());
    }

    public record Greeting(String message, String owner, LocalDateTime timestamp) { }

    public record Info(String owner, String javaVersion, int cpuCores) { }

    public record StatsResult(
            List<Double> numbers,
            double min,
            double max,
            double average,
            int count
    ) { }
}