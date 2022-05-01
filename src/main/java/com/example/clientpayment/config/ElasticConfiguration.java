package com.example.clientpayment.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

@Configuration
public class ElasticConfiguration extends AbstractElasticsearchConfiguration {
    @Override
    public RestHighLevelClient elasticsearchClient() {
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo("client-payment-3186eb.es.us-central1.gcp.cloud.es.io:9243")
                .usingSsl()
                .withBasicAuth("elastic","X2jVrZnqu2WLWzB1ToyyANzY")
                .build();
        return RestClients.create(clientConfiguration).rest();
    }
}
