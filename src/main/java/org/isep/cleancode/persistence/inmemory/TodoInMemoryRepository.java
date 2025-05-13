package org.isep.cleancode.persistence.inmemory;

import org.isep.cleancode.Todo;
import org.isep.cleancode.application.ITodoRepository;

import java.util.ArrayList;
import java.util.List;

public class TodoInMemoryRepository implements ITodoRepository {
    private final List<Todo> todos = new ArrayList<>();

    public void addTodo(Todo todo) {
        todos.add(todo);
    }

    public List<Todo> getAllTodos() {
        return new ArrayList<>(todos); // Return a copy to avoid external modification
    }

    public boolean nameExists(String name) {
        return todos.stream().anyMatch(todo -> todo.getName().equals(name));
    }
}