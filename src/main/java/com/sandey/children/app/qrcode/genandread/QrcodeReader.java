package com.sandey.children.app.qrcode.genandread;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

@Component
public class QrcodeReader {
	public String ReadQr(MultipartFile multipartFile) {
		BinaryBitmap binaryBitmap;
		Result result = null;
		try {
			File file = new File("/users/vvravikiran/downloads/" + multipartFile.getOriginalFilename());
			multipartFile.transferTo(file);
			binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(ImageIO.read(file))));
			result = new MultiFormatReader().decode(binaryBitmap);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result.getText();
	}
}
