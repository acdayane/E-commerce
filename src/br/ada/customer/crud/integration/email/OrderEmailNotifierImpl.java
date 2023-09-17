package br.ada.customer.crud.integration.email;

import br.ada.customer.crud.model.Order;
import br.ada.customer.crud.usecases.INotifierOrderUseCase;

public class OrderEmailNotifierImpl implements INotifierOrderUseCase {

    private SendEmail sendEmail;

    public OrderEmailNotifierImpl (SendEmail sendEmail) {
        this.sendEmail = sendEmail;
    }

    @Override
    public void pendingPayment(Order order) {
        sendEmail.send("comunicado@ada.com.br", order.getCustomer().getEmail(), "Estamos aguardando o pagamento do pedido");
    } 

    @Override
    public void notifyPayment(Order order) {
        sendEmail.send("comunicado@ada.com.br", order.getCustomer().getEmail(), "Uhul! Recebemos o pagamento!");
    }

    @Override
    public void shipping(Order order) {
        sendEmail.send("comunicado@ada.com.br", order.getCustomer().getEmail(), "Temos novidades! Seu pedido foi enviado!");
    } 
    
}
