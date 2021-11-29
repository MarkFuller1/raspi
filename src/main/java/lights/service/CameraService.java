package lights.service;


import lombok.extern.slf4j.Slf4j;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.opencv_core.IplImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;

@Slf4j
@Service
public class CameraService {

    @Qualifier("cameraBean")
    @Autowired
    OpenCVFrameGrabber grabber;

    private final String imageName = "selfie.jpg";

    public String getPicture() {
        try {
            Frame frame = grabber.grab();
            OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
            IplImage img = converter.convert(frame);
            opencv_imgcodecs.cvSaveImage(imageName, img);
            return imageName;
        } catch (FrameGrabber.Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @PreDestroy
    public void shutdown() {
        try {
            log.info("Closing camera handle");
            grabber.close();
            log.info("Closed camera handle");
        } catch (FrameGrabber.Exception e) {
            log.error("Failed to close camera handle");
            e.printStackTrace();
        }
    }
}
