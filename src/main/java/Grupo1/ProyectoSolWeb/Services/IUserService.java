package Grupo1.ProyectoSolWeb.Services;


import Grupo1.ProyectoSolWeb.Model.Usuario;
import java.util.List;
import java.util.Optional;

public interface IUserService{
    public List<Usuario> listar();
    public void guardar(Usuario U);
    public void eliminar(int idUsuario);
    public Optional<Usuario> encontrar(int idUsuario);
    public List<Usuario> Ascendente();
    public List<Usuario> Descendente();
}


