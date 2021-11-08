package com.help.foodcalorie.controllers;

import com.help.foodcalorie.entities.Bill;
import com.help.foodcalorie.services.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
@CrossOrigin
public class BillController {

    @Autowired
    BillService billService;

    @PostMapping("/bill")
    public String insert(@RequestBody Bill bill)
    {
        String insertedBillItemStatus=billService.insertBillItem(bill);
        return insertedBillItemStatus;
    }

    @GetMapping("/bills")
    public List<Bill> getAllBills()
    {
        return billService.getAllBills();
    }

    @GetMapping("/bills/{id}")
    public Optional<Bill> getBillWithId(@PathVariable("id") String id) {
        return billService.obtainBillById(id);
    }

    @GetMapping("/bills/due/{date}")
    public List<Bill> getBillsOnDueDate(@PathVariable("date") String date) {
        return billService.getBillsOnDueDate(date);
    }

}
