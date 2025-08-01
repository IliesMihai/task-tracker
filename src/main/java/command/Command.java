package src.main.java.command;

import src.main.java.repository.TaskRepository;

public abstract class Command {

    protected TaskRepository taskRepository;
    protected boolean isUndoable = false;

    public Command(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public abstract void execute();
    
    public void undo() {
        throw new UnsupportedOperationException("This command does not support undo");
    }
    
    public boolean isUndoable() {
        return isUndoable;
    }
    
    public abstract String getDescription();
}
