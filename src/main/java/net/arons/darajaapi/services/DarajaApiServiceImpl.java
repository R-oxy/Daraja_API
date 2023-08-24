package net.arons.darajaapi.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.arons.darajaapi.config.MpesaConfiguration;
import net.arons.darajaapi.dtos.*;
import net.arons.darajaapi.utils.HelperUtility;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.stereotype.Service;
import okhttp3.OkHttpClient;

import java.io.IOException;
import java.util.Objects;

import static net.arons.darajaapi.utils.Constants.*;

@Service
@Slf4j
public class DarajaApiServiceImpl implements DarajaApiService{

    private final MpesaConfiguration mpesaConfiguration;
    private final OkHttpClient okHttpClient;
    private final ObjectMapper objectMapper;

    public DarajaApiServiceImpl(MpesaConfiguration mpesaConfiguration, OkHttpClient okHttpClient, ObjectMapper objectMapper) {
        this.mpesaConfiguration = mpesaConfiguration;
        this.okHttpClient = okHttpClient;
        this.objectMapper = objectMapper;
    }

    /**
     * @return Returns Daraja API Access Token Response
     */
    @Override
    public AccessTokenResponse getAccessToken() {

        // get the Base64 rep of consumerKey + ":" + consumerSecret
        String encodedCredentials = HelperUtility.toBase64String(String.format("%s:%s", mpesaConfiguration.getConsumerKey(),
                mpesaConfiguration.getConsumerSecret()));

        Request request = new Request.Builder()
                .url(String.format("%s?grant_type=%s", mpesaConfiguration.getOauthEndpoint(), mpesaConfiguration.getGrantType()))
                .get()
                .addHeader(AUTHORIZATION_HEADER_STRING, String.format("%s %s", BASIC_AUTH_STRING, encodedCredentials))
                .addHeader(CACHE_CONTROL_HEADER, CACHE_CONTROL_HEADER_VALUE)
                .build();

        try {
            Response response = okHttpClient.newCall(request).execute();
            assert response.body() != null;

            // use Jackson to Decode the ResponseBody ...
            return objectMapper.readValue(response.body().string(), AccessTokenResponse.class);
        } catch (IOException e) {
            log.error(String.format("Could not get access token. -> %s", e.getLocalizedMessage()));
            return null;
        }
    }

    @Override
    public RegisterUrlResponse registerUrl() {

        AccessTokenResponse accessTokenResponse = getAccessToken();

        RegisterUrlRequest registerUrlRequest = new RegisterUrlRequest();
        registerUrlRequest.setConfirmationUrl(mpesaConfiguration.getConfirmationUrl());
        registerUrlRequest.setValidationUrl(mpesaConfiguration.getValidationUrl());
        registerUrlRequest.setResponseType(mpesaConfiguration.getResponseType());
        registerUrlRequest.setShortCode(mpesaConfiguration.getShortCode());

        RequestBody body = RequestBody.create(JSON_MEDIA_TYPE, Objects.requireNonNull(HelperUtility.toJson(registerUrlRequest)));

        Request request = new Request.Builder()
                .url(mpesaConfiguration.getRegisterUrlEndpoint())
                .post(body)
                .addHeader(AUTHORIZATION_HEADER_STRING, String.format("%s %s", BEARER_AUTH_STRING, accessTokenResponse.getAccessToken()))
                .build();

        try {
            Response response = okHttpClient.newCall(request).execute();

            assert response.body() != null;
            // use Jackson to Decode the ResponseBody ...
            return objectMapper.readValue(response.body().string(), RegisterUrlResponse.class);

        } catch (IOException e) {
            log.error(String.format("Could not register url -> %s", e.getLocalizedMessage()));
            return null;
        }
    }

    @Override
    public SimulateC2BResponse simulateC2BTransaction(SimulateC2BRequest simulateC2BRequest) {
        AccessTokenResponse accessTokenResponse = getAccessToken();

        RequestBody body = RequestBody.create(JSON_MEDIA_TYPE, Objects.requireNonNull(HelperUtility.toJson(simulateC2BRequest)));

        Request request = new Request.Builder()
                .url(mpesaConfiguration.getSimulateTransactionEndpoint())
                .post(body)
                .addHeader(AUTHORIZATION_HEADER_STRING, String.format("%s %s", BEARER_AUTH_STRING, accessTokenResponse.getAccessToken()))
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();

            assert response.body() != null;
            // use Jackson to Decode the ResponseBody ...
            return objectMapper.readValue(response.body().string(), SimulateC2BResponse.class);

        } catch (IOException e) {
            log.error(String.format("Could not simulate C2B transaction -> %s", e.getLocalizedMessage()));
            return null;
        }
    }
}
