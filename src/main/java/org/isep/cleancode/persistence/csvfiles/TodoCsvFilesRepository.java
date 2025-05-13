package org.isep.cleancode.persistence.csvfiles;

import org.isep.cleancode.Todo;
import org.isep.cleancode.application.ITodoRepository;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TodoCsvFilesRepository implements ITodoRepository {
    // placeholder, no RW methods
    // Todo objects to and from CSV files.
    private final File file= new File("todos.csv");
    @Override
    public void addTodo(Todo todo) {
        // Implementation for adding a Todo to a CSV file

        if(!this.file.exists()){
            try {
                FileWriter fileWriter = new FileWriter(this.file);
                fileWriter.write("Name,DueDate\n");
                fileWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            FileWriter fileWriter = new FileWriter(this.file, true);
            fileWriter.write(todo.getName() + "," + todo.getDueDate() + "\n");

            fileWriter.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Todo> getAllTodos() {
        // Implementation for retrieving all Todos from a CSV file
        if (!this.file.exists()) {
            return new ArrayList<>();
        }
        try {
            // Read the CSV file and parse it into a list of Todo objects
            ArrayList<Todo> todos = new ArrayList<>();
            FileReader fileReader = new FileReader(this.file);
            StringBuilder sb = new StringBuilder();
            int c;
            while ((c = fileReader.read()) != -1) {
                sb.append((char) c);
            }
            String[] lines = sb.toString().split("\n");
            for (int i = 1; i < lines.length; i++) {
                String[] parts = lines[i].split(",");
                String name = parts[0];
                String dueDate = parts.length<2?"":parts[1];
                Todo todo = new Todo(name, dueDate);
                todos.add(todo);

            }
            return todos;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean nameExists(String name) {
        // Implementation for checking if a Todo name exists in the CSV file
        List<Todo> todos = this.getAllTodos();
        if(todos.isEmpty()){
            return false;
        }
        return todos.stream().anyMatch(todo -> todo.getName().equals(name));
    }
}
