package com.faizan.service;

import java.io.File;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.faizan.common.OrderRequestDTO;
import com.faizan.dto.ItemDTO;
import com.faizan.entity.Item;
import com.faizan.exception.BadRequestException;
import com.faizan.repository.BookingRepository;
import com.faizan.util.Helper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ItemBookingServiceImpl implements ItemBookingService {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    RestTemplate restTemplate;

    @Value("${microservice.order-service.endpoints.endpoint.uri}")
    private String ENDPOINT_URI; // = "http://PAYMENT-SERVICE/payment/order-payment";

    @Override
    public void addItem(String itemDto, MultipartFile file) {
        final boolean uploadfile = Helper.uploadfile(file);
        if (!uploadfile) {
            throw new BadRequestException("file not uploaded");
        }
        if (itemDto.equals("null")) {
            throw new BadRequestException("item json cant be null");
        }

        final ObjectMapper objectMapper = new ObjectMapper();
        ItemDTO itemDTO = null;
        try {
            itemDTO = objectMapper.readValue(itemDto, ItemDTO.class);
        } catch (final JsonProcessingException e) {
            throw new BadRequestException("Check the Item JSon");
        }
        Helper.isValidRequest(itemDTO);
        final Item item = Item.builder().itemName(itemDTO.getItemName()).itemType(itemDTO.getItemType()).itemDescription(itemDTO.getItemDescription()).itemPrice(itemDTO.getItemPrice()).quantity(itemDTO.getQuantity()).itemStatus(Item.ItemStatus.NOT_SOLD)
                .imageLocation(Helper.UPLOAD_DIR + File.separator + file.getOriginalFilename()).availableQty(itemDTO.getQuantity()).build();
        bookingRepository.save(item);
    }

    @Override
    public Optional<Item> getItem(Long id) {
        /*
         * final boolean flag = bookingRepository.existsById(id); if (!flag) { throw new BadRequestException("incorrect item id"); }
         */
        final Optional<Item> item = bookingRepository.findById(id);
        return item;

    }

    @Override
    public List<Item> getItems() {
        final Iterable<Item> items = bookingRepository.findAll();
        return (List<Item>) items;

    }

    @Override
    public void buyItem(Long itemId, Long qty) {
        final Optional<Item> item = bookingRepository.findById(itemId);
        if (qty <= 1 || item.get().getAvailableQty() < qty) {
            throw new BadRequestException("Quantity must be greater than 1");
        }

        System.out.println("item booking ********************************");
        if (Helper.isQuantityAvailable(item.get())) {
            System.out.println("************************888");
            item.get().setSoldQty(qty + item.get().getSoldQty());
            item.get().setAvailableQty(item.get().getQuantity() - item.get().getSoldQty());
            bookingRepository.save(item.get());
            final OrderRequestDTO order = new OrderRequestDTO();
            order.setItemId(item.get().getId());
            order.setQty(qty);
            order.setPrice(item.get().getItemPrice() * qty);
            order.setName(item.get().getItemName());

            final OrderRequestDTO orderResponse = restTemplate.postForObject(ENDPOINT_URI, order, OrderRequestDTO.class);
            if (orderResponse == null) {
                throw new BadRequestException("no response from order service");
            }

        } else {
            throw new BadRequestException("Item is not available");

        }

    }

}
