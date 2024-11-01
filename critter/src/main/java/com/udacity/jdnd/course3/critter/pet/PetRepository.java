package com.udacity.jdnd.course3.critter.pet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface PetRepository extends JpaRepository<PetEntity, Long> {
    List<PetEntity> findPetEntitiesByCustomerId(Long id);
}
