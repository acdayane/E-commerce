package br.ada.customer.crud.usecases.impl;

import java.math.BigDecimal;

import br.ada.customer.crud.model.Order;
import br.ada.customer.crud.model.OrderItem;
import br.ada.customer.crud.model.OrderStatus;
import br.ada.customer.crud.usecases.IOrderPlaceUseCase;

public class OrderPlaceUseCaseImpl implements IOrderPlaceUseCase {

    @Override
    public void placeOrder(Order order) {
        BigDecimal sum = BigDecimal.ZERO;

        if (order.getStatus() != OrderStatus.OPEN) {
            throw new RuntimeException("Pedido não está aberto");
        }

        if (order.getItems() == null || order.getItems().isEmpty()) { //não existe carrinho e carrinho vazio
            throw new RuntimeException("Não tem item");
        }

        for (OrderItem item : order.getItems()) {
            sum = sum.add(item.getSaleValue());
        }

        if (sum.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Soma dos produtos igual ou menor que 0");
        }

        order.setStatus(OrderStatus.PENDING_PAYMENT);
    }
    
}
