package com.sandey.children.app.qrcode.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandey.children.app.qrcode.entities.Children;
import com.sandey.children.app.qrcode.repository.ChildrenRepository;
import java.util.List;

@Service
public class ChildrenService {
	@Autowired
	ChildrenRepository childrenRepository;

	public Children addChildren(Children children) {
		return childrenRepository.save(children);
	}

	public List<Children> fetchActiveChildren() {
		return childrenRepository.fetchActiveChildren();
	}
}
