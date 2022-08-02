package com.testtask.restaurant.service;

import com.testtask.restaurant.convertion.EmployeeConversion;
import com.testtask.restaurant.entity.EmployeeDAO;
import com.testtask.restaurant.entity.PositionDAO;
import com.testtask.restaurant.repository.EmployeeRepository;
import com.testtask.restaurant.repository.PositionRepository;
import com.testtask.restaurant.transfer.EmployeeTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PositionRepository positionRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                               PositionRepository positionRepository){
        this.employeeRepository = employeeRepository;
        this.positionRepository = positionRepository;
    }

    @Override
    @Transactional
    public List<EmployeeTO> getEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(EmployeeConversion::convertToTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EmployeeTO getEmployeeById(int employeeId) {
        return employeeRepository.findById(employeeId)
                .map(EmployeeConversion::convertToTO)
                .orElse(null);
    }

    @Override
    @Transactional
    public EmployeeTO addEmployee(EmployeeTO employee) {
        if (employeeRepository.existsById(employee.getId())) {
            throw new IllegalArgumentException(
                    String.format("Employee with id = %d is already created",
                            employee.getId()));
        }
        return EmployeeConversion.convertToTO(saveEmployee(employee));
    }

    @Override
    @Transactional
    public EmployeeTO editEmployee(EmployeeTO employee) {
        if (!employeeRepository.existsById(employee.getId())) {
            throw new IllegalArgumentException(
                    String.format("Employee with id = %d is non created",
                            employee.getId()));
        }
        return EmployeeConversion.convertToTO(saveEmployee(employee));
    }


    private EmployeeDAO saveEmployee(EmployeeTO employeeTO) {
        PositionDAO positionDAO = getPositionDAO(employeeTO.getPositionId());
        EmployeeDAO employeeDAO = EmployeeConversion.convertToDTO(employeeTO, positionDAO);
        return employeeRepository.save(employeeDAO);
    }

    @Override
    @Transactional
    public void deleteEmployee(int employeeId) {
        employeeRepository.deleteById(employeeId);
    }

    private PositionDAO getPositionDAO(int positionId) {
        Optional<PositionDAO> positionDAO = positionRepository.findById(positionId);
        return positionDAO.orElseThrow(() -> new IllegalArgumentException(
                String.format("Position with id %d not found", positionId)
        ));
    }
}
