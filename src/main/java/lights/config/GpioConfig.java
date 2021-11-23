package lights.config;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GpioConfig {

    @Bean
    public Context getGpioController(){

        var pi4j = Pi4J.newAutoContext();
        return pi4j;
    }
}
