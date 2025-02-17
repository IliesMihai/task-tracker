package src.main.java.json;

import src.main.java.model.Status;
import src.main.java.model.Task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class JsonProcessorImpl implements JsonProcessor {


    @Override
    public String extractJsonValue(String json, String key) {
        Pattern pattern = Pattern.compile("\"" + key + "\":\"?(.*?)\"?(,|})");
        Matcher matcher = pattern.matcher(json);
        return matcher.find() ? matcher.group(1) : "Missing value for key: " + key;
    }


    @Override
    public String serializeJsonArray(List<Task> tasks) {
        return "[" + tasks.stream().map(this::serializeTask).collect(Collectors.joining(",")) + "]";
    }

    @Override
    public Task parseTask(String json) {
        Long id = Long.valueOf(extractJsonValue(json, "id"));
        String description = extractJsonValue(json, "description");
        Status status = Status.valueOf(extractJsonValue(json, "status"));
        LocalDateTime createdAt = LocalDateTime.parse(extractJsonValue(json, "createdAt"));
        LocalDateTime updatedAt = LocalDateTime.parse(extractJsonValue(json, "updatedAt"));
        return new Task(id, description, status, createdAt, updatedAt);
    }

    @Override
    public List<Task> parseJsonArray(String json) {
        List<Task> tasks = new ArrayList<>();
        if (!json.equals("[]")) {
            String[] entries = json.substring(1, json.length() - 1).split("},");
            for (String entry : entries) {
                entry = entry.endsWith("}") ? entry : entry + "}";
                tasks.add(parseTask(entry));
            }
        }
        return tasks;
    }

    private String serializeTask(Task task) {
        return "\n{" +
                "\n\"id\":\"" + task.getId() + "\"," +
                "\n\"description\":\"" + task.getDescription() + "\"," +
                "\n\"status\":\"" + task.getStatus() + "\"," +
                "\n\"createdAt\":\"" + task.getCreatedAt() + "\"," +
                "\n\"updatedAt\":\"" + task.getUpdatedAt() + "\"" +
                "\n}" ;
    }
}
