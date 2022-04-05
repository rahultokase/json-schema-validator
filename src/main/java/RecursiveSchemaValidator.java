import com.fasterxml.jackson.databind.JsonNode;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.ValidationMessage;
import com.networknt.schema.ValidationResult;

import java.io.IOException;
import java.util.Set;

public class RecursiveSchemaValidator extends BaseJsonSchemaValidatorTest {

    public static void main(String args[]) throws IOException {

        JsonSchema schema = getJsonSchemaFromStringContent("{\"title\":\"Example Schema\",\"type\":\"object\",\"definitions\":{\"SomeInterfaceListType\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/definitions/SomeInterface\"}},\"SomeInterface\":{\"type\":\"object\",\"properties\":{\"a\":{\"$ref\":\"#/definitions/SomeInterfaceListType\"},\"b\":{\"type\":\"string\"}},\"required\":[\"b\"]}},\"properties\":{\"firstName\":{\"$ref\":\"#/definitions/SomeInterface\"},\"lastName\":{\"type\":\"string\"}},\"additionalProperties\":false,\"required\":[\"firstName\",\"lastName\"]}");
        // JsonNode node = getJsonNodeFromStringContent("{\"firstName\":{\"a\":{\"b\":\"rahul\"}},\"lastName\":\"tokase\"}");
        JsonNode node = getJsonNodeFromStringContent("{\"firstName\":{\"a\":[{}],\"b\":\"b\"},\"lastName\":\"tokase\"}");
        // JsonNode node = getJsonNodeFromStringContent("{\"firstName\":{\"a\":{\"b\":\"rahul\"},\"b\":\"b\"},\"lastName\":\"tokase\"}");
        Set<ValidationMessage> errors = schema.validate(node);
        ValidationResult walk = schema.walk(node, true);
        Set<ValidationMessage> validationMessages = walk.getValidationMessages();

        System.out.println(errors);
        System.out.println(validationMessages);
    }
}
