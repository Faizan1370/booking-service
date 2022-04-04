package com.faizan.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.faizan.entity.Item;

public interface ItemBookingService {

    void addItem(String itemDTO, MultipartFile file);

    Optional<Item> getItem(Long id);

    Page<Item> getItems(Integer pageNumber, Integer numberOfRecord, String sortBy);

    void buyItem(Long itemId, Long qty);

}
