package com.sandey.children.app.qrcode.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;

import com.sandey.children.app.qrcode.entities.Children;
import com.sandey.children.app.qrcode.entities.QrcodeStatus;
import com.sandey.children.app.qrcode.repository.ChildrenRepository;

@ExtendWith(MockitoExtension.class)
public class ChildrenServiceTest {
	@Mock
	ChildrenRepository childrenRepository;

	@InjectMocks
	ChildrenService childrenService;

	@Test
	public void testAddChildren() {
		Children children = buildChildren();
		when(childrenRepository.save(any())).thenReturn(children);
		Children resultChildChildren =  childrenService.addChildren(any());
		assertEquals(children, resultChildChildren);
	}
	
	@Test
	public void testAddChildren_WithInvalidData() {
		Children children = buildChildren();
		children.setName(null);
		when(childrenRepository.save(any())).thenThrow(DataIntegrityViolationException.class);
		assertThrows(DataIntegrityViolationException.class, ()->childrenService.addChildren(any()));
	}
	
	@Test
	public void testActiveChildren() {
		Children children = buildChildren();
		List<Children> childList = new ArrayList<>();
		childList.add(children);
		when(childrenRepository.fetchActiveChildren()).thenReturn(childList);
		List<Children> resultChildList = childrenService.fetchActiveChildren();
		assertEquals(childList, resultChildList);
	}
	
	@Test
	public void testActiveChildren_WithNoData() {
		when(childrenRepository.fetchActiveChildren()).thenReturn(new ArrayList<Children>());
		List<Children> resultChildList = childrenService.fetchActiveChildren();
		assertTrue(resultChildList.size() == 0);
	}

	private Children buildChildren() {
		Children children = new Children();
		children.setId(1);
		children.setAge(23);
		children.setGender("F");
		children.setName("Ravi");
		QrcodeStatus qrcodeStatus = new QrcodeStatus();
		qrcodeStatus.setActive(false);
		qrcodeStatus.setIsactive(false);
		qrcodeStatus.setId(1);
		children.setQrcodeStatus(qrcodeStatus);
		return children;
	}
}
