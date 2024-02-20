package com.sandey.children.app.qrcode.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.sandey.children.app.qrcode.entities.Children;
import com.sandey.children.app.qrcode.entities.QrcodeStatus;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ChildrenRepositoryTest {
	@Autowired
	TestEntityManager entityManager;

	@Autowired
	ChildrenRepository childrenRepository;

	@Test
	public void givenChildren_WhenSave_ThenSuccess() {
		Children children = buildChildren();
		Children resultChildren = childrenRepository.save(children);
		assertThat(entityManager.find(Children.class, resultChildren.getId())).isEqualTo(children);
	}
	
	@Test
	public void test_FetchACtiveChildren() {
		Children children = buildChildren();
		QrcodeStatus qrcodeStatus = new QrcodeStatus();
		qrcodeStatus.setActive(true);
		qrcodeStatus.setIsactive(true);
		qrcodeStatus.setId(1);
		children.setQrcodeStatus(qrcodeStatus);
		childrenRepository.save(children);
		List<Children> resultChildren = childrenRepository.fetchActiveChildren();
		assertTrue(resultChildren.get(0).getQrcodeStatus().isActive());
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
