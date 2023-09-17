package br.ada.customer.crud.usecases.impl;

import br.ada.customer.crud.model.Order;
import br.ada.customer.crud.model.OrderStatus;
import br.ada.customer.crud.usecases.INotifierOrderUseCase;
import br.ada.customer.crud.usecases.IOrderPayUseCase;
import br.ada.customer.crud.usecases.repository.OrderRepository;

public class OrderPayUseCaseImpl implements IOrderPayUseCase {

    private OrderRepository orderRepository;
    private INotifierOrderUseCase paymentNotifier;
    

    public OrderPayUseCaseImpl(
        OrderRepository repository,
        INotifierOrderUseCase notifier
    ) {
        this.orderRepository = repository;
        this.paymentNotifier = notifier;
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

        paymentNotifier.notifyPayment(order);
    }

    private void checkOrderStatus(Order order) {
        if (order.getStatus() != OrderStatus.PENDING_PAYMENT) {
            throw new RuntimeException("Pedido não está aberto");
        }
    }
    
}
