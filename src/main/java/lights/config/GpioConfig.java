package lights.config;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GpioConfig {

    @Bean
    public GpioController getGpioController(){
        return GpioFactory.getInstance();
    }
}
