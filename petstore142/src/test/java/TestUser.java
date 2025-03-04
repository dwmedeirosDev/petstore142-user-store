
// Comparadores do Hamcrest
import static org.hamcrest.Matchers.is;

// Função given
import static io.restassured.RestAssured.given;

// Tratamento de erro
import java.io.IOException;

// Imports da leitura do JSON
import java.nio.file.Files;
import java.nio.file.Paths;

// Ordem dos @Test
import org.junit.jupiter.api.Order;

// Import do @Test
import org.junit.jupiter.api.Test;

public class TestUser {
    static String ct = "application/json";
    static String baseURL = "https://petstore.swagger.io/v2";

    static int id = 118650010;
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
                .post(baseURL + "/" + "user")

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

        ;
    }
}
