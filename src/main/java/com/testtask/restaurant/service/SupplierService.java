package com.testtask.restaurant.service;

import com.testtask.restaurant.transfer.SupplierTO;

import java.util.List;

public interface SupplierService {

    List<SupplierTO> getSuppliers();

    SupplierTO getSupplierById(int supplierId);

    SupplierTO addSupplier(SupplierTO supplier);

    SupplierTO editSupplier(SupplierTO supplier);

    void deleteSupplier(int supplierId);

}
