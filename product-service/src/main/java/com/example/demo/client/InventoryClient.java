package com.example.demo.client;

import com.example.demo.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("inventory-service")
public interface InventoryClient {

    @RequestMapping(method = RequestMethod.GET,
            value = "/api/inventory/{code}")
    ProductDto getProduct(@PathVariable String code);

    @RequestMapping(method = RequestMethod.GET,
            value = "/api/inventory/",
            produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    List<ProductDto> getAllProducts();

    @RequestMapping(method = RequestMethod.POST,
        value = "/api/inventory/",
        consumes = MimeTypeUtils.APPLICATION_JSON_VALUE)
    void saveProduct(@RequestBody ProductDto productDto);

    @RequestMapping(method = RequestMethod.DELETE,
            value = "/api/inventory/{code}")
    void deleteProduct(@PathVariable String code);


}
