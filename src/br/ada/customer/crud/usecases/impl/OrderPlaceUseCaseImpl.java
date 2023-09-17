package br.ada.customer.crud.usecases.impl;

import java.math.BigDecimal;

import br.ada.customer.crud.model.Order;
import br.ada.customer.crud.model.OrderItem;
import br.ada.customer.crud.model.OrderStatus;
import br.ada.customer.crud.usecases.INotifierOrderUseCase;
import br.ada.customer.crud.usecases.IOrderPlaceUseCase;
import br.ada.customer.crud.usecases.repository.OrderRepository;

public class OrderPlaceUseCaseImpl implements IOrderPlaceUseCase {

    private OrderRepository orderRepository;
    private INotifierOrderUseCase notifierEmail;
    private INotifierOrderUseCase notifierSms;

    public OrderPlaceUseCaseImpl(OrderRepository repository, INotifierOrderUseCase notifierEmail, INotifierOrderUseCase notifierSms) {
        this.orderRepository = repository;
        this.notifierEmail = notifierEmail;
        this.notifierSms = notifierSms;
    }

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

        orderRepository.update(order);

        notifierEmail.pendingPayment(order);
        notifierSms.pendingPayment(order);
    }
    
}
