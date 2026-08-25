# What PEs Need to Know About SE

## Part 3

### Implement the button in ReactJS

Add the `press-metrics` div to `App.tsx` under `Lab/software-engineering-need-to-know/frontend/src/`.

Replace the text that says `{/* Button goes here. */}`

```javascript
<div className="press-metrics">
        <button onClick={async () =>  {
          // @ts-expect-error
          let data = await fetch('/api/press').then(r => r.json());
          setCount((count) => count + 1)
        }}>
          Press Me!
        </button>
        <p>Button presses as counted in the browser: {count}</p>
      </div>
```

### Implement the Controller and Service in Java

#### The Controller Class

Create folder `metrics` under `Lab/software-engineering-need-to-know/src/main/java/com/example/experiment/`.
Create file `SignalController.java` in this folder with the following content.

```java
package com.example.experiment.metrics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

/**
 * Minimal API surface.
 *
 * Every request here is automatically recorded by Micrometer as
 * http_server_requests_seconds_* with method, status, and uri tags. On top of
 * that, /api/press and /api/fail drive the CUSTOM meters defined in
 * {@link SignalMetrics}, and /api/stats feeds the frontend.
 */
@RestController
@RequestMapping("/api")
public class SignalController {

    private static final Logger log = LoggerFactory.getLogger(SignalController.class);

    private final SignalMetrics metrics;

    public SignalController(SignalMetrics metrics) {
        this.metrics = metrics;
    }

    @GetMapping("/press")
    public Map<String, Object> press() {
        long count = metrics.recordSignal();
        log.info("Signal sent (#{})", count);
        return Map.of(
                "message", "Signal sent.",
                "signalsSent", count,
                "status", "ok"
        );
    }

    /**
     * Deliberately fails to pivot from a metric (5xx rate climbing) to the matching ERROR log line in Loki.
     */
    @GetMapping("/fail")
    public Map<String, String> fail() {
        metrics.recordFailure();
        log.error("PANIC!!!! I can't believe you did this. I hope you had a good reason everything is now on fire.");
        throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Simulated failure");
    }

    /**
     * JSON snapshot of the custom meters, consumed by the frontend so you can watch the same values that will later be queryed in Grafana.
     */
    @GetMapping("/stats")
    public Map<String, Object> stats() {
        return Map.of(
                "signalStrength", metrics.signalStrength(),
                "signalsSent", metrics.signalsSent(),
                "failures", metrics.failures(),
                "status", "UP"
        );
    }
}
```

#### The Service Class

Create file `SignalMetrics.java` in the same `metrics` folder under `Lab/software-engineering-need-to-know/src/main/java/com/example/experiment/` with the following content.

```java
package com.example.experiment.metrics;

import io.micrometer.core.instrument.FunctionCounter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class SignalMetrics {

    private final AtomicLong signalsSent = new AtomicLong(0);
    private final AtomicLong failures = new AtomicLong(0);
    private final AtomicInteger signalStrength = new AtomicInteger(72);

    public SignalMetrics(MeterRegistry registry) {
        FunctionCounter.builder("press.signals.sent", signalsSent, AtomicLong::doubleValue)
                .description("Total number of signals (greetings) sent")
                .register(registry);
        FunctionCounter.builder("press.failures", failures, AtomicLong::doubleValue)
                .description("Total number of simulated failures triggered")
                .register(registry);
    }

    public long recordSignal() {
        return signalsSent.incrementAndGet();
    }

    public long recordFailure() {
        return failures.incrementAndGet();
    }

    public int signalStrength() {
        return signalStrength.get();
    }

    public long signalsSent() {
        return signalsSent.get();
    }

    public long failures() {
        return failures.get();
    }

    /**
     * Make the gauge actually do something: random walk within bounds. 
     * A real app would derive a gauge from something meaningful (queue depth, cache size, etc.).
     */
    @Scheduled(fixedRate = 2000)
    void updateSignalStrength() {
        int current = signalStrength.get();
        int drift = ThreadLocalRandom.current().nextInt(-6, 7);
        int next = Math.max(5, Math.min(99, current + drift));
        signalStrength.set(next);
    }
}
```

### Build and Deploy

Build the app inside the container.

```shell
docker build "$PWD" -t need-to-know:latest --build-arg "PROJECT_DIR=$PWD" --no-cache
```

Deploy using `docker compose`.

```shell
docker compose up --detach
```

### Generate Metrics

Browse to the URL below and click the button.

```shell
http://127.0.0.1:8080
```

Or, use the CLI.

```shell
curl http://127.0.0.1:8080/api/press
```

### Get the Count of Times the Button Was Pressed

The metrics are accessed via the Actuator endpoint. This is the data that is gathered by Alloy, stored in Mimir, and displayed by Grafana.

```shell
curl localhost:8080/actuator/prometheus 2>/dev/null | egrep '^press_signals_sent_total'
```

### Clean Up

```shell
docker compose down --volumes --remove-orphans
```
