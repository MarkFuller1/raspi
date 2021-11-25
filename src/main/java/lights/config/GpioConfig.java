package lights.config;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalOutputConfigBuilder;
import com.pi4j.io.gpio.digital.DigitalState;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.annotation.PreDestroy;

@Configuration
public class GpioConfig {

    public static final int PID_NUMBER = 4;

    @Bean
    @Profile("!local")
    public Context getGpioConfig() {
        return Pi4J.newAutoContext();
    }

    @Bean
    @Profile("!local")
    public DigitalOutputConfigBuilder getDigitalOutput() {
        return DigitalOutput.newConfigBuilder(getGpioConfig())
                .id("led")
                .name("toggle led")
                .address(PID_NUMBER)
                .shutdown(DigitalState.LOW)
                .initial(DigitalState.LOW)
                .provider("pigpio-digital-output");
    }

    @Bean
    @Profile("!local")
    public DigitalOutput getLed() {
        return getGpioConfig().create(getDigitalOutput());
    }

    @PreDestroy
    @Profile("!local")
    public void shutdown() {
        getGpioConfig().shutdown();
    }
}
