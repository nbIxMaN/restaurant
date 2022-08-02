package com.testtask.restaurant.service;

import com.testtask.restaurant.convertion.PositionConversion;
import com.testtask.restaurant.entity.PositionDAO;
import com.testtask.restaurant.repository.PositionRepository;
import com.testtask.restaurant.transfer.PositionTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PositionServiceImpl implements PositionService {

    private final PositionRepository positionRepository;

    @Autowired
    public PositionServiceImpl(PositionRepository positionRepository){
        this.positionRepository = positionRepository;
    }

    @Override
    @Transactional
    public List<PositionTO> getPositions() {
        return positionRepository.findAll()
                .stream()
                .map(PositionConversion::convertToTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PositionTO getPositionById(int positionId) {
        return positionRepository.findById(positionId)
                .map(PositionConversion::convertToTO)
                .orElse(null);
    }

    @Override
    @Transactional
    public PositionTO addPosition(PositionTO position) {
        if (positionRepository.existsByIdOrName(position.getId(), position.getName())) {
            throw new IllegalArgumentException(
                    String.format("Position with id = %d or name = %s is already created",
                            position.getId(),
                            position.getName()));
        }
        return PositionConversion.convertToTO(savePosition(position));
    }

    @Override
    @Transactional
    public PositionTO editPosition(PositionTO position) {
        if (!positionRepository.existsById(position.getId())) {
            throw new IllegalArgumentException(
                    String.format("ingredient with id = %d is non created",
                            position.getId()));
        }
        //Checking if another id has same name
        if (positionRepository.existsByNameAndIdNot(position.getName(), position.getId())) {
            throw new IllegalArgumentException(
                    String.format("ingredient with name = %s is already created",
                            position.getName()));
        }
        return PositionConversion.convertToTO(savePosition(position));
    }

    public PositionDAO savePosition(PositionTO position) {
        return positionRepository.save(PositionConversion.convertToDTO(position));
    }

    @Override
    @Transactional
    public void deletePosition(int positionId) {
        positionRepository.deleteById(positionId);
    }
}
