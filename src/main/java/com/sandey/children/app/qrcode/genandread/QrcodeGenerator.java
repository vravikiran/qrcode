package com.sandey.children.app.qrcode.genandread;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QrcodeGenerator {
	public static void generateQrCode() {
		Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
		hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
		Integer data = 7;
		BitMatrix matrix;
		Path path = Paths.get("/users/vvravikiran/downloads/demo3.jpg");
		try {
			matrix = new MultiFormatWriter().encode(new String(data.toString().getBytes("UTF-8"), "UTF-8"),
					BarcodeFormat.QR_CODE, 150, 150);
			MatrixToImageWriter.writeToPath(matrix, "jpg", path);
		} catch (UnsupportedEncodingException | WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String args[]) {
		generateQrCode();
	}
}
