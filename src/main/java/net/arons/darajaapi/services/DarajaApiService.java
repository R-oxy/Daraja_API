package net.arons.darajaapi.services;

import lombok.Data;
import net.arons.darajaapi.dtos.*;

public interface DarajaApiService {
    /**
     * @return Returns Daraja API Access Token Response
     */

    AccessTokenResponse getAccessToken();

    RegisterUrlResponse registerUrl();

    SimulateC2BResponse simulateC2BTransaction(SimulateC2BRequest simulateC2BRequest);
}
