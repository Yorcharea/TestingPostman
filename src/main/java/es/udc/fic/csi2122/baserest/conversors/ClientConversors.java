package es.udc.fic.csi2122.baserest.conversors;


import es.udc.fic.csi2122.baserest.dto.ClientDto;
import es.udc.fic.csi2122.baserest.entity.Client;
import java.util.List;

/**
 * Utility class with conversions between {@link Client} and {@link ClientDto}
 *
 * @author jorge.arias@udc.es
 */

public class ClientConversors {

    /**
     * Private constructor to avoid instantiation
     */
    private ClientConversors() {
    }

    /**
     * Convert to a {@link ClientDto}
     *
     * @param client the client entity
     * @return a client dto
     */
    public static ClientDto toClientDto(Client client) {
        return new ClientDto(client.getName(), client.getSurname(), client.getEmail(), client.getPayMethods());
    }

    /**
     * Convert a {@link List} of {@link Client} to a {@link List} of {@link ClientDto}
     *
     * @param clients the list of clients entities
     * @return a list of client dtos
     */
    public static List<ClientDto> toClientDtoList(List<Client> clients) {
        return clients.stream().map(ClientConversors::toClientDto).toList();
    }

    /**
     * Convert a {@link ClientDto} to a {@link Client} without id
     *
     * @param client the client dto
     * @return a client entity without id
     */
    public static Client toClient(ClientDto client) {
        return new Client(client.name(), client.surname(), client.email(), client.payMethods());
    }
}

