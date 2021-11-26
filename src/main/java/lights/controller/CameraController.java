package lights.controller;

import lights.service.CameraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping("/camera")
public class CameraController {

    @Autowired
    CameraService camera;

    @GetMapping
    public @ResponseBody
    ResponseEntity<byte[]> getImage() throws IOException {
        File image = new File(camera.getPicture());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(Files.readAllBytes(image.toPath()));
    }
}
