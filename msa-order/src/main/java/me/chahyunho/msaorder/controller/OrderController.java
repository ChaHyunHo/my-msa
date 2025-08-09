package me.chahyunho.msaorder.controller;

import me.chahyunho.msaorder.event.OrderEventPublisher;
import me.chahyunho.msaorder.model.Order;
import me.chahyunho.msaorder.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderEventPublisher orderEventPublisher;

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody Order order) {
        order.setStatus("PENDING");
        orderService.createOrder(order);
        orderEventPublisher.publishOrderCreated(order.getId());

        return ResponseEntity.ok("Order Created: " + order.getId().toString());
    }

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<String> cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        orderEventPublisher.publishOrderCancel(orderId);

        return ResponseEntity.ok("Order Created: " + orderId.toString());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }
}
