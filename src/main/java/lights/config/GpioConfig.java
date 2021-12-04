package lights.config;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Profile("!local")
@Configuration
public class GpioConfig {

    public static final int LED_PID_NUMBER = 25;
    public static final int BUTTON_START_PID_NUMBER = 23;
    public static final int BUTTON_STOP_PID_NUMBER = 24;

    private static List<Context> contexts = new ArrayList<Context>();


    public Context getGpioConfig() {
        // keep track of the contexts so that we can close them
        contexts.add(Pi4J.newAutoContext());
        return contexts.get(contexts.size() - 1);
    }

    @Bean
    public DigitalOutputConfigBuilder getDigitalOutput() {
        return DigitalOutput.newConfigBuilder(getGpioConfig())
                .id("led")
                .name("toggle led")
                .address(LED_PID_NUMBER)
                .shutdown(DigitalState.LOW)
                .initial(DigitalState.LOW)
                .provider("pigpio-digital-output");
    }

    @Bean
    public DigitalOutput getLed() {
        return getGpioConfig().create(getDigitalOutput());
    }

    @Bean(name = "STARTBUTTONDI")
    public DigitalInputConfigBuilder getStartButtonDigitalInput() {
        return DigitalInput.newConfigBuilder(getGpioConfig())
                .id("button")
                .name("Press button")
                .address(BUTTON_START_PID_NUMBER)
                .pull(PullResistance.PULL_DOWN)
                .debounce(3000L)
                .provider("pigpio-digital-input");
    }

    @Bean(name = "STOPBUTTONDI")
    public DigitalInputConfigBuilder getStopButtonDigitalInput() {
        return DigitalInput.newConfigBuilder(getGpioConfig())
                .id("button")
                .name("Press button")
                .address(BUTTON_STOP_PID_NUMBER)
                .pull(PullResistance.PULL_DOWN)
                .debounce(3000L)
                .provider("pigpio-digital-input");
    }

    @Bean(name = "STARTBUTTON")
    public DigitalInput getStartButton() {
        return getGpioConfig().create(getStartButtonDigitalInput());
    }

    @Bean(name = "STOPBUTTON")
    public DigitalInput getStopButton() {
        return getGpioConfig().create(getStopButtonDigitalInput());
    }

    public static void shutdown() {
        log.info("cleaning up gpio contexts");
        contexts.forEach(Context::shutdown);
    }

}
