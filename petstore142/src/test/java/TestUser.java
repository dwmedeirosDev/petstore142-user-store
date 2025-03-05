// 1- Testes para o método Post, Get, Put e Delete da entidade User

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

// Import do @Test
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

// Import do Gson
import com.google.gson.Gson;

import org.junit.jupiter.api.MethodOrderer;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class TestUser {
    static String ct = "application/json";
    static String baseURL = "https://petstore.swagger.io/v2";

    static int id = 11865005;
    static int userStatus = 1;
    static String username = "dwmedeiros";
    static String firstName = "David";
    static String lastName = "Medeiros";
    static String email = "teste@qa.com";
    static String password = "@Teste123";
    static String phone = "21999998888";

    // Leitura do JSON
    public static String readFileJson(String fileJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileJson)));
    }

    @Test
    @Order(1)

    // POST
    public void testPostUser() throws IOException {

        // Guardando a leitura do JSON em uma variável
        String jsonBody = readFileJson("src/test/resources/json/user1.json");

        given() // Dado que
                .contentType(ct)
                .log().all()
                .body(jsonBody)

                .when() // Quando
                .post(baseURL + "/user")

                .then() // Então
                .log().all()

                // Comparando o código de requisição
                .statusCode(200)

                // Comparando as respostas
                .body("code", is(200))
                .body("type", is("unknown"))
                .body("message", is("11865005"));
    }

    @Test
    @Order(2)

    // GET
    public void testGetUser() {
        given() // Dado que
                .contentType(ct)
                .log().all()

                .when() // Quando
                .get(baseURL + "/user/" + username)

                .then() // Então
                .log().all()

                // Comparando o código de requisição
                .statusCode(200)

                // Comparando as respostas (Eco)
                .body("id", is(id))
                .body("username", is(username))
                .body("firstName", is(firstName))
                .body("lastName", is(lastName))
                .body("email", is(email))
                .body("password", is(password))
                .body("phone", is(phone))
                .body("userStatus", is(userStatus))

                // Teste de contrato
                // Verificação das propriedadas existentes na resposta
                .body("$", hasKey("id"))
                .body("$", hasKey("username"))
                .body("$", hasKey("firstName"))
                .body("$", hasKey("lastName"))
                .body("$", hasKey("email"))
                .body("$", hasKey("password"))
                .body("$", hasKey("phone"))
                .body("$", hasKey("userStatus"))

                // Verificação dos formatos das propriedades existentes na resposta
                .body("id", instanceOf(Integer.class))
                .body("username", instanceOf(String.class))
                .body("firstName", instanceOf(String.class))
                .body("lastName", instanceOf(String.class))
                .body("email", instanceOf(String.class))
                .body("password", instanceOf(String.class))
                .body("phone", instanceOf(String.class))
                .body("userStatus", instanceOf(Integer.class));
    }

    @Test
    @Order(3)

    // PUT
    public void testPutUser() throws IOException {

        // Guardando a leitura do JSON em uma variável
        String jsonBody = readFileJson("src/test/resources/json/user2.json");

        given() // Dado que
                .contentType(ct)
                .log().all()
                .body(jsonBody)

                .when() // Quando
                .put(baseURL + "/user/" + username)

                .then() // Então
                .log().all()

                // Comparando o código de requisição
                .statusCode(200)

                // Comparando as respostas
                .body("code", is(200))
                .body("type", is("unknown"))
                .body("message", is("11865005"));
    }

    @Test
    @Order(4)

    // DEL
    public void testDelUser() {
        given() // Dado que
                .contentType(ct)
                .log().all()

                .when() // Quando
                .delete(baseURL + "/user/" + username)

                .then() // Então
                .log().all()

                // Comparando o código de requisição
                .statusCode(200)

                // Comparando as respostas
                .body("code", is(200))
                .body("type", is("unknown"))
                .body("message", is("dwmedeiros"));
    }

    // 2- Um teste Data Driven para o Post da User

    @ParameterizedTest
    @Order(5)

    // Chamando o arquivo CSV, pulando a leitura da primeira linha, separando os dados por vírgula
    @CsvFileSource(resources = "/csv/userMassa.csv", numLinesToSkip = 1, delimiter = ',')

    public void testPostUserDDT(

            // Parâmetros
            int id,
            String username,
            String firstName,
            String lastName,
            String email,
            String password,
            String phone,
            int userStatus) {

        // Instanciando o objeto User.java
        User user = new User();

        // Relacionando os atributos do objeto com os parâmetros do método
        user.id = id;
        user.username = username;
        user.firstName = firstName;
        user.lastName = lastName;
        user.email = email;
        user.password = password;
        user.phone = phone;
        user.userStatus = userStatus;

        // Instanciando a biblioteca Gson e convertendo o objeto User.java em uma string JSON
        Gson gson = new Gson();
        String jsonBody = gson.toJson(user);

        given() // Dado que
                .contentType(ct)
                .log().all()
                .body(jsonBody)

                .when() // Quando
                .post(baseURL + "/user")

                .then() // Então
                .log().all()

                // Comparando o código de requisição
                .statusCode(200)
        ;
    }
}