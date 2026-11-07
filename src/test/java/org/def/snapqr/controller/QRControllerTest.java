
package org.def.snapqr.controller;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.http.*;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureTestRestTemplate
class QRControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private HttpEntity<String> createRequest(String json) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new HttpEntity<>(json, headers);
    }

    @Test
    void shouldReturnQRCodeForValidRequest() {

        // Arrange
        String json = """
                {
                    "content": "https://google.com",
                    "size": 400,
                    "foregroundColor": "#000000",
                    "backgroundColor": "#FFFFFF"
                }
                """;

        // Act
        ResponseEntity<byte[]> response = restTemplate.postForEntity(
                "/api/qr/static",
                createRequest(json),
                byte[].class
        );

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(
                MediaType.IMAGE_PNG,
                response.getHeaders().getContentType()
        );

        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);
    }

    @Test
    void shouldReturnBadRequestForEmptyContent() {

        // Arrange
        String json = """
                {
                    "content": "",
                    "size": 400,
                    "foregroundColor": "#000000",
                    "backgroundColor": "#FFFFFF"
                }
                """;

        // Act
        ResponseEntity<byte[]> response = restTemplate.postForEntity(
                "/api/qr/static",
                createRequest(json),
                byte[].class
        );

        // Assert
        assertEquals(
                HttpStatus.BAD_REQUEST,
                response.getStatusCode()
        );

        assertNotNull(response.getBody());

        String responseBody = new String(
                response.getBody(),
                StandardCharsets.UTF_8
        );

        assertTrue(responseBody.contains("message"));
    }

    @Test
    void shouldReturnBadRequestForInvalidSize() {

        // Arrange
        String json = """
                {
                    "content": "https://google.com",
                    "size": 50,
                    "foregroundColor": "#000000",
                    "backgroundColor": "#FFFFFF"
                }
                """;

        // Act
        ResponseEntity<byte[]> response = restTemplate.postForEntity(
                "/api/qr/static",
                createRequest(json),
                byte[].class
        );

        // Assert
        assertEquals(
                HttpStatus.BAD_REQUEST,
                response.getStatusCode()
        );
    }

    @Test
    void shouldReturnBadRequestWhenSizeIsMissing() {

        // Arrange
        String json = """
                {
                    "content": "https://google.com",
                    "foregroundColor": "#000000",
                    "backgroundColor": "#FFFFFF"
                }
                """;

        // Act
        ResponseEntity<byte[]> response = restTemplate.postForEntity(
                "/api/qr/static",
                createRequest(json),
                byte[].class
        );

        // Assert
        assertEquals(
                HttpStatus.BAD_REQUEST,
                response.getStatusCode()
        );
    }
}
