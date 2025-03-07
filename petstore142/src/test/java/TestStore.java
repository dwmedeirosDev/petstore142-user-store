
// assertThat
import static org.assertj.core.api.Assertions.assertThat;

// Função given
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

// Tratamento de erro
import java.io.IOException;

// Imports da leitura do JSON
import java.nio.file.Files;
import java.nio.file.Paths;

// Ordem dos @Test
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import com.google.gson.Gson;

import io.restassured.response.Response;

import org.junit.jupiter.api.MethodOrderer;

// Import do @Test
import org.junit.jupiter.api.Test;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class TestStore {
    static String ct = "application/json";
    static String baseURL = "https://petstore.swagger.io/v2";

    static int id = 9;
    static int petId = 5;
    static int quantity = 1;
    static String shipDate = "2025-03-05T20:50:29.821+0000";
    static String status = "sold";
    static boolean complete = true;

    // Leitura do JSON
    public static String readFileJson(String fileJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileJson)));
    }

    @Test
    @Order(1)

    public void TestPostStore() throws IOException {

        // Guardando a leitura do JSON em uma variável
        String jsonBody = readFileJson("src/test/resources/json/store1.json");

        Response response = given() // Dado que
                .contentType(ct)
                .log().all()
                .body(jsonBody)

                .when() // Quando
                .post(baseURL + "/store/order")

                .then() // Então
                .log().all()

                // Comparando o código de requisição
                .statusCode(200)

                // Comparando as respostas
                .body("id", is(id))
                .body("petId", is(petId))
                .body("quantity", is(quantity))
                .body("shipDate", is(shipDate))
                .body("status", is(status))
                .body("complete", is(complete))

                // Teste de contrato
                // Verificação das propriedadas existentes na resposta
                .body("$", hasKey("id"))
                .body("$", hasKey("petId"))
                .body("$", hasKey("quantity"))
                .body("$", hasKey("shipDate"))
                .body("$", hasKey("status"))
                .body("$", hasKey("complete"))

                // Verificação dos formatos das propriedades existentes na resposta
                .body("id", instanceOf(Integer.class))
                .body("petId", instanceOf(Integer.class))
                .body("quantity", instanceOf(Integer.class))
                .body("shipDate", instanceOf(String.class))
                .body("status", instanceOf(String.class))
                .body("complete", instanceOf(Boolean.class))

                .extract()
                .response();

        // Validando se o ID retornado está no intervalo desejado
        int responseId = response.jsonPath().getInt("id");
        assertThat(responseId).isBetween(1, 10);
    }

    @Test
    @Order(2)

    public void testGetStore() {
        Response response = given() // Dado que
                .contentType(ct)
                .log().all()

                .when() // Quando
                .get(baseURL + "/store/order/" + id)

                .then() // Então
                .log().all()

                // Comparando o código de requisição
                .statusCode(200)

                // Comparando as respostas
                .body("id", is(id))
                .body("petId", is(petId))
                .body("quantity", is(quantity))
                .body("shipDate", is(shipDate))
                .body("status", is(status))
                .body("complete", is(complete))

                // Teste de contrato
                // Verificação das propriedadas existentes na resposta
                .body("$", hasKey("id"))
                .body("$", hasKey("petId"))
                .body("$", hasKey("quantity"))
                .body("$", hasKey("shipDate"))
                .body("$", hasKey("status"))
                .body("$", hasKey("complete"))

                // Verificação dos formatos das propriedades existentes na resposta
                .body("id", instanceOf(Integer.class))
                .body("petId", instanceOf(Integer.class))
                .body("quantity", instanceOf(Integer.class))
                .body("shipDate", instanceOf(String.class))
                .body("status", instanceOf(String.class))
                .body("complete", instanceOf(Boolean.class))

                .extract()
                .response();

        // Validando se o ID retornado está no intervalo desejado
        int responseId = response.jsonPath().getInt("id");
        assertThat(responseId).isBetween(1, 10);
    }

    @Test
    @Order(3)

    public void TestDelStore() {
        given() // Dado que
                .contentType(ct)
                .log().all()

                .when() // Quando
                .delete(baseURL + "/store/order/" + id)

                .then() // Então
                .log().all()

                // Comparando o código de requisição
                .statusCode(200)

                // Comparando as respostas
                .body("code", is(200))
                .body("type", is("unknown"))
                .body("message", is("9"));
    }

    @ParameterizedTest
    @Order(4)

    // Chamando o arquivo CSV, pulando a leitura da primeira linha, separando os dados por vírgula
    @CsvFileSource(resources = "/csv/storeMassa.csv", numLinesToSkip = 1, delimiter = ',')

    public void testPostStoreDDT(

            // Parâmetros
            int id,
            int petId,
            int quantity,
            String shipDate,
            String status,
            Boolean complete) {

        // Instanciando o objeto Store.java
        Store store = new Store();

        // Relacionando os atributos do objeto com os parâmetros do método
        store.id = id;
        store.petId = petId;
        store.quantity = quantity;
        store.shipDate = shipDate;
        store.status = status;
        store.complete = complete;

        // Instanciando a biblioteca Gson e convertendo o objeto Store.java em uma string JSON
        Gson gson = new Gson();
        String jsonBody = gson.toJson(store);

        given() // Dado que
                .contentType(ct)
                .log().all()
                .body(jsonBody)

                .when() // Quando
                .post(baseURL + "/store/order")

                .then() // Então
                .log().all()

                // Comparando o código de requisição
                .statusCode(200);
    }
}