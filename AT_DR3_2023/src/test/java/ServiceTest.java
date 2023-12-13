import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import dto.UsuarioDTOInput;
import dto.UsuarioDTOOutput;
import service.UsuarioService;
import java.util.List;

public class ServiceTest {

    private UsuarioService usuarioService;

    @Before
    public void setUp() {
        // Inicialize a instância de UsuarioService antes de cada teste
        usuarioService = new UsuarioService();
    }

    @Test
    public void testInserirUsuarioEListar() {
        // Crie um objeto UsuarioDTOInput para inserção
        UsuarioDTOInput usuarioDTOInput = new UsuarioDTOInput();
        usuarioDTOInput.setNome("Nome do Usuário");
        usuarioDTOInput.setSenha("Senha123");

        // Execute o método de inserção
        usuarioService.inserirUsuario(usuarioDTOInput);

        // Execute o método de listagem
        List<UsuarioDTOOutput> listaUsuariosDTO = usuarioService.listarUsuariosDTO();

        // Verifique se a execução foi bem-sucedida (tamanho da lista igual a 1)
        Assert.assertEquals(1, listaUsuariosDTO.size());

        // Se necessário, você também pode imprimir os detalhes dos usuários
        for (UsuarioDTOOutput usuario : listaUsuariosDTO) {
            System.out.println("ID: " + usuario.getId() + ", Nome: " + usuario.getNome());
        }
    }
}