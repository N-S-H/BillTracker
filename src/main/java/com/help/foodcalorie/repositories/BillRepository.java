package com.help.foodcalorie.repositories;

import com.help.foodcalorie.entities.Bill;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BillRepository extends MongoRepository<Bill,String> {
}
