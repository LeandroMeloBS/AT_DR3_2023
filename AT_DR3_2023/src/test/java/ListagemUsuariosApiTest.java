import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.HttpURLConnection;
import java.net.URL;

public class ListagemUsuariosApiTest {

    private String apiUrl; // Defina a URL da sua API aqui

    @Before
    public void setUp() {
        // Defina a URL da sua API
        apiUrl = "http://localhost:4567/usuarios";
    }

    @Test
    public void testListagemUsuariosApi() {
        // Inicie a sua aplicação Spark aqui (opcional, dependendo da estrutura do seu aplicativo)
        // ...

        // Faça uma requisição à API e verifique o código de resposta
        int responseCode = verificarCodigoRespostaApi(apiUrl);
        Assert.assertEquals(200, responseCode);

        // Encerre a sua aplicação Spark aqui (opcional, dependendo da estrutura do seu aplicativo)
        // ...
    }

    private int verificarCodigoRespostaApi(String apiUrl) {
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            return connection.getResponseCode();
        } catch (Exception e) {
            e.printStackTrace();
            return -1; // Retorna um valor que indica um problema na requisição
        }
    }
}
