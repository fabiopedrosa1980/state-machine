package br.com.pedrosa.statemachine;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("orders")
public class OrdersController {
    private final OrderService orderService;

    public OrdersController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("new")
    String newOrder() {
        return orderService.newOrder();
    }

    @PostMapping("pay")
    String pay() {
        return orderService.payOrder();
    }

    @PostMapping("ship")
    String ship() {
        return orderService.shipOrder();
    }

    @PostMapping("complete")
    String complete() {
        return orderService.completeOrder();
    }

    @PostMapping("cancel")
    String cancel() {
        return orderService.cancelOrder();
    }

}
