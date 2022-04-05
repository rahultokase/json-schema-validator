import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

public class BaseJsonSchemaValidatorTest {


    private static ObjectMapper mapper = new ObjectMapper();

    protected JsonNode getJsonNodeFromClasspath(String name) throws IOException {
        InputStream is1 = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(name);
        return mapper.readTree(is1);
    }

    protected static JsonNode getJsonNodeFromStringContent(String content) throws IOException {
        return mapper.readTree(content);
    }

    protected JsonNode getJsonNodeFromUrl(String url) throws IOException {
        return mapper.readTree(new URL(url));
    }

    protected JsonSchema getJsonSchemaFromClasspath(String name) {
        JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V4);
        InputStream is = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(name);
        return factory.getSchema(is);
    }

    protected static JsonSchema getJsonSchemaFromStringContent(String schemaContent) {
        JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V4);
        return factory.getSchema(schemaContent);
    }

    protected JsonSchema getJsonSchemaFromUrl(String uri) throws URISyntaxException {
        JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V4);
        return factory.getSchema(new URI(uri));
    }

    protected static JsonSchema getJsonSchemaFromJsonNode(JsonNode jsonNode) {

        JsonSchemaFactory factory = mySchemaFactory().getInstance();
        return factory.getSchema(jsonNode);
    }

    // Automatically detect version for given JsonNode
    protected static JsonSchema getJsonSchemaFromJsonNodeAutomaticVersion(JsonNode jsonNode) {
        JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersionDetector.detect(jsonNode));
        return factory.getSchema(jsonNode);
    }

    protected static JsonSchemaFactory mySchemaFactory() {
        String URI = "https://cms.salesforce.com/types/sfdc_cms__core";
        String ID = "$id";
        List<Format> BUILTIN_FORMATS = new ArrayList<>(JsonMetaSchema.COMMON_BUILTIN_FORMATS);
        JsonMetaSchema myJsonMetaSchema =  new JsonMetaSchema.Builder(URI).idKeyword(ID).addFormats(BUILTIN_FORMATS).addKeywords(ValidatorTypeCode.getNonFormatKeywords(SpecVersion.VersionFlag.V201909))
                // keywords that may validly exist, but have no validation aspect to them
                .addKeywords(Arrays.asList(
                        new NonValidationKeyword("$schema"),
                        new NonValidationKeyword("$id"),
                        new NonValidationKeyword("title"),
                        new NonValidationKeyword("description"),
                        new NonValidationKeyword("default"),
                        new NonValidationKeyword("definitions"),
                        new NonValidationKeyword("$defs")  // newly added in 2018-09 release.
                ))
                // add your custom keyword
                .addKeyword(new GroovyKeyword())
                .build();

        return new JsonSchemaFactory.Builder().
                 defaultMetaSchemaURI(myJsonMetaSchema.getUri())
                .addMetaSchema(myJsonMetaSchema).forceHttps(false)
                .build();
    }
}
