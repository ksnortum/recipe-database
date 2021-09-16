package recipes.businesslayer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.List;

@Converter
public class ArrayListConverter implements AttributeConverter<List<String>, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<String> recipeInfo) {

        String recipeInfoJson = null;
        try {
            recipeInfoJson = objectMapper.writeValueAsString(recipeInfo);
        } catch (final JsonProcessingException e) {
            e.printStackTrace();
        }

        return recipeInfoJson;
    }

    @Override
    public List<String> convertToEntityAttribute(String recipeInfoJSON) {

        List<String> recipeInfo = null;
        try {
            recipeInfo = objectMapper.readValue(recipeInfoJSON, List.class);
        } catch (final IOException e) {
            e.printStackTrace();
        }

        return recipeInfo;
    }

}