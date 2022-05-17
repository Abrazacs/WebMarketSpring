package ru.sergeysemenov.webmarketspring.core.properties;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "integrations.cart-service")
@Getter
@Setter
public class CartServiceIntegrationProperties {
    private String url;
    private Integer connectionTimeout;
    private Integer readTimeout;
    private Integer writeTimeout;
}
