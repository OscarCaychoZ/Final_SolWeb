package Grupo1.ProyectoSolWeb.Repository;

import Grupo1.ProyectoSolWeb.Model.Usuario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IUsuario extends CrudRepository<Usuario, Integer> {
    
    public Optional<Usuario> findByUser(String username);
    
    /*Optional<Cliente> findByEmail(String correo);*/
    @Query(value = "SELECT * FROM usuario "
            + "ORDER BY nombre ", nativeQuery = true)
    List<Usuario> ascNombres();

    @Query(value = "SELECT * FROM usuario "
            + "ORDER BY nombre DESC ", nativeQuery = true)
    List<Usuario> descNombres();

}
