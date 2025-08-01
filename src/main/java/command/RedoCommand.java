package src.main.java.command;

import src.main.java.repository.TaskRepository;

public class RedoCommand extends Command {
    
    private final CommandHistory history;

    public RedoCommand(TaskRepository taskRepository, CommandHistory history) {
        super(taskRepository);
        this.history = history;
    }

    @Override
    public void execute() {
        history.redo();
    }
    
    @Override
    public String getDescription() {
        return "Redo last undone command";
    }
}