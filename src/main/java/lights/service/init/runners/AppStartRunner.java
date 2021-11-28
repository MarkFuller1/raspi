package lights.service.init.runners;

import lights.service.GpioControllerService;
import lights.service.TimerService;
import lights.service.kafka.ProducerService;
import lights.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
@Profile("!local")
@Component
public class AppStartRunner implements ApplicationRunner {

    @Autowired
    GpioControllerService gpioControllerService;

    @Autowired
    TimerService timer;

    @Autowired
    ProducerService producerService;

    private final ScheduledExecutorService timerExecutor = Executors.newSingleThreadScheduledExecutor();
    private final ScheduledExecutorService timerListenerExecutor = Executors.newSingleThreadScheduledExecutor();
    private volatile ScheduledFuture<?> task;

    @Override
    public void run(ApplicationArguments args) {

        //physical representation of startup success
        gpioControllerService.blink(10, 200);
        log.info("Initialization complete");

        // tell kafka broker node has started
        initNode();

        // start timer thread;
        startTimerThread();
    }

    public void produceTimerMessage() {
    }

    public void initNode() {
        try {
            producerService.produce("Node startup:" + Constants.MAC_ADDRESS);
        } catch (Exception e) {
            log.error("failed to send node startup message");
        }
    }

    public void startTimerThread() {
        task = timerExecutor.scheduleWithFixedDelay(() -> {
            if (timer.isElapsed()) {
                log.info("Timer complete");
                gpioControllerService.blink(10, 200);
                timer.setTimer(0, 0, 0, 0);
                producerService.produce("Timer expired");
            }
        }, 0, 1, TimeUnit.SECONDS);

        timerListenerExecutor.scheduleWithFixedDelay(() -> {
            if (task.isCancelled() || task.isDone()) {
                log.error("The timer thread has ended");
            }
        }, 0, 1, TimeUnit.SECONDS);
    }
}
