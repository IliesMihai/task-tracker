package src.main.java.command;

import src.main.java.repository.TaskRepository;

public class HistoryCommand extends Command {
    
    private final CommandHistory history;

    public HistoryCommand(TaskRepository taskRepository, CommandHistory history) {
        super(taskRepository);
        this.history = history;
    }

    @Override
    public void execute() {
        history.showHistory();
    }
    
    @Override
    public String getDescription() {
        return "Show command history";
    }
}