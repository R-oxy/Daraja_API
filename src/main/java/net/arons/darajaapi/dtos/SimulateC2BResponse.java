package net.arons.darajaapi.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SimulateC2BResponse{

	@JsonProperty("ResponseCode")
	private String responseCode;

	@JsonProperty("OriginatorCoversationID")
	private String originatorCoversationID;

	@JsonProperty("ResponseDescription")
	private String responseDescription;

}