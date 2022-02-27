package com.faizan.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.faizan.entity.Item;

@Repository
public interface BookingRepository extends CrudRepository<Item, Long> {

}
