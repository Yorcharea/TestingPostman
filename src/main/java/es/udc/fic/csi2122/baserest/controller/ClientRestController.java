package es.udc.fic.csi2122.baserest.controller;


import java.net.SocketOption;
import java.rmi.ServerException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.swing.plaf.synth.SynthOptionPaneUI;
import javax.transaction.Transactional;


import es.udc.fic.csi2122.baserest.conversors.ClientConversors;
import es.udc.fic.csi2122.baserest.dto.ClientDto;
import es.udc.fic.csi2122.baserest.entity.Client;
import es.udc.fic.csi2122.baserest.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;

import es.udc.fic.csi2122.baserest.conversors.UserConversors;
import es.udc.fic.csi2122.baserest.dto.UserDto;
import es.udc.fic.csi2122.baserest.entity.User;
import es.udc.fic.csi2122.baserest.repository.ClientRepository;


/**
 * Client spring REST controller
 *
 * The {@link RequestMapping} annotation indicates the client path for all
 * petitions that this
 * controller will handle. the path for the endpoints defined is appended to
 * this path
 *
 * @author jorge.arias@udc.es
 */

@RestController
@Transactional
@RequestMapping("client")
public class ClientRestController {


    private static final Logger logger = LoggerFactory.getLogger(ClientRestController.class);

    @PersistenceContext
    private EntityManager em;

    private ClientRepository clientRepository;

    /**
     * Constructor dependency injection signaled by the {@link Autowired} annotation
     *
     * @param clientRepository the user repository
     */
    @Autowired
    public ClientRestController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    /**
     * Get all users
     *
     * @return a list of all the users
     */
    @GetMapping(value = "all")
    public List<ClientDto> getClient() {
        return ClientConversors.toClientDtoList(clientRepository.findAll());
    }


    @GetMapping(value = "{id}")
    public ResponseEntity<ClientDto> get(@PathVariable Long id) {
        logger.info("Fetching client with id: {}", id);
        var user = clientRepository.findById(id);

        return ResponseEntity.of(user.map(u -> {
            logger.info("Found client with id {}: {}", id, u);
            return ClientConversors.toClientDto(u);
        }));
    }

    /**
     * Create a new client
     *
     * We use the JPA EntityManager to do the operation. Errors during this
     * operation are not handled and will results in an exceptions thrown and a 500
     * Internal Server Error response
     *
     * Alternatively we could use the save() method of the UserRepository.
     *
     * @param client the new client
     * @return the id of the new client
     */
    @PostMapping(value = "new")
    public Long newClient(@RequestBody ClientDto client) {
        logger.info("Creating new client: {}", client);
        var newClient = em.merge(ClientConversors.toClient(client));
        logger.info("Created client: {}", newClient);
        return newClient.getId();
    }

    @GetMapping(value = "search")
    public ResponseEntity<List<ClientDto>> search(String name) {

        final List<Client> clients;

            clients = clientRepository.findOneByName(name)
                    .map(Arrays::asList)
                    .orElseGet(Arrays::asList);

        return ResponseEntity.ok(ClientConversors.toClientDtoList(clients));
    }

    @PutMapping("/client/{id}")
    Client updateClient(@PathVariable(value = "id") Long clientId,
                        @RequestBody Client newClient) {

        System.out.println(newClient.toString());

        final Client client;
         client = clientRepository.findById(clientId).get();
         if (client.getId()!=null){
             logger.info("Updating client id : {} with {}",client,newClient);
             System.out.println(newClient.toString());
             client.setName(newClient.getName());
             client.setSurname(newClient.getSurname());
             client.setEmail(newClient.getEmail());
             client.setPayMethods(newClient.getPayMethods());

             Client clienteActualizado = clientRepository.save(client);
             logger.info("Updated client: {}", newClient);

             return clienteActualizado;
         }
         else{
             logger.info("Client with id : {} doesn't exists",clientId);
             return null;
         }
    }
    }