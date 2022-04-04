package com.faizan.bookingcontroller;

import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.faizan.entity.Item;
import com.faizan.service.ItemBookingService;
import com.faizan.util.Helper;

@RestController
@RequestMapping("/booking/service")
public class ItemController {

    @Autowired
    ItemBookingService itemBookingService;
    Logger logger = LoggerFactory.getLogger(ItemController.class);

    @PostMapping("/add/item")
    public ResponseEntity<?> addItem(@RequestPart("item") String itemDTO, @RequestPart("file") MultipartFile file) {
        System.out.println("item json :" + itemDTO);
        itemBookingService.addItem(itemDTO, file);
        return ResponseEntity.ok("Item Added Successfully");

    }

    @GetMapping("/item")
    public ResponseEntity<?> getItem(@RequestParam Long id) {
        logger.info("inside the get item method");
        final Optional<Item> item = itemBookingService.getItem(id);
        if (!item.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no such data found for the item id :" + id);
        }
        return ResponseEntity.ok(item);

    }

    @GetMapping("/items/{pageNumber}/{numberOfRecord}/{sortBy}")
    public ResponseEntity<?> getItems(@PathVariable("pageNumber") Integer pageNumber, @PathVariable("numberOfRecord") Integer numberOfRecord, @PathVariable("sortBy") String sortBy) {
        final Page<Item> items = itemBookingService.getItems(pageNumber, numberOfRecord, sortBy);
        return ResponseEntity.ok(items.get().collect(Collectors.toList()));

    }

    @PostMapping("/upload-file")
    public ResponseEntity<?> fileUpload(@RequestParam("file") MultipartFile file) {
        final boolean uploaded = Helper.uploadfile(file);
        if (uploaded) {
            return ResponseEntity.ok("File uploaded");
        }
        /*
         * System.out.println(file.getOriginalFilename()); System.out.println(file.getContentType()); System.out.println(file.getName());
         */
        return ResponseEntity.ok("working");

    }

    @PutMapping("/buy-item/{id}/{qty}")
    public ResponseEntity<?> buyItem(@PathVariable("id") Long itemId, @PathVariable("qty") Long qty) {
        itemBookingService.buyItem(itemId, qty);
        return ResponseEntity.ok("Item Successfully Purchased");

    }

}
