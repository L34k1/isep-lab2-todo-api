package org.isep.cleancode.persistence.csvfiles;

import org.isep.cleancode.Todo;
import org.isep.cleancode.application.ITodoRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TodoCsvFilesRepository implements ITodoRepository {
    private final Path csvFilePath;

    public TodoCsvFilesRepository() {
        String appData = System.getenv("APPDATA");
        Path storageDir = Paths.get(appData, "TodoApp");
        this.csvFilePath = storageDir.resolve("todos.csv");

        try {
            Files.createDirectories(storageDir);
            if (!Files.exists(csvFilePath)) {
                Files.createFile(csvFilePath);
                // Write header
                Files.writeString(csvFilePath, "name,dueDate\n");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize CSV storage", e);
        }
    }

    @Override
    public void addTodo(Todo todo) {
        try {
            String line = String.format("\"%s\",%s\n",
                    todo.getName(),
                    todo.getDueDate() != null ? "\"" + todo.getDueDate() + "\"" : "");
            Files.writeString(csvFilePath, line, java.nio.file.StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save todo", e);
        }
    }

    @Override
    public List<Todo> getAllTodos() {
        try {
            List<String> lines = Files.readAllLines(csvFilePath);
            List<Todo> todos = new ArrayList<>();

            // Skip header line
            for (int i = 1; i < lines.size(); i++) {
                String[] parts = lines.get(i).split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                String name = parts[0].replaceAll("^\"|\"$", "");
                String dueDate = parts.length > 1 ? parts[1].replaceAll("^\"|\"$", "") : null;

                if (dueDate != null && dueDate.isEmpty()) {
                    dueDate = null;
                }

                todos.add(new Todo(name, dueDate));
            }
            return todos;
        } catch (IOException e) {
            throw new RuntimeException("Failed to read todos", e);
        }
    }

    @Override
    public boolean nameExists(String name) {
        return getAllTodos().stream()
                .anyMatch(todo -> todo.getName().equals(name));
    }
}