package com.testtask.restaurant.controler;


import com.testtask.restaurant.service.SupplierService;
import com.testtask.restaurant.transfer.SupplierTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/supplier")
@Tag(name = "Supplier", description = "Ingredient supplier API")
public class SupplierController {

    private final SupplierService supplierService;

    @Autowired
    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping("/")
    @Operation(summary = "Get ingredient suppliers")
    public List<SupplierTO> getSuppliers(){
        return supplierService.getSuppliers();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a ingredient supplier by id")
    public SupplierTO getSuppliers(@PathVariable("id") int supplierId){
        return supplierService.getSupplierById(supplierId);
    }

    @PostMapping("/")
    @Operation(summary = "Add a new ingredient supplier")
    public SupplierTO addSupplier(@RequestBody SupplierTO supplierTO) {
        return supplierService.addSupplier(supplierTO);
    }

    @PutMapping("/")
    @Operation(summary = "Edit an existing ingredient supplier")
    public SupplierTO editSupplier(@RequestBody SupplierTO supplierTO) {
        return supplierService.editSupplier(supplierTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove ingredient supplier by id")
    public void removeSupplier(@PathVariable("id") int supplierId) {
        supplierService.deleteSupplier(supplierId);
    }

}
