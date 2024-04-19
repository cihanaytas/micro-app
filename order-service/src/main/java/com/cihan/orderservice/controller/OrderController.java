package com.cihan.orderservice.controller;

import com.cihan.orderservice.client.InventoryClient;
import com.cihan.orderservice.client.ProductClient;
import com.cihan.orderservice.dto.OrderDto;
import com.cihan.orderservice.model.Order;
import com.cihan.orderservice.model.OrderLineItems;
import com.cihan.orderservice.repository.OrderRepository;
import com.cihan.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreaker;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.function.Supplier;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final Resilience4JCircuitBreakerFactory circuitBreakerFactory;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    InventoryClient inventoryClient;

    @Autowired
    ProductClient productClient;

    private final ExecutorService traceableExecutorService;

    private final StreamBridge streamBridge;

    @PostMapping
    public String placeOrder(@RequestBody OrderDto orderDto){
      //  circuitBreakerFactory.configureExecutorService(traceableExecutorService);
        Resilience4JCircuitBreaker circuitBreaker = circuitBreakerFactory.create("inventory");
        Supplier<Boolean> booleanSupplier = () -> orderDto.getOrderLineItemsList().stream()
                .allMatch(lineItem -> inventoryClient.checkStock(lineItem.getSkuCode()));

        boolean productInStock = circuitBreaker.run(booleanSupplier, throwable -> handleErrorCase());

       boolean allProductsInStock = false;

       allProductsInStock = orderDto.getOrderLineItemsList().stream()
                .allMatch(orderLineItems -> inventoryClient.checkStock(orderLineItems.getSkuCode()));

        if (allProductsInStock) {

            Order order = new Order();
            order.setOrderLineItems(orderDto.getOrderLineItemsList());
            order.setOrderNumber(UUID.randomUUID().toString());

            orderRepository.save(order);
            log.info("Sending Order Details with Order Id {} to Notification Service", order.getId());
            streamBridge.send("notificationEventSupplier-out-0", MessageBuilder.withPayload(order.getId()).build());
            return "Order Place Successfully";
        } else {
            return "Order failed, not in stock.";
        }
    }


    @Autowired
    public OrderService orderService;

//    @PostMapping
//    @Transactional
//    public String placeOrder(@RequestBody OrderDto orderDto) throws Exception {
//        //  circuitBreakerFactory.configureExecutorService(traceableExecutorService);
////        Resilience4JCircuitBreaker circuitBreaker = circuitBreakerFactory.create("inventory");
////        Supplier<Boolean> booleanSupplier = () -> orderDto.getOrderLineItemsList().stream()
////                .allMatch(lineItem -> inventoryClient.checkStock(lineItem.getSkuCode()));
////
////        boolean productInStock = circuitBreaker.run(booleanSupplier, throwable -> handleErrorCase());
////
////        boolean allProductsInStock = false;
////
////        allProductsInStock = orderDto.getOrderLineItemsList().stream()
////                .allMatch(orderLineItems -> inventoryClient.checkStock(orderLineItems.getSkuCode()));
//
//        boolean allProductsInStock = true;
//        if (allProductsInStock) {
//
//            Order order = new Order();
//            order.setOrderLineItems(orderDto.getOrderLineItemsList());
//            order.setOrderNumber(UUID.randomUUID().toString());
//
//            orderService.save(order);
//            System.out.println("Kaydetti");
//            Thread.sleep(5000);
//            x();
//
//
////            log.info("Sending Order Details with Order Id {} to Notification Service", order.getId());
////            streamBridge.send("notificationEventSupplier-out-0", MessageBuilder.withPayload(order.getId()).build());
//            return "Order Place Successfully";
//        } else {
//            return "Order failed, not in stock.";
//        }
//    }

    public void x(){
        throw new RuntimeException("Hata");
    }


//    @GetMapping
//    public Boolean a(){
//        return inventoryClient.a();
//    }

    @GetMapping
    public String aa(){
        return "productClient.a()";
    }





//    @PostMapping
//    public String placeOrder(@RequestBody SpringDataJaxb.OrderDto orderDto) {
//        circuitBreakerFactory.configureExecutorService(traceableExecutorService);
//        Resilience4JCircuitBreaker circuitBreaker = circuitBreakerFactory.create("inventory");
//        java.util.function.Supplier<Boolean> booleanSupplier = () -> orderDto.getOrderLineItemsList().stream()
//                .allMatch(lineItem -> {
//                    log.info("Making Call to Inventory Service for SkuCode {}", lineItem.getSkuCode());
//                    return inventoryClient.checkStock(lineItem.getSkuCode());
//                });
//        boolean productsInStock = circuitBreaker.run(booleanSupplier, throwable -> handleErrorCase());
//
//        if (productsInStock) {
//            Order order = new Order();
//            order.setOrderLineItems(orderDto.getOrderLineItemsList());
//            order.setOrderNumber(UUID.randomUUID().toString());
//
//            orderRepository.save(order);
//            log.info("Sending Order Details with Order Id {} to Notification Service", order.getId());
//            streamBridge.send("notificationEventSupplier-out-0", MessageBuilder.withPayload(order.getId()).build());
//            return "Order Place Successfully";
//        } else {
//            return "Order Failed - One of the Product in your Order is out of stock";
//        }
//    }

    private Boolean handleErrorCase() {
        return false;
    }
}