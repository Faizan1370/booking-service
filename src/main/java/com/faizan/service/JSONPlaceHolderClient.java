package com.faizan.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.faizan.common.OrderRequestDTO;

@FeignClient("ORDER-SERVICE")
public interface JSONPlaceHolderClient {
    @RequestMapping(method = RequestMethod.GET, value = "/posts")
    List<OrderRequestDTO> getPosts();

    @RequestMapping(method = RequestMethod.POST, value = "/order/book", produces = "application/json")
    OrderRequestDTO orderItem(@RequestBody OrderRequestDTO orderRequestDTO);

}
