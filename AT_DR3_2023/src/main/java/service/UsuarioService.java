package service;

import dto.UsuarioDTOInput;
import dto.UsuarioDTOOutput;
import model.Usuario;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;

public class UsuarioService {

    private List<Usuario> listaUsuarios = new ArrayList<>();
    private ModelMapper modelMapper = new ModelMapper();

    public List<Usuario> listarUsuarios() {
        return listaUsuarios;
    }

    public void inserirUsuario(UsuarioDTOInput usuarioDTOInput) {
        // Convertendo UsuarioDTOInput para Usuario usando ModelMapper
        Usuario usuario = modelMapper.map(usuarioDTOInput, Usuario.class);

        // Adicionando o usuário convertido à lista
        listaUsuarios.add(usuario);
    }

    public void alterarUsuario(int id, UsuarioDTOInput usuarioDTOInput) {
        // Convertendo UsuarioDTOInput para Usuario usando ModelMapper
        Usuario usuarioAtualizado = modelMapper.map(usuarioDTOInput, Usuario.class);

        // Substituindo o objeto existente na lista de usuários
        Optional<Usuario> usuarioExistente = listaUsuarios.stream()
                .filter(u -> u.getId() == id)
                .findFirst();

        usuarioExistente.ifPresent(u -> {
            u.setNome(usuarioAtualizado.getNome());
            u.setSenha(usuarioAtualizado.getSenha());
        });
    }

    public UsuarioDTOOutput buscarUsuarioPorId(int id) {
        // Buscando o usuário na lista
        Optional<Usuario> usuarioExistente = listaUsuarios.stream()
                .filter(u -> u.getId() == id)
                .findFirst();

        // Convertendo o usuário para UsuarioDTOOutput usando ModelMapper
        return usuarioExistente.map(usuario -> modelMapper.map(usuario, UsuarioDTOOutput.class))
                .orElse(null); // Pode retornar null ou outra indicação de que o usuário não foi encontrado
    }

    public boolean excluirUsuario(int id) {
        Optional<Usuario> usuarioExistente = listaUsuarios.stream()
                .filter(u -> u.getId() == id)
                .findFirst();

        if (usuarioExistente.isPresent()) {
            listaUsuarios.remove(usuarioExistente.get());
            return true; // Usuário excluído com sucesso
        } else {
            return false; // Usuário não encontrado
        }
    }

    public List<UsuarioDTOOutput> listarUsuariosDTO() {
        return listaUsuarios.stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioDTOOutput.class))
                .collect(Collectors.toList());
    }
}
