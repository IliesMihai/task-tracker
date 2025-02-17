package src.main.java.json;

import src.main.java.model.Task;

import java.util.List;

public interface JsonProcessor {

    String extractJsonValue(String json, String key);

    String serializeJsonArray(List<Task> tasks);

    Task parseTask(String json);

    List<Task> parseJsonArray(String json);

}
