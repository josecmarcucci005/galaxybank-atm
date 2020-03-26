package com.galaxybank.repository;

import com.galaxybank.model.ATM;
import org.springframework.data.repository.CrudRepository;

public interface AtmRepository extends CrudRepository<ATM, Long> {
}
