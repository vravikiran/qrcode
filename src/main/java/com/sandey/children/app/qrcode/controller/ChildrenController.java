package com.sandey.children.app.qrcode.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sandey.children.app.qrcode.entities.Children;
import com.sandey.children.app.qrcode.entities.QrcodeStatus;
import com.sandey.children.app.qrcode.service.ChildrenService;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("/Children")
@CrossOrigin(origins = "*")
public class ChildrenController {
	@Autowired
	ChildrenService childrenService;

	@PostMapping("/addChild")
	@ResponseBody
	public Children AddChild(@RequestBody Children children) {
		QrcodeStatus qrcodeStatus = new QrcodeStatus();
		qrcodeStatus.setActive(false);
		children.setQrcodeStatus(qrcodeStatus);
		childrenService.addChildren(children);
		return children;
	}

	@GetMapping("/fetchActiveChildren")
	public List<Children> fetchActiveChildren() {
		return childrenService.fetchActiveChildren();
	}
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Object> handleDataInegrityViol() {
		return new ResponseEntity<Object>("Some of the data is missing while creating children object", new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
