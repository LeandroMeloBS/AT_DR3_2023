package org.example;


import controller.UsuarioController;
import service.UsuarioService;
import spark.Spark;

public class Main {
    public static void main(String[] args) {
        // Inicializa o serviço de usuários
        UsuarioService usuarioService = new UsuarioService();

        // Inicializa o controlador de usuários
        UsuarioController usuarioController = new UsuarioController(usuarioService);

        // Inicia o Spark e escuta na porta 4567 antes de definir as rotas
        Spark.port(4567);

        // Configura as rotas e respostas para requisições HTTP
        usuarioController.respostasRequisicoes();

        // Aguarde a inicialização completa do Spark
        Spark.awaitInitialization();
    }
}



