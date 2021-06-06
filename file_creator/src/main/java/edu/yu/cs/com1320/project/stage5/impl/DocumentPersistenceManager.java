
package edu.yu.cs.com1320.project.stage5.impl;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;
import edu.yu.cs.com1320.project.stage5.Document;
import edu.yu.cs.com1320.project.stage5.PersistenceManager;

import javax.print.Doc;
import java.io.*;
import java.lang.reflect.Type;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * created by the document store and given to the BTree via a call to BTree.setPersistenceManager
 */
public class DocumentPersistenceManager implements PersistenceManager<URI, Document> {

    private File baseDir;

    private JsonSerializer<Document> serializer = new JsonSerializer<Document>() {
        @Override
        public JsonElement serialize(Document document, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonObject element = new JsonObject();
            Gson gson = new Gson();
            element.addProperty("Text", document.getDocumentAsTxt());
            element.addProperty("URI", gson.toJson(document.getKey()));
            element.addProperty("Hashcode", document.getDocumentTextHashCode());
            element.addProperty("WordMap", gson.toJson((document.getWordMap())));
            return element;
        }
    };

    private JsonDeserializer<Document> deserializer = new JsonDeserializer<Document>() {
        @Override
        public Document deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String text = jsonElement.getAsJsonObject().get("Text").getAsString();
            URI uri = null;
            String map = jsonObject.get("WordMap").getAsString();
            HashMap<String, Integer> wordMap = null;

            try{
                String uriText = gson.fromJson(jsonElement.getAsJsonObject().get("URI").getAsString(), String.class);
                uri = new URI(uriText);
                Type wordsMapType = new TypeToken<HashMap<String, Integer>>() {}.getType();
                wordMap = gson.fromJson(map, wordsMapType);
            } catch (Exception e) {
                e.printStackTrace();
            }
            int hashCode = jsonElement.getAsJsonObject().get("Hashcode").getAsInt();
            Document document = new DocumentImpl(uri, text, hashCode);
            document.setWordMap(wordMap);
            return document;
        }
    };

    public DocumentPersistenceManager(File baseDir){
        if (baseDir == null){
            this.baseDir = new File(System.getProperty("user.dir"));
        } else {
            this.baseDir = baseDir;
        }

    }

    @Override
    public void serialize(URI uri, Document val) throws IOException {
        String text = uri.getHost() + uri.getPath();
        String fileText = text.replace("/", File.separator);
        String filePathText = baseText + File.separator + fileText + ".json";
        System.out.println(filePathText);

        Path path = Paths.get(filePathText);
        Files.createDirectories(path.getParent());
        if(!Files.exists(path)) {
            Files.createFile(path);
        }
        Gson gson = new GsonBuilder().registerTypeAdapter(Document.class, serializer).setPrettyPrinting().create();

        try {
            FileWriter writer = new FileWriter(filePathText, false);
            JsonWriter jw = new JsonWriter(writer);

            gson.toJson(val, Document.class, jw);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Document deserialize(URI uri) throws IOException {
        String text = uri.getHost() + uri.getPath();
        String fileText = text.replace("/", File.separator);
        String baseText = baseDir.getPath(); //might need toString() instead
        String uriString = File.separator + fileText + ".json";
        String filePathText = baseText + uriString;

        File fakeFile = new File(filePathText);
        boolean fileTest = fakeFile.exists();
        if (!fileTest){
            return null;
        }

        Document doc = null;

        Gson gson = new GsonBuilder().registerTypeAdapter(Document.class, deserializer).create();
        try {

            FileReader reader = new FileReader(filePathText);
            Object object = JsonParser.parseReader(reader);
            JsonObject jsonObject = (JsonObject) object;
            String newText = jsonObject.toString();
            doc = gson.fromJson(newText, Document.class);
            reader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        File file = new File(filePathText);
        file.delete();
        this.deleteDirectories(filePathText);

        return doc;
    }

    private void deleteDirectories(String filePath){
        while (!filePath.equals(this.baseDir.toString())){
            File file = new File(filePath);
            String previous = file.getParent();
            file.delete();
            filePath = previous;
        }
    }
}