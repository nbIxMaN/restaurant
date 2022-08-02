package com.testtask.restaurant.service;

import com.testtask.restaurant.transfer.CheckTO;

import java.util.List;

public interface CheckService {

    List<CheckTO> getChecks();

    CheckTO getCheckById(int checkId);

    CheckTO addCheck(CheckTO check);

    CheckTO editCheck(CheckTO check);

    void deleteCheck(int checkId);

}
