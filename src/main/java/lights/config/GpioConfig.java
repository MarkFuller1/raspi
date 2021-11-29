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
    public static final int BUTTON_START_PID_NUMBER = 27;
    public static final int BUTTON_STOP_PID_NUMBER = 22;
    public static final int BUTTON_AUX_PID_NUMBER = 5;


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

    @Bean(name = "STARTBUTTONDI")
    public DigitalInputConfigBuilder getStartButtonDigitalInput() {
        return DigitalInput.newConfigBuilder(getGpioConfig())
                .id("button")
                .name("Press button")
                .address(BUTTON_START_PID_NUMBER)
                .pull(PullResistance.PULL_DOWN)
                .debounce(1000L)
                .provider("pigpio-digital-input");
    }

    @Bean(name = "STOPBUTTONDI")
    public DigitalInputConfigBuilder getStopButtonDigitalInput() {
        return DigitalInput.newConfigBuilder(getGpioConfig())
                .id("button")
                .name("Press button")
                .address(BUTTON_STOP_PID_NUMBER)
                .pull(PullResistance.PULL_DOWN)
                .debounce(1000L)
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

    @PreDestroy
    public void shutdown() {
        getGpioConfig().shutdown();
    }
}
