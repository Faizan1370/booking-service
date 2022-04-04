package com.faizan.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.faizan.entity.Item;

@Repository
public interface BookingRepository extends JpaRepository<Item, Long> {

    Page<Item> findByItemPrice(Double itemPrice, Pageable sortedByPriceDesc);

}
