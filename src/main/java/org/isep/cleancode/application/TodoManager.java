package org.isep.cleancode.application;

import org.isep.cleancode.Todo;
import java.util.List;

public class TodoManager {
    private final ITodoRepository todoRepository;

    public TodoManager(ITodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public void addTodo(Todo todo) throws IllegalArgumentException {
        if (!todo.nameIsValid()) {
            throw new IllegalArgumentException(
                    "Todo name invalid. Must be non-empty and shorter or equal than 63 characters.");
        }

        if (todoRepository.nameExists(todo.getName())) {
            throw new IllegalArgumentException("Todo name must be unique.");
        }

        if (todo.getDueDate() != null && !todo.getDueDate().isBlank() && !todo.dueDateIsValid()) {
            throw new IllegalArgumentException(
                    "Due date invalid. Must be a valid date in the future.");
        }

        todoRepository.addTodo(todo);
    }

    public List<Todo> getAllTodos() {
        return todoRepository.getAllTodos();
    }
}