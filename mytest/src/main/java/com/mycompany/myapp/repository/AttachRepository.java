package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Attach;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Attach entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttachRepository extends JpaRepository<Attach, Long> {}
