package es.udc.fic.csi2122.baserest.dto;

/**
 * Dto class for controller responses
 *
 * @param name the client name
 * @param surname  the client surname or surnames
 * @param email  the client email
 * @param payMethods  the client pay methods
 *
 * @author jorge.arias@udc.es
 */
public record ClientDto(String name, String surname, String email, String payMethods) {
}
