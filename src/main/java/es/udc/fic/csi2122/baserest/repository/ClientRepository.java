package es.udc.fic.csi2122.baserest.repository;


import java.util.List;
import java.util.Optional;

import es.udc.fic.csi2122.baserest.entity.Client;
import es.udc.fic.csi2122.baserest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.udc.fic.csi2122.baserest.entity.Client;

/**
 * The client repository (aka DAO)
 *
 * We extend {@link JpaRepository} indicating the type of the entity and the
 * type of the id
 *
 * {@link JpaRepository} provides us already with some CRUD operations
 *
 * @author jorge.arias@udc.es
 */

public interface ClientRepository extends JpaRepository<Client, Long>{


    /**
     * Search client by name.
     *
     * @param name the client name
     * @return maybe the client or an empty Optional
     */
    Optional<Client> findOneByName(String name);

    /**
     * Search client by id.
     *
     * @param id the client id
     * @return maybe the client or an empty Optional
     */
    Optional<Client> findById(Long id);


}
