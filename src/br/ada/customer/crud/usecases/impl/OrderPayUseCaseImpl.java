package br.ada.customer.crud.usecases.impl;

import br.ada.customer.crud.model.Order;
import br.ada.customer.crud.model.OrderStatus;
import br.ada.customer.crud.usecases.INotifierOrderUseCase;
import br.ada.customer.crud.usecases.IOrderPayUseCase;
import br.ada.customer.crud.usecases.repository.OrderRepository;

public class OrderPayUseCaseImpl implements IOrderPayUseCase {

    private OrderRepository orderRepository;
    private INotifierOrderUseCase notifierEmail;
    private INotifierOrderUseCase notifierSms;

    public OrderPayUseCaseImpl(OrderRepository repository, INotifierOrderUseCase notifierEmail, INotifierOrderUseCase notifierSms) {
        this.orderRepository = repository;
        this.notifierEmail = notifierEmail;
        this.notifierSms = notifierSms;
    }

     /*
     * 1 - Pedido precisa estar com status == OrderStatus.PENDING_PAYMENT
     * 2 - Pedido deve passar a ter o status igual a OrderStatus.PAID
     * 3 - Notificar o cliente sobre o pagamento com sucesso
     */

    @Override
    public void pay(Order order) {
        checkOrderStatus(order);
    
        order.setStatus(OrderStatus.PAID);

        orderRepository.update(order);

        notifierEmail.notifyPayment(order);
        notifierSms.notifyPayment(order);
    }

    private void checkOrderStatus(Order order) {
        if (order.getStatus() != OrderStatus.PENDING_PAYMENT) {
            throw new RuntimeException("Pedido não está com pagamento pendente");
        }
    }
    
}
