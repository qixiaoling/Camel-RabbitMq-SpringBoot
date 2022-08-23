This is a project which uses only the package from RabbitMQ itself, e.g. import com.rabbitmq.client.ConnectionFactory, 
as well as import com.fasterxml.jackson.databind.ObjectMapper; to Deserialize JSON format into Obj and Serialize

Whereas the other project with "javaInUse" is a set of projects which includes a producer and a consumer.
it uses springboot amqp libary e.g. import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
and uses Jackson2JsonMessageConverter to do the serialization and deserialization.



