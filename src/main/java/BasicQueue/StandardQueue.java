package BasicQueue;

import com.rabbitmq.client.*;
import org.apache.camel.processor.resequencer.Timeout;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Time;
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
        receiveQueue();

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
    private static void receiveQueue(){
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");

        try{
            connection = connectionFactory.newConnection();
            channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);

            Consumer consumer = new DefaultConsumer(channel){
              @Override
              public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                  String routingKey = envelope.getRoutingKey();
                  String contentType = properties.getContentType();
                  long deliveryTag = envelope.getDeliveryTag();
                  channel.basicAck(deliveryTag, false);
              }
            };
            int msgCount = getMessageCount(channel, QUEUE_NAME);

            int count = 0;

            while(count < msgCount) {
                channel.basicConsume(QUEUE_NAME, true, consumer);
                System.out.println(consumer.getClass());
                count++;
            }



        }catch(IOException | TimeoutException e){
            e.printStackTrace();
        }


    }

    private static int getMessageCount(Channel channel, String queueName) throws IOException{

            var result = channel.queueDeclare(queueName, true, false, false, null);
            return result.getMessageCount();


    }


}
