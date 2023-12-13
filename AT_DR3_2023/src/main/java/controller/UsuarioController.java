package controller;

import dto.UsuarioDTOInput;
import dto.UsuarioDTOOutput;
import service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;
import service.UsuarioService;

public class UsuarioController {

    private UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public void respostasRequisicoes() {
        // Endpoint para listar usuários
        Spark.get("/usuarios", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                // Listar usuários utilizando UsuarioService
                ObjectMapper objectMapper = new ObjectMapper();
                String usuariosJson = objectMapper.writeValueAsString(usuarioService.listarUsuariosDTO());

                // Configurar a resposta
                response.type("application/json");
                response.status(200);
                return usuariosJson;
            }
        });

        // Endpoint para buscar usuário por ID
        Spark.get("/usuarios/:id", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                // Obter o ID do parâmetro da URI
                int id = Integer.parseInt(request.params(":id"));

                // Buscar usuário utilizando UsuarioService
                UsuarioDTOOutput usuarioDTO = usuarioService.buscarUsuarioPorId(id);

                // Configurar a resposta
                if (usuarioDTO != null) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    String usuarioJson = objectMapper.writeValueAsString(usuarioDTO);

                    response.type("application/json");
                    response.status(200);
                    return usuarioJson;
                } else {
                    response.status(404); // Not Found (usuário não encontrado)
                    return "Usuário não encontrado";
                }
            }
        });

        // Endpoint para excluir usuário por ID
        Spark.delete("/usuarios/:id", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                // Obter o ID do parâmetro da URI
                int id = Integer.parseInt(request.params(":id"));

                // Excluir usuário utilizando UsuarioService
                boolean usuarioExcluido = usuarioService.excluirUsuario(id);

                // Configurar a resposta
                if (usuarioExcluido) {
                    response.status(204); // No Content (usuário excluído com sucesso)
                    return "";
                } else {
                    response.status(404); // Not Found (usuário não encontrado)
                    return "Usuário não encontrado";
                }
            }
        });

        // Endpoint para inserir usuário
        Spark.post("/usuarios", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                // Converter JSON no corpo da requisição para UsuarioDTOInput
                ObjectMapper objectMapper = new ObjectMapper();
                UsuarioDTOInput novoUsuarioDTO = objectMapper.readValue(request.body(), UsuarioDTOInput.class);

                // Inserir usuário utilizando UsuarioService
                usuarioService.inserirUsuario(novoUsuarioDTO);

                // Configurar a resposta
                response.status(201); // Created (usuário inserido com sucesso)
                return "Usuário inserido com sucesso!";
            }
        });

        // Endpoint para atualizar usuário
        Spark.put("/usuarios", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                // Converter JSON no corpo da requisição para UsuarioDTOInput
                ObjectMapper objectMapper = new ObjectMapper();
                UsuarioDTOInput usuarioAtualizadoDTO = objectMapper.readValue(request.body(), UsuarioDTOInput.class);

                // Atualizar usuário utilizando UsuarioService
                usuarioService.alterarUsuario(usuarioAtualizadoDTO.getId(), usuarioAtualizadoDTO);

                // Configurar a resposta
                response.status(200); // OK (usuário atualizado com sucesso)
                return "Usuário atualizado com sucesso!";
            }
        });

    }
}