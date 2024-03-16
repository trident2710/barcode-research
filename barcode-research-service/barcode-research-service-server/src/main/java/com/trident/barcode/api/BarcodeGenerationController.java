package com.trident.barcode.api;

import com.trident.barcode.image.BarcodeImageGenerators;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
public class BarcodeGenerationController {

    @RequestMapping(path = "/barcode/generate", method = RequestMethod.GET)
    public ResponseEntity<Resource> generateBarcode(@RequestParam String message) throws IOException {

        var generator = BarcodeImageGenerators.BASE_41_RS;
        var image = generator.generateBarcodeImage(message);

        var outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "bmp", outputStream);
        var resource = new ByteArrayResource(outputStream.toByteArray());

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=barcode.bmp")
                .contentLength(outputStream.toByteArray().length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
