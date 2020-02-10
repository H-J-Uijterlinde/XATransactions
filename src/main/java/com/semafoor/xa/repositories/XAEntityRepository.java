package com.semafoor.xa.repositories;

import com.semafoor.xa.model.XAEntity;
import org.springframework.data.repository.CrudRepository;

public interface XAEntityRepository extends CrudRepository<XAEntity, Long> {
}
