import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.ValidationMessage;
import com.networknt.schema.ValidationResult;

import java.io.IOException;
import java.util.Set;

public class RecursiveSchemaValidator extends BaseJsonSchemaValidatorTest {

    public static void main(String args[]) throws IOException {

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        ObjectMapper mapper = new ObjectMapper();

        JsonNode schemaFromFile = mapper.readTree(classloader.getResource("schema.json"));
        JsonSchema schema = getJsonSchemaFromJsonNode(schemaFromFile);
        JsonNode node = mapper.readTree(classloader.getResource("data.json"));

        Set<ValidationMessage> errors = schema.validate(node);
        ValidationResult walk = schema.walk(node, true);
        Set<ValidationMessage> validationMessages = walk.getValidationMessages();

        System.out.println("Errors: " + errors);
        System.out.println("ValidationMessages: " + validationMessages);
    }
}
