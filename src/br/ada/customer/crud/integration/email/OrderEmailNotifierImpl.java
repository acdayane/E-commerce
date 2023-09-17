package br.ada.customer.crud.integration.email;

import br.ada.customer.crud.model.Order;
import br.ada.customer.crud.usecases.INotifierPaymentUseCase;

public class OrderEmailNotifierImpl implements INotifierPaymentUseCase {

    private SendEmail sendEmail;

    public OrderEmailNotifierImpl (SendEmail sendEmail) {
        this.sendEmail = sendEmail;
    }

    @Override
    public void notify(Order order) {
        sendEmail.send("comunicado@ada.com.br", order.getCustomer().getEmail(), "Uhul! Recebemos o pagamento!");
    }
    
}
