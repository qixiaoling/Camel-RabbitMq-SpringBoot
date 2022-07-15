package com.capgemini.rabbitmq.routes;

import com.capgemini.rabbitmq.dto.WeatherDto;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.support.DefaultMessage;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class WeatherRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("rabbitmq:amq.direct?queue=weather&routingKey=weather&autoDelete=false")
                .log(LoggingLevel.ERROR, "Got this message from Rabbit: ${body}")
                .unmarshal().json(JsonLibrary.Jackson, WeatherDto.class)
                .process(this::enrichWeatherDto)
                .log(LoggingLevel.ERROR, "After the enrichment: ${body}")
                .marshal().json(JsonLibrary.Jackson, WeatherDto.class)
                .to("rabbitmq:amq.direct?queue=weather-event&routingKey=weather-event&autoDelete=false")

        ;
    }

    public void enrichWeatherDto(Exchange exchange){
        WeatherDto dto = exchange.getMessage().getBody(WeatherDto.class);
        dto.setReceivedTime(new Date().toString());

        Message message = new DefaultMessage(exchange);
        message.setBody(dto);
        exchange.setMessage(message);
    }

}
