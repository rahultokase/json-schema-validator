import com.fasterxml.jackson.databind.JsonNode;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.ValidationMessage;

import java.io.IOException;
import java.util.Set;

public class Sample extends BaseJsonSchemaValidatorTest{

    public static void main(String args[]) throws IOException {

        JsonSchema schema = getJsonSchemaFromStringContent("{\"enum\":[1, 2, 3, 4],\"enumErrorCode\":\"Not in the list\"}");
        JsonNode node = getJsonNodeFromStringContent("7");
        Set<ValidationMessage> errors = schema.validate(node);

        System.out.println(errors);

        // With automatic version detection
        JsonNode schemaNode = getJsonNodeFromStringContent(
                "{\"$schema\": \"https://cms.salesforce.com/types/sfdc_cms__core\", \"properties\": { \"id\": {\"type\": \"number\"}}}");
        schema = getJsonSchemaFromJsonNode(schemaNode);

        schema.initializeValidators(); // by default all schemas are loaded lazily. You can load them eagerly via
        // initializeValidators()

        node = getJsonNodeFromStringContent("{\"id\": \"2\"}");
        errors = schema.validate(node);

        System.out.println(errors);
    }
}
