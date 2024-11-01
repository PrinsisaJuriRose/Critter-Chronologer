package com.udacity.jdnd.course3.critter.schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {
    List<ScheduleEntity> findAllByPetsId(Long id);
    List<ScheduleEntity> findAllByEmployeesId(Long id);

    @Query("SELECT s FROM ScheduleEntity s JOIN s.pets p WHERE p.customer.id = :customerId")
    List<ScheduleEntity> findAllByCustomerId(@Param("customerId") Long customerId);
}
