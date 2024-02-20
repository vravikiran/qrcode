package com.sandey.children.app.qrcode.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.sandey.children.app.qrcode.entities.Children;
@Repository
public interface ChildrenRepository extends CrudRepository<Children, Integer> {
@Query(value = "select ch.id,ch.name,ch.gender,ch.age from children ch, qrcode_status qr where qr.id = ch.id and qr.isactive=1",nativeQuery = true)
public List<Children> fetchActiveChildren();
}
