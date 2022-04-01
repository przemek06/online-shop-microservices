package com.example.demo.service;

import com.example.demo.dto.OrderResponseDto;
import com.example.demo.dto.ProductOrderDto;
import com.example.demo.dto.ProductTicketDto;
import com.example.demo.entity.Product;
import com.example.demo.entity.ProductOrder;
import com.example.demo.entity.ProductTicket;
import com.example.demo.repository.ProductOrderRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.TicketRepository;
import com.example.demo.utils.OrderUtils;
import com.example.demo.utils.TicketUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class OrderService {

    ProductRepository productRepository;
    ProductOrderRepository productOrderRepository;
    TicketRepository ticketRepository;

    public OrderService(ProductRepository productRepository, ProductOrderRepository productOrderRepository, TicketRepository ticketRepository) {
        this.productRepository = productRepository;
        this.productOrderRepository = productOrderRepository;
        this.ticketRepository = ticketRepository;
    }

    public List<ProductTicketDto> getTicketsInOrder(Long id){
        return ticketRepository.findAllByOrderRefId(id)
                .stream().map(TicketUtils::entityToDto)
                .collect(Collectors.toList());
    }

    public List<ProductOrderDto> getAllOrders(){
        return productOrderRepository.findAll()
                .stream().map(OrderUtils::entityToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteOrder(Long id){
        productOrderRepository.deleteById(id);
    }

    @Transactional
    public OrderResponseDto makeOrder(ProductOrderDto productOrderDto) {
        List<ProductTicketDto> tickets = productOrderDto.getTickets();
        if (!checkIfProductAvailable(tickets)) return buildNotAvailableResponse();
        ProductOrder productOrder = makeOrderTransactional(productOrderDto, tickets);
        return buildOkResponse(productOrder.getId());
    }

    @Transactional
    public ProductOrder makeOrderTransactional(ProductOrderDto productOrderDto, List<ProductTicketDto> tickets) {
        for (ProductTicketDto ticket : tickets) {
            updateAmountAvailable(ticket.getProductCode(), ticket.getProductAmount());
        }
        ProductOrder productOrder = saveProductOrder(productOrderDto);
        saveTicketList(tickets, productOrder);
        return productOrder;
    }

    private ProductOrder saveProductOrder(ProductOrderDto orderDto) {
        return productOrderRepository.save(OrderUtils.dtoToEntity(orderDto));
    }

    private void saveTicketList(List<ProductTicketDto> tickets, ProductOrder order) {
        List<ProductTicket> toSave = tickets.stream().map(TicketUtils::dtoToEntity)
                .collect(Collectors.toList());
        toSave.forEach(t -> t.setOrderRef(order));
        ticketRepository.saveAll(toSave);
    }

    @Transactional
    public void updateAmountAvailable(String code, Integer amount)  {
        int updated = productRepository.updateProductAmount(amount, code);
        if(updated<=0) throw new RuntimeException();
        Product updatedProduct = productRepository.getById(code);
        if(updatedProduct.getAmountAvailable()<0) throw new RuntimeException();
    }

    private Boolean checkIfProductAvailable(List<ProductTicketDto> tickets) {
        for (ProductTicketDto ticket : tickets) {
            Optional<Product> optional = productRepository.findById(ticket.getProductCode());
            if (optional.isEmpty()) return false;
            Product product = optional.get();
            if (product.getAmountAvailable() < ticket.getProductAmount()) return false;
        }
        return true;
    }

    public static OrderResponseDto buildNotAvailableResponse() {
        return new OrderResponseDto("Some products are not available.", null);
    }


    private OrderResponseDto buildOkResponse(Long id){
        return new OrderResponseDto("Order in realization!", id);
    }
}
