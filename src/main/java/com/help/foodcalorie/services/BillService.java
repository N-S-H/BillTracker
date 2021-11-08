package com.help.foodcalorie.services;

import com.help.foodcalorie.entities.Bill;
import com.help.foodcalorie.repositories.BillRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BillService {

    @Autowired
    BillRepository billRepository;


    public String insertBillItem(Bill bill) {
        String billNumber = bill.getBillNumber();
        if(billNumber==null) {
            return "The mandatory identifier field is absent in the bill inserted";
        } else if(billExistsById(billNumber)) {
            return "The bill with the given bill number: "+billNumber+" already exists. please enter the bill with different number";
        } else if(validBillDueDate(bill.getBilledDate())==false) {
            return "The bill due date format is incorrectly specified. The appropriate format is dd-MM-yyyy";
        } else if(calculateTotalBillPrice(bill)==false) {
            return "The bill tax and bill price is not specified";
        }
        else {
            billRepository.insert(bill);
            return "The bill with the bill number: "+billNumber+" is inserted successfully";
        }
    }

    private boolean calculateTotalBillPrice(Bill bill) {
        if(bill.getBillTax()==null||bill.getBilledPrice()==null) return false;
        bill.setTotalPrice(bill.getBilledPrice().doubleValue()+bill.getBillTax().doubleValue());
        return true;
    }

    private boolean validBillDueDate(String billedDate) {
        DateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy");
        if(billedDate==null) return false;
        try {
            dateFormat.parse(billedDate);
            return true;
        } catch (ParseException e) {
            log.error("Exception {} occured while parsing the date {}",e.getMessage(),billedDate);
            return false;
        }
    }

    public boolean billExistsById(String id) {
        if(billRepository.existsById(id)) return true;
        else return false;
    }

    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    public Optional<Bill> obtainBillById(String id) {
        if(billExistsById(id)==false) log.error("No bill exists with the given bill number: "+id);
        return billRepository.findById(id);
    }

    public List<Bill> getBillsOnDueDate(String date) {
        if(validBillDueDate(date)==false) {
            log.error("Invalid bill due date format specified. The appropriate format is dd-MM-yyyy");
            return null;
        }
        else {
            List<Bill> dueDateBills=new ArrayList<>();
            List<Bill> allBills=getAllBills();
            allBills.stream().forEach(bill-> {
                if(bill.getBilledDate().equals(date)) dueDateBills.add(bill);
            });
            return dueDateBills;
        }
    }
}
