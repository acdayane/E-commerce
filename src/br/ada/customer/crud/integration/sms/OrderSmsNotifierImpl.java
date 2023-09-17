package br.ada.customer.crud.integration.sms;

import br.ada.customer.crud.model.Order;
import br.ada.customer.crud.usecases.INotifierOrderUseCase;

public class OrderSmsNotifierImpl implements INotifierOrderUseCase {

    private SendSms sendSms;

    public OrderSmsNotifierImpl (SendSms sendSms) {
        this.sendSms = sendSms;
    }

    @Override
    public void pendingPayment(Order order) {
        sendSms.send("+351", order.getCustomer().getTelephone(), "Estamos aguardando o pagamento do pedido " + order.getId());
    }

    @Override
    public void notifyPayment(Order order) {
        sendSms.send("+351", order.getCustomer().getTelephone(), "Uhull! Recebemos o pagamento do pedido " + order.getId());
    }

    @Override
    public void shipping(Order order) {
        sendSms.send("+351", order.getCustomer().getTelephone(), "Obaaa! Seu pedido " + order.getId() + " foi enviado!");
    }
    
}
