package net.arons.darajaapi.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RegisterUrlResponse {

    @JsonProperty("ConversationID")
    private String conversationID;

    @JsonProperty("ResponseDescription")
    private String responseDescription;

    @JsonProperty("OriginatorConversationID")
    private String originatorConversationID;

}
