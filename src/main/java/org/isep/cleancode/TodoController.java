package org.isep.cleancode.presentation;

import com.google.gson.Gson;
import org.isep.cleancode.Todo;
import org.isep.cleancode.application.TodoManager;
import org.isep.cleancode.persistence.inmemory.TodoInMemoryRepository;
import spark.Request;
import spark.Response;

import java.util.List;

public class TodoController {
    private static final Gson gson = new Gson();
    private final TodoManager todoManager;

    public TodoController() {
        // Use in-memory repository for Step 1
        this.todoManager = new TodoManager(new TodoInMemoryRepository());
    }

    public Object getAllTodos(Request req, Response res) {
        res.type("application/json");
        List<Todo> todos = todoManager.getAllTodos();
        return gson.toJson(todos);
    }

    public Object createTodo(Request req, Response res) {
        try {
            Todo newTodo = gson.fromJson(req.body(), Todo.class);

            if (newTodo == null || !newTodo.isValid()) {
                res.status(400);
                return "Invalid Todo: Name must be 1-64 characters and due date (if provided) must be in yyyy-MM-dd format and in the future";
            }

            todoManager.addTodo(newTodo);
            res.status(201);
            res.type("application/json");
            return gson.toJson(newTodo);
        } catch (Exception e) {
            res.status(500);
            return "Error creating todo: " + e.getMessage();
        }
    }
}
