package lights.service;


import lombok.extern.slf4j.Slf4j;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.opencv_core.IplImage;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CameraService {

    private final String imageName = "selfie.jpg";

    public String getPicture() {
        try {
            FrameGrabber grabber = new OpenCVFrameGrabber(0);
            grabber.start();
            Frame frame = grabber.grab();
            grabber.close();
            OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
            IplImage img = converter.convert(frame);
            opencv_imgcodecs.cvSaveImage(imageName, img);
            return imageName;
        } catch (Exception e) {
            log.info("Failed to take pic");
        }
        return "";
    }
}
