import com.fasterxml.jackson.databind.JsonNode;
import com.networknt.schema.*;

import java.util.Collections;
import java.util.Set;

public class GroovyKeyword extends AbstractKeyword {

    public GroovyKeyword() {
        super("groovy");
    }

    @Override
    public AbstractJsonValidator newValidator(String schemaPath, JsonNode schemaNode, JsonSchema parentSchema, ValidationContext validationContext) throws JsonSchemaException, Exception {
        // you can read validator config here
        String config = schemaNode.asText();
        return new AbstractJsonValidator(this.getValue()) {
            @Override
            public Set<ValidationMessage> validate(JsonNode node, JsonNode rootNode, String at) {
                return Collections.emptySet();
            }
        };
    }
}
