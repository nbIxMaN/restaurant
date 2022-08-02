package com.testtask.restaurant.controler;


import com.testtask.restaurant.service.CheckService;
import com.testtask.restaurant.transfer.CheckTO;
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
@RequestMapping("/check")
@Tag(name = "Check", description = "Check API")
public class CheckController {

    private final CheckService checkService;

    @Autowired
    public CheckController(CheckService checkService) {
        this.checkService = checkService;
    }

    @GetMapping("/")
    @Operation(summary = "Get checks")
    public List<CheckTO> getChecks(){
        return checkService.getChecks();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a check by id")
    public CheckTO getCheckById(@PathVariable("id") int checkId){
        return checkService.getCheckById(checkId);
    }

    @PostMapping("/")
    @Operation(summary = "Add a new check")
    public CheckTO addCheck(@RequestBody CheckTO checkTO) {
        return checkService.addCheck(checkTO);
    }

    @PutMapping("/")
    @Operation(summary = "Edit an existing check")
    public CheckTO editCheck(@RequestBody CheckTO checkTO) {
        return checkService.editCheck(checkTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove check by id")
    public void removeCheck(@PathVariable("id") int checkId) {
        checkService.deleteCheck(checkId);
    }

}
