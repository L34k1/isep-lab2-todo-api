package org.isep.cleancode;

import static spark.Spark.*;

import org.isep.cleancode.application.ITodoRepository;
import org.isep.cleancode.persistence.csvfiles.TodoCsvFilesRepository;
import org.isep.cleancode.presentation.TodoController;

public class Main {
    public static void main(String[] args) {
        // Use CSV file repository
        ITodoRepository repository = new TodoCsvFilesRepository();
        TodoController todoController = new TodoController(repository);

        port(4567);

        get("/todos", todoController::getAllTodos);
        post("/todos", todoController::createTodo);

        after((req, res) -> res.type("application/json"));
    }
}