package Grupo1.ProyectoSolWeb.Services.Implements;

import Grupo1.ProyectoSolWeb.Model.Usuario;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import Grupo1.ProyectoSolWeb.Repository.IUsuario;
import Grupo1.ProyectoSolWeb.Services.IUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
public class UserServices implements IUserService,UserDetailsService{
    
    @Autowired
    private IUsuario data;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws
    UsernameNotFoundException {
    Optional<Usuario> usuario = data.findByUser(username);
    return usuario.map(UsuarioServiceDetails::new)
    .orElseThrow(()->new UsernameNotFoundException("user not found "+username));
    }

    
    @Override
    public List<Usuario> listar() {
        return (List<Usuario>) data.findAll();// Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void guardar(Usuario U) {
        data.save(U); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void eliminar(int idUsuario) {
        data.deleteById(idUsuario); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Optional<Usuario> encontrar(int idUsuario) {
         return data.findById(idUsuario); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Usuario> Ascendente() {
        return data.ascNombres(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Usuario> Descendente() {
        return data.descNombres(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}

