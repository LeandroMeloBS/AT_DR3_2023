import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InsercaoUsuarioApiTest {

    private static final Logger logger = Logger.getLogger(InsercaoUsuarioApiTest.class.getName());

    @Test
    public void testInsercaoUsuarioApi() {
        String apiUrl = "http://localhost:4567/usuarios";
        String usuarioRandom = obterUsuarioRandom();

        Assert.assertNotNull("Falha ao obter usuário aleatório", usuarioRandom);

        String nomeUsuario = extrairNomeUsuario(usuarioRandom);
        Assert.assertNotNull("Falha ao extrair nome do usuário", nomeUsuario);

        int responseCode = fazerRequisicaoApi(apiUrl, nomeUsuario);

        // Adiciona informações de debug
        System.out.println("Resposta da API: " + responseCode);
        if (responseCode == 500) {
            System.out.println("Erro interno no servidor. Verifique os logs do servidor para mais detalhes.");
        }

        Assert.assertEquals("A inserção do usuário falhou", 201, responseCode);
    }

    private String obterUsuarioRandom() {
        try {
            URL randomUserApiUrl = new URL("https://randomuser.me/api/");
            HttpURLConnection connection = prepararConexao(randomUserApiUrl);

            if (connection.getResponseCode() == 200) {
                return lerResposta(connection);
            } else {
                throw new IOException("Falha na requisição à API pública");
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Erro ao obter usuário aleatório", e);
            return null;
        }
    }

    private HttpURLConnection prepararConexao(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        return connection;
    }

    private String lerResposta(HttpURLConnection connection) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            return response.toString();
        }
    }

    private String extrairNomeUsuario(String usuarioRandom) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(usuarioRandom);
            return rootNode.path("results").get(0).path("name").path("first").asText();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Erro ao extrair nome do usuário", e);
            return null;
        }
    }

    private int fazerRequisicaoApi(String apiUrl, String nomeUsuario) {
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = prepararConexao(url);
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            String requestBody = "{ \"nome\": \"" + nomeUsuario + "\", \"email\": \"" + nomeUsuario.toLowerCase() + "@example.com\" }";
            connection.getOutputStream().write(requestBody.getBytes());

            return connection.getResponseCode();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Erro ao fazer requisição à API", e);
            return -1;
        }
    }
}
