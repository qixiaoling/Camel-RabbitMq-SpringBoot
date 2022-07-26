package BasicQueue;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Publisher {

    private final static String QUEUE_NAME = "StandardQueue_ExampleQueue";





    public static void main(String[] args) {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        try{
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);

            Payment payment1 = new Payment("1234567", 25.00, "Mr S Hunts");
            Payment payment2 = new Payment("6789012", 5.00, " Mr S Hunts");


            channel.basicPublish("", QUEUE_NAME, null, ObjectSerialize.Serialize(payment1));
            System.out.println("[x] Payment Message Sent : " + payment1.getCardNumber() + " " + payment1.getPaymentAmount() + " " + payment1.getName());


        }catch(IOException | TimeoutException e){
            e.printStackTrace();
        }


    }


}
