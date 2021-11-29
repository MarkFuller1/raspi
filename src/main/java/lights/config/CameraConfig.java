package lights.config;

import lombok.extern.slf4j.Slf4j;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class CameraConfig {

    @Bean("cameraBean")
    public OpenCVFrameGrabber cameraConfig() {
        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
        try {
            grabber.start();
        } catch (FrameGrabber.Exception e) {
            log.error("Failed to find camera");
        }
        return grabber;
    }
}
