package licenta.backend.configuration;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Converter
public class ByteArrayConverter implements AttributeConverter<List<byte[]>, String> {

    @Override
    public String convertToDatabaseColumn(List<byte[]> attribute) {
        // Convert the list of byte arrays to a single string
        StringBuilder sb = new StringBuilder();
        for (byte[] bytes : attribute) {
            sb.append(Base64.getEncoder().encodeToString(bytes));
            sb.append(";");
        }
        return sb.toString();
    }

    @Override
    public List<byte[]> convertToEntityAttribute(String dbData) {
        // Convert the string to a list of byte arrays
        List<byte[]> byteArrayList = new ArrayList<>();
        String[] base64Strings = dbData.split(";");
        for (String base64String : base64Strings) {
            byte[] bytes = Base64.getDecoder().decode(base64String);
            byteArrayList.add(bytes);
        }
        return byteArrayList;
    }

}