package org.def.snapqr.service;

import org.def.snapqr.dto.StaticQRRequest;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;

class QRServiceTest {

    private final QRService qrService = new QRService();

    private StaticQRRequest createRequest(
            String content,
            int size,
            String foregroundColor,
            String backgroundColor
    ) {
        StaticQRRequest request = new StaticQRRequest();

        request.setContent(content);
        request.setSize(size);
        request.setForegroundColor(foregroundColor);
        request.setBackgroundColor(backgroundColor);

        return request;
    }

    @Test
    void shouldGenerateValidPngImage() throws Exception {

        // Arrange
        StaticQRRequest request = createRequest(
                "https://google.com",
                400,
                "#000000",
                "#FFFFFF"
        );

        // Act
        byte[] result = qrService.generateQRCode(request);

        // Assert
        assertNotNull(result);
        assertTrue(result.length > 0);

        BufferedImage image = ImageIO.read(
                new ByteArrayInputStream(result)
        );

        assertNotNull(image, "Generated data should be a valid image");
        assertEquals(400, image.getWidth());
        assertEquals(400, image.getHeight());
    }

    @Test
    void shouldGenerateQRCodeWithCustomColors() throws Exception {

        // Arrange
        StaticQRRequest request = createRequest(
                "https://example.com",
                300,
                "#FF0000",
                "#FFFFFF"
        );

        // Act
        byte[] result = qrService.generateQRCode(request);

        BufferedImage image = ImageIO.read(
                new ByteArrayInputStream(result)
        );

        // Assert
        assertNotNull(image);

        boolean hasRedPixel = false;
        boolean hasWhitePixel = false;

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {

                int pixel = image.getRGB(x, y);

                if (pixel == Color.RED.getRGB()) {
                    hasRedPixel = true;
                }

                if (pixel == Color.WHITE.getRGB()) {
                    hasWhitePixel = true;
                }
            }
        }

        assertTrue(hasRedPixel, "Image should contain red pixels");
        assertTrue(hasWhitePixel, "Image should contain white pixels");
    }

    @Test
    void shouldGenerateQRCodeWithRequestedDimensions() throws Exception {

        // Arrange
        StaticQRRequest request = createRequest(
                "SnapQR testing",
                250,
                "#000000",
                "#FFFFFF"
        );

        // Act
        byte[] result = qrService.generateQRCode(request);

        BufferedImage image = ImageIO.read(
                new ByteArrayInputStream(result)
        );

        // Assert
        assertNotNull(image);
        assertEquals(250, image.getWidth());
        assertEquals(250, image.getHeight());
    }
}

