package com.bootcamp.melifrescos.repository;

import com.bootcamp.melifrescos.model.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEmailRepo extends JpaRepository<Email, Long> {
}
