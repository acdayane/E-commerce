package br.ada.customer.crud.usecases.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.ada.customer.crud.model.Order;
import br.ada.customer.crud.model.OrderItem;
import br.ada.customer.crud.model.OrderStatus;
import br.ada.customer.crud.model.Product;
import br.ada.customer.crud.usecases.IOrderItemUseCase;
import br.ada.customer.crud.usecases.repository.OrderRepository;

public class OrderItemUseCaseImpl implements IOrderItemUseCase {

    private OrderRepository repository;

    public OrderItemUseCaseImpl(OrderRepository repository) {
        this.repository = repository;
    }

     /*
     * 1 - Pedido precisa estar com status == OrderStatus.OPEN
     * 2 - Lembrar de atualizar o banco através do repository
     */
    private void checkOrderStatus(Order order) {
        if (order.getStatus() != OrderStatus.OPEN) {
            throw new RuntimeException("Pedido não está aberto");
        }
    }

    @Override
    public OrderItem addItem(Order order, Product product, BigDecimal price, Integer amount) {
        OrderItem newOrderItem = new OrderItem();

        checkOrderStatus(order);        
        
        newOrderItem.setProduct(product);
        newOrderItem.setSaleValue(price);
        newOrderItem.setAmount(amount);

        order.getItems().add(newOrderItem);        

        repository.update(order);

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

        checkOrderStatus(order);

        for (OrderItem item : orderItemList) {
            
            if (item.getProduct().getId().equals(product.getId())) {

                item.setAmount(amount);

                repository.update(order);

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
        List<OrderItem> orderItemList = order.getItems();
        List<OrderItem> updatedItemList = new ArrayList<>();
        
        checkOrderStatus(order);

        for (OrderItem item : orderItemList) {
            
            if (!item.getProduct().equals(product)) {

                updatedItemList.add(item);             

            } 
        }        

        order.setItems(updatedItemList);
        repository.update(order);       
    }
}
