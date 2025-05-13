package org.isep.cleancode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Todo {
    private String name;
    private String dueDate;

    public Todo(String name) {
        this.name = name;
    }

    public Todo(String name, String dueDate) {
        this.name = name;
        this.dueDate = dueDate;
    }

    public String getName() {
        return name;
    }

    public String getDueDate() {
        return dueDate;
    }

    public boolean nameIsValid() {
        return name != null && !name.trim().isEmpty() && name.length() <= 64;
    }

    public boolean dueDateIsValid() {
        if (dueDate == null) {
            return true; // dueDate is optional
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);

        try {
            Date dueDateObject = dateFormat.parse(dueDate);
            return dueDateObject.after(new Date());
        } catch (ParseException e) {
            return false;
        }
    }

    public boolean isValid() {
        return nameIsValid() && dueDateIsValid();
    }
}
