import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import com.fasterxml.jackson.databind.SerializationFeature;

public class File_IO {

    private static class LocalDateDeserializer implements JsonDeserializer<LocalDate> {
        private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        @Override
        public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String dateStr = json.getAsString();
            return LocalDate.parse(dateStr, formatter);
        }
    }
    public ArrayList<Input> readData(String fileName) throws IOException {

        ArrayList<Input> array;

        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new File_IO.LocalDateDeserializer()).create();

        try(Reader reader = new FileReader(fileName)){
            array = gson.fromJson(reader, new TypeToken<ArrayList<Input>>(){}.getType());

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        catch (IOException e) {
            throw new RuntimeException(e);
        }

        return array;
    }

    public void printData(ArrayList<Output> outputList, String fileName){

        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            objectMapper.writeValue(new File(fileName), outputList);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
