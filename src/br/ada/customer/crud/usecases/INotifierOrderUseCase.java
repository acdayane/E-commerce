package br.ada.customer.crud.usecases;

import br.ada.customer.crud.model.Order;

public interface INotifierOrderUseCase {

    void pendingPayment(Order order);

    void notifyPayment(Order order);

    void shipping(Order order);
    
}
