package com.faizan.service;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.faizan.entity.Item;

public interface ItemBookingService {

    void addItem(String itemDTO, MultipartFile file);

    Optional<Item> getItem(Long id);

    List<Item> getItems();

    void buyItem(Long itemId, Long qty);

}
