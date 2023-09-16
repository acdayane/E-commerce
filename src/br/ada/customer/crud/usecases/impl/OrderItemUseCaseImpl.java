package br.ada.customer.crud.usecases.impl;

import java.math.BigDecimal;
import java.util.List;

import br.ada.customer.crud.model.Order;
import br.ada.customer.crud.model.OrderItem;
import br.ada.customer.crud.model.OrderStatus;
import br.ada.customer.crud.model.Product;
import br.ada.customer.crud.usecases.INotifierUserCase;
import br.ada.customer.crud.usecases.IOrderItemUseCase;
import br.ada.customer.crud.usecases.repository.OrderRepository;

public class OrderItemUseCaseImpl implements IOrderItemUseCase {

    private OrderRepository orderRepository;
    private INotifierUserCase<Order> orderNotifier;

    public OrderItemUseCaseImpl(OrderRepository repository, INotifierUserCase<Order> notifier) {
        this.orderRepository = repository;
        this.orderNotifier = notifier;
    }

     /*
     * 1 - Pedido precisa estar com status == OrderStatus.OPEN
     * 2 - Lembrar de atualizar o banco através do repository
     */
    @Override
    public OrderItem addItem(Order order, Product product, BigDecimal price, Integer amount) {

        OrderItem newOrderItem = new OrderItem();
                
        if (order.getStatus() != OrderStatus.OPEN) {
            throw new RuntimeException("Pedido não está aberto");
        }

        newOrderItem.setProduct(product);
        newOrderItem.setSaleValue(price);
        newOrderItem.setAmount(amount);

        order.getItems().add(newOrderItem);        

        orderRepository.save(order);
        orderNotifier.registered(order);

        return newOrderItem;
        
    }

     /*
     * 1 - Pedido precisa estar com status == OrderStatus.OPEN
     * 2 - Trocar a quantidade que foi vendida desse produto
     * 3 - Lembrar de atualizar o banco através do repository
     */
    @Override
    public OrderItem changeAmount(Order order, Product product, Integer amount) {
        List<OrderItem> orderItemList = order.getItems();
        OrderItem changeAmoItem = null;

        if (order.getStatus() != OrderStatus.OPEN) {
            throw new RuntimeException("Pedido não está aberto");
        }

        for (OrderItem item : orderItemList) {
            
            if (item.getId().equals(product.getId())) {

                item.setAmount(amount);

                orderRepository.update(order);
                orderNotifier.updated(order);

                changeAmoItem = item;
            }
        }  
       
        return changeAmoItem;
    }

    
    /*
     * 1 - Pedido precisa estar com status == OrderStatus.OPEN
     * 2 - Lembrar de atualizar o banco através do repository
     */
    @Override
    public void removeItem(Order order, Product product) {
       
    }
    
}
