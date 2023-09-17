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
        System.out.println();
        sendEmail.send("comunicado@ada.com.br", order.getCustomer().getEmail(), "Estamos aguardando o pagamento do pedido " + order.getId());
    } 

    @Override
    public void notifyPayment(Order order) {
        System.out.println();
        sendEmail.send("comunicado@ada.com.br", order.getCustomer().getEmail(), "Uhull! Recebemos o pagamento do pedido " + order.getId());
    }

    @Override
    public void shipping(Order order) {
        System.out.println();
        sendEmail.send("comunicado@ada.com.br", order.getCustomer().getEmail(), "Obaaa! Seu pedido " + order.getId() + " foi enviado!");
    } 
    
}
