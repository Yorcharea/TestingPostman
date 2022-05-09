package es.udc.fic.csi2122.baserest.controller;


import static es.udc.fic.csi2122.baserest.utils.TestRestTemplateUtils.getForList;
import static org.assertj.core.api.Assertions.assertThat;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import es.udc.fic.csi2122.baserest.dto.ClientDto;
import es.udc.fic.csi2122.baserest.repository.ClientRepository;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ClientRestControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl;

    @BeforeEach
    private void initBaseUrl() {
        baseUrl = "http://localhost:" + port + "/client";
    }

    @BeforeEach
    @AfterEach
    private void resetClients() {
        clientRepository.deleteAll();
    }

    @Test
    void createClient() {
        var client = new ClientDto("Jorge", "Arias","Ronda de Outeiro,257,4ºA","PayPal");
        var response = restTemplate.postForEntity(baseUrl + "/new", client, Long.class);


        assertThat(response.getStatusCode()).matches(HttpStatus::is2xxSuccessful);

        var id = response.getBody();
        var response2 = restTemplate.getForObject(baseUrl + "/" + id, ClientDto.class);
        assertThat(response2).isEqualTo(client);

    }

    @Test
    void getClient() {
        var client = new ClientDto("Jorge", "Arias","Ronda de Outeiro,257,4ºA","PayPal");
        var client2 = new ClientDto("Pepito", "Gómez","Rua da Concordancia,5, 2ºA","Tarjeta de Credito, Paypal");
        restTemplate.postForObject(baseUrl + "/new", client, Long.class);
        System.out.println(baseUrl);
        restTemplate.postForObject(baseUrl + "/new", client2, Long.class);
        var response = getForList(restTemplate, baseUrl + "/all", ClientDto.class);

        assertThat(response).hasSize(2);
        assertThat(response.get(0)).isEqualTo(client);
        assertThat(response.get(1)).isEqualTo(client2);
    }
    @Test
    void updateClient() {
        var client = new ClientDto("Jorge", "Arias","Ronda de Outeiro,257,4ºA","PayPal");

        var response=restTemplate.postForEntity(baseUrl + "/new", client, Long.class);
        var id= response.getBody();
        var updateClient = new ClientDto("Jorge", "Arias Barrio","Prueba Cambio Direccion","PayPal,Cuenta Bancaria");
        //Aqui se crea bien
        Map< String, Long > params = new HashMap< String, Long >();
        params.put("id", Long.getLong("1"));
        restTemplate.put(baseUrl+"/client/{id}"+id,updateClient, params);

        var response2 = getForList(restTemplate, baseUrl + "/all", ClientDto.class);

        assertThat(response2).hasSize(1);
        assertThat(response2.get(0)).isEqualTo(updateClient);
    }




}
