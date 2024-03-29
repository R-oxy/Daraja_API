package net.arons.darajaapi.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "mpesa.daraja")
public class MpesaConfiguration {

    private String consumerKey;
    private String consumerSecret;
    private String grantType;
    private String oauthEndpoint;
    private String confirmationUrl;
    private String validationUrl;
    private String shortCode;
    private String responseType;
    private String registerUrlEndpoint;
    private String simulateTransactionEndpoint;

    @Override
    public String toString(){
        return String.format("{consumerKey='%s', consumerSecret='%s', grantType='%s', oauthEndpoint='%s'}",
                consumerKey, consumerSecret, grantType, oauthEndpoint);
    }
}
