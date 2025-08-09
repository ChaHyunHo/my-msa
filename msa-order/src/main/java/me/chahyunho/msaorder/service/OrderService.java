package me.chahyunho.msaorder.service;
import me.chahyunho.msaorder.model.Order;
import me.chahyunho.msaorder.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public void createOrder(Order order) {
        orderRepository.save(order);
    }

    @Transactional
    public void updateOrder(Long orderId) {
        Order orderToUpdate = getOrderById(orderId);
        orderToUpdate.setStatus("SHIPPED");
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        Order orderToCancel = getOrderById(orderId);
        orderToCancel.setStatus("CANCELLED");
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }
}
