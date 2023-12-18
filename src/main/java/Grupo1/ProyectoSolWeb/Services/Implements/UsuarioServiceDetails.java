/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Grupo1.ProyectoSolWeb.Services.Implements;

import Grupo1.ProyectoSolWeb.Model.Usuario;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


public class UsuarioServiceDetails implements UserDetails {
    private String user;
    private String pass;
    private List<GrantedAuthority> authorities;
    
        public UsuarioServiceDetails(Usuario usuario) {
            user = usuario.getNombre();
            pass = usuario.getPassword();
            authorities = Arrays.asList(usuario.getRol())
                .stream()
                .map(rol -> new SimpleGrantedAuthority("ROLE_" + rol.getDescripcion()))
                .collect(Collectors.toList());
    }
        
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    @Override
    public String getPassword() {
        return pass;
    }
    @Override
    public String getUsername() {
        return user;
    }@Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
}
