package org.isep.cleancode.persistence;

import org.isep.cleancode.Todo;
import org.isep.cleancode.application.ITodoRepository;
import java.util.ArrayList;
import java.util.List;

public abstract class TodoRepository implements ITodoRepository {
    private final List<Todo> todos = new ArrayList<>();

    @Override
    public List<Todo> getAllTodos() {
        return new ArrayList<>(todos);
    }

    @Override
    public boolean nameExists(String name) {
        return false;
    }

    @Override
    public void addTodo(Todo todo) {
        todos.add(todo);
    }

}
