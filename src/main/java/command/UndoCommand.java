package src.main.java.command;

import src.main.java.repository.TaskRepository;

public class UndoCommand extends Command {
    
    private final CommandHistory history;

    public UndoCommand(TaskRepository taskRepository, CommandHistory history) {
        super(taskRepository);
        this.history = history;
    }

    @Override
    public void execute() {
        history.undo();
    }
    
    @Override
    public String getDescription() {
        return "Undo last command";
    }
}