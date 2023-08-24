package net.arons.darajaapi.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RegisterUrlRequest {

    @JsonProperty("ShortCode")
    private String shortCode;

    @JsonProperty("ConfirmationUrl")
    private String confirmationUrl;

    @JsonProperty("ValidationUrl")
    private String validationUrl;

    @JsonProperty("ResponseType")
    private String responseType;


}
