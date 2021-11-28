package lights.config;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.annotation.PreDestroy;

@Profile("!local")
@Configuration
public class GpioConfig {

    public static final int LED_PID_NUMBER = 4;
    public static final int BUTTON_PID_NUMBER = 27;

    @Bean
    public Context getGpioConfig() {
        return Pi4J.newAutoContext();
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

    @Bean
    public DigitalInputConfigBuilder getDigitalInput() {
        return DigitalInput.newConfigBuilder(getGpioConfig())
                .id("button")
                .name("Press button")
                .address(BUTTON_PID_NUMBER)
                .pull(PullResistance.PULL_DOWN)
                .debounce(1000L)
                .provider("pigpio-digital-input");
    }

    @Bean
    public DigitalInput getButton() {
        return getGpioConfig().create(getDigitalInput());
    }

    @PreDestroy
    public void shutdown() {
        getGpioConfig().shutdown();
    }
}
