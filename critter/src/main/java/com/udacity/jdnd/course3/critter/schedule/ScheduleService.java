package com.udacity.jdnd.course3.critter.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    public ScheduleEntity saveSchedule(ScheduleEntity schedule)
    {
        return scheduleRepository.save(schedule);
    }

    public List<ScheduleEntity>getAllSchedules()
    {
        return scheduleRepository.findAll();
    }

    public List<ScheduleEntity> getScheduleByPetId(Long id)
    {
       return scheduleRepository.findAllByPetsId(id);
    }

    public List<ScheduleEntity> getScheduleByEmployeeId(Long id)
    {
        return scheduleRepository.findAllByEmployeesId(id);
    }

    public List<ScheduleEntity> getScheduleByCustomerId(Long id)
    {
        return scheduleRepository.findAllByCustomerId(id);
    }
}
