package com.sandey.children.app.qrcode.controller;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.sandey.children.app.qrcode.entities.Children;
import com.sandey.children.app.qrcode.genandread.QrcodeReader;
import com.sandey.children.app.qrcode.repository.ChildrenRepository;

@RestController
@RequestMapping("/ReadQrcode")
public class QrCodeStatusController {
	@Autowired
	QrcodeReader qrcodeReader;
	@Autowired
	ChildrenRepository childrenRepository;

	@PostMapping("/uploadQrCode")
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile multipartFile) {
		String value = null;
		if (multipartFile.getContentType().equals("image/jpeg")) {
			value = qrcodeReader.ReadQr(multipartFile);
		} else {
			throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
		}
		if (StringUtils.isNumeric(value)) {
			Optional<Children> children = childrenRepository.findById(Integer.parseInt(value));
			if (!children.isPresent()) {
				throw new NoSuchElementException();
			} else {
				Children childrenObj = children.get();
				childrenObj.getQrcodeStatus().setActive(true);
				childrenRepository.save(childrenObj);
				System.out.println("Updated the child status to active");
			}
		} else {
			throw new InputMismatchException();
		}

		return ResponseEntity.status(HttpStatus.valueOf(200)).build();
	}

	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<Object> NoValuePresent() {
		return new ResponseEntity<Object>("The child details are not present", new HttpHeaders(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InputMismatchException.class)
	public ResponseEntity<Object> InvalidInput() {
		return new ResponseEntity<Object>("The QRCode is invalid, input should be an Integer", new HttpHeaders(),HttpStatus.BAD_REQUEST);
	}
}
