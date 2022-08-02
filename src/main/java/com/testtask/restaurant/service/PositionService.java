package com.testtask.restaurant.service;

import com.testtask.restaurant.transfer.PositionTO;

import java.util.List;

public interface PositionService {

    List<PositionTO> getPositions();

    PositionTO getPositionById(int positionId);

    PositionTO addPosition(PositionTO position);

    PositionTO editPosition(PositionTO position);

    void deletePosition(int positionId);

}
