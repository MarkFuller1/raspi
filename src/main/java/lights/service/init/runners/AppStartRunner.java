package lights.service.init.runners;

import lights.service.GpioControllerService;
import lights.service.TimerService;
import lights.service.kafka.ProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.NetworkInterface;
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
    ProducerService<String> producerService;

    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private volatile ScheduledFuture<?> task;

    @Override
    public void run(ApplicationArguments args) {
        gpioControllerService.blink(10, 200);
        System.err.println("Initialization complete");

        new Thread(() -> {
            task = executor.scheduleWithFixedDelay(() -> {
                if (timer.isElapsed()) {
                    System.out.println("Timer complete");
                    gpioControllerService.blink(10, 200);
                    timer.setTimer(0, 0, 0, 0);
                    produceTimerMessage();
                }
            }, 0, 1, TimeUnit.SECONDS);
        }).start();
    }

    public void produceTimerMessage() {
        try {
            producerService.produce("Timer expired:" + Arrays.toString(NetworkInterface.getByInetAddress(InetAddress.getLocalHost()).getHardwareAddress()));
        } catch (Exception e) {
            log.error("Failed to produce the message");
            log.error(Arrays.toString(e.getStackTrace()));
        }
    }
}
