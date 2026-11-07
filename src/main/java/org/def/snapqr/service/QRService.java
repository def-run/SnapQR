package org.def.snapqr.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.def.snapqr.dto.StaticQRRequest;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class QRService {

    public byte[] generateQRCode(StaticQRRequest request)
            throws WriterException, IOException {

        String content = request.getContent();
        int size = request.getSize();

        Map<EncodeHintType, Object> hints  = new HashMap<>();
        hints.put(EncodeHintType.MARGIN, 2);

        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        BitMatrix bitMatrix = qrCodeWriter.encode(
                content,
                BarcodeFormat.QR_CODE,
                size,
                size,
                hints
        );

        int foreground = java.awt.Color.decode(
                request.getForegroundColor()
        ).getRGB();

        int background = java.awt.Color.decode(
                request.getBackgroundColor()
        ).getRGB();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        MatrixToImageWriter.writeToStream(
                bitMatrix,
                "PNG",
                baos,
                new MatrixToImageConfig(foreground, background)
        );

        return baos.toByteArray();
    }
}
