package org.isep.cleancode.application;

import org.isep.cleancode.Todo;

import java.util.List;

public interface ITodoRepository {
        void addTodo(Todo todo);
        List<Todo> getAllTodos();
        boolean nameExists(String name);

}
