package com.gabrielabalmeida.apigist;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * Created by gabriela on 04/01/18.
 */

public class ObjDeserializerGist implements JsonDeserializer<ObjetoFile> {

        @Override
        public ObjetoFile deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

            JsonObject jsonObject = (JsonObject) json;

            ObjetoFile obj = new ObjetoFile();
            obj.gists = new ArrayList<>();

        Set<Map.Entry<String, JsonElement>> entries = jsonObject.entrySet();//will return members of your object
        for (Map.Entry<String, JsonElement> entry : entries) {
            JsonObject gistFileObject = (JsonObject) entry.getValue();
            Arquivo file = new Arquivo();
            file.nomeArquivo = getStrValue(gistFileObject, "filename");
            file.tipo = getStrValue(gistFileObject, "type");
            file.linguagem = getStrValue(gistFileObject, "language");
            file.url = getStrValue(gistFileObject, "raw_url");
            file.tamanho = gistFileObject.get("size").getAsInt();
            obj.gists.add(file);
        }

        return obj;
    }

    public String getStrValue(JsonObject obj, String key) {
        //Some times the value is null so, with this function an empty string is returned
        try {
            return obj.get(key).getAsString();
        } catch (Exception e) {
            return "";
        }
    }
}