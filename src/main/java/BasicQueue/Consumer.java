package BasicQueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import jdk.swing.interop.SwingInterOpUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.TimeoutException;

public class Consumer {

    private final static String QUEUE_NAME = "StandardQueue_ExampleQueue";





    public static void main(String[] args) {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        try{
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String msg = new String(delivery.getBody(), "UTF-8");
                System.out.println("[X] message received " + msg + LocalDateTime.now());
            };

            channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTage -> {});


        }catch(IOException | TimeoutException e){
            e.printStackTrace();
        }


    }


}
