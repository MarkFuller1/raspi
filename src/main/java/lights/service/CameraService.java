package lights.service;


import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.opencv_core.IplImage;
import org.springframework.stereotype.Service;

@Service
public class CameraService {

    public String getPicture() {
        try {
            FrameGrabber grabber = new OpenCVFrameGrabber(0);
            grabber.start();
            Frame frame = grabber.grab();
            grabber.close();
            OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
            IplImage img = converter.convert(frame);
            opencv_imgcodecs.cvSaveImage("selfie.jpg", img);
            return "selfie.jpg";
        } catch (Exception e) {
            System.out.println("Failed to take pic");
        }
        return "";
    }
}
