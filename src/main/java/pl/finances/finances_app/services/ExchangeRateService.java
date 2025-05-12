package pl.finances.finances_app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.finances.finances_app.dto.requestAndResponse.ExchangeRateResponse;

@Service
public class ExchangeRateService {
    private final RestTemplate restTemplate;

    @Autowired
    public ExchangeRateService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public double getEuroExchangeRate() {
        String url = "https://api.nbp.pl/api/exchangerates/rates/a/eur/?format=json";
        ExchangeRateResponse exchangeRateResponse = restTemplate.getForObject(url, ExchangeRateResponse.class);

        if(exchangeRateResponse == null) {
            throw new RuntimeException("Exchange rate response is null");
        }
        return exchangeRateResponse.rates().getFirst().mid();
    }
}
