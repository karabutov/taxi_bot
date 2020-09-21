package com.example.taxi_service.services;

import com.example.taxi_service.model.Aggregator;
import com.example.taxi_service.model.Coordinates;
import com.example.taxi_service.model.RidePrice;
import com.example.taxi_service.services.TaxiService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class YandexTaxiService implements TaxiService {

    private static final String ROUTE = "route";

    private Map<String, Object> body = new HashMap<>();

    @Value("${yandex.body}")
    private String yandexBody;

    @Value("${yandex.url}")
    private String yandexUrl;

    private final RestTemplate restTemplate;

    @SneakyThrows
    @PostConstruct
    public void initBody() {
        body = new ObjectMapper().readValue(yandexBody, HashMap.class);
    }

    @Override
    public List<RidePrice> getRideInfo(Coordinates startPoint, Coordinates endPoint) {
        Map<String, Object> body = getBody(startPoint, endPoint);
        ResponseEntity<String> exchange = restTemplate.exchange(yandexUrl, HttpMethod.POST, new HttpEntity<>(body), String.class);
        return extractPrises(exchange.getBody());
    }

    @SneakyThrows
    private List<RidePrice> extractPrises(String jsonSource) {
        List<RidePrice> ridePriceList = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(jsonSource);
        JSONArray jsonArray = jsonObject.getJSONArray("service_levels");

        for (int i = 0; i < jsonArray.length(); i++) {
            int price = Integer.parseInt(jsonArray.getJSONObject(i).getString("price").replaceAll("[^0-9]", ""));
            String classTaxiName = jsonArray.getJSONObject(i).getString("name");

            ridePriceList.add(
                    RidePrice.builder()
                            .price(price)
                            .classTaxi(classTaxiName)
                            .aggregator(Aggregator.YANDEX_TAXI)
                            .build()
            );
        }
        return ridePriceList;
    }

    private Map<String, Object> getBody(Coordinates startPoint, Coordinates endPoint) {
        Map<String, Object> res = new HashMap<>(body);
        res.put(ROUTE, List.of(
                List.of(Double.parseDouble(startPoint.getLatitude()), Double.parseDouble(startPoint.getLongitude())),
                List.of(Double.parseDouble(endPoint.getLatitude()), Double.parseDouble(endPoint.getLongitude()))
                )
        );
        return res;
    }

}
