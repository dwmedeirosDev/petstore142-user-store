// Tratamento de erro
import java.io.IOException;

// Imports da leitura do JSON
import java.nio.file.Files;
import java.nio.file.Paths;

// Import do @Test
import org.junit.jupiter.api.Test;

public class TestUser {
    static String ct = "aplication/json";
    static String uriPet = "petstore.swagger.io/v2/user";
    static String petId = "118650010"; // Número fixo sem necessidade de criar em forma de "int"

    // Leitura do JSON
    public static String readFileJson(String fileJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileJson)));
    }

    @Test

    // POST
    public void testPostUser() {

    }
}
