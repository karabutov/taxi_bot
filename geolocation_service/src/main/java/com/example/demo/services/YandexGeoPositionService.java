package com.example.demo.services;

import com.example.demo.model.Coordinates;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import javax.annotation.PostConstruct;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;


@Component
@RequiredArgsConstructor
public class YandexGeoPositionService implements GeoPositionService {

    private final RestTemplate restTemplate;

    @Value("${geo.apikey}")
    private String geoApiKey;

    @Value("${geo.url}")
    private String geoUrl;

    @PostConstruct
    public void initUrl(){
        geoUrl = geoUrl.replace("{$geoApiKey}", geoApiKey);
    }

    @Override
    public Coordinates getCoordinates(String address) {
        String endpoint = geoUrl.replace("{$address}", address.replace(' ', '+'));
        ResponseEntity<String> responseEntity = restTemplate.exchange(endpoint, HttpMethod.GET, null, String.class);
        return extractCoordinates(responseEntity.getBody());
    }

    @SneakyThrows
    private Coordinates extractCoordinates(String xmlSource){

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Element elem = builder.parse(new InputSource(new StringReader(xmlSource))).getDocumentElement();
        String pos = elem.getElementsByTagName("pos").item(0).getChildNodes().item(0).getNodeValue();
        return Coordinates.builder()
                .Latitude(pos.split(" ")[0])
                .Longitude(pos.split(" ")[1])
                .build();
    }

}
