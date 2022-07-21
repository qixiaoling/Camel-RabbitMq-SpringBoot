package BasicQueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class StandardQueue {
    private final static String QUEUE_NAME = "StandardQueue_ExampleQueue";
    private static ConnectionFactory connectionFactory;
    private static Connection connection;
    private static Channel channel;




    public static void main(String[] args) {
        Payment payment1 = new Payment("1234567", 25.00, "Mr S Hunts");
        Payment payment2 = new Payment("6789012", 5.00, " Mr S Hunts");



        createQueue();
        sendMessage(payment1);
        sendMessage(payment2);

    }
    private static void createQueue(){
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");

        try{
            connection = connectionFactory.newConnection();
            channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);

        }catch (IOException | TimeoutException e){
            e.printStackTrace();
        }

    }
    private static void sendMessage(Payment payment){
        try {
            channel.basicPublish("", QUEUE_NAME, null, ObjectSerialize.Serialize(payment));
            System.out.println("[x] Payment Message Sent : " + payment.getCardNumber() + payment.getPaymentAmount() + payment.getName());
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
