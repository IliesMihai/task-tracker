package src.main.java.command;

import java.util.ArrayList;
import java.util.List;

public class CommandHistory {
    private final List<Command> executedCommands = new ArrayList<>();
    private final List<Command> undoneCommands = new ArrayList<>();
    private static final int MAX_HISTORY_SIZE = 50;

    public void executeCommand(Command command) {
        command.execute();
        
        if (command.isUndoable()) {
            executedCommands.add(command);
            undoneCommands.clear(); // Clear redo stack when new command is executed
            
            // Limit history size
            if (executedCommands.size() > MAX_HISTORY_SIZE) {
                executedCommands.remove(0);
            }
        }
    }

    public boolean canUndo() {
        return !executedCommands.isEmpty();
    }

    public boolean canRedo() {
        return !undoneCommands.isEmpty();
    }

    public void undo() {
        if (canUndo()) {
            Command lastCommand = executedCommands.remove(executedCommands.size() - 1);
            lastCommand.undo();
            undoneCommands.add(lastCommand);
        } else {
            System.out.println("Nothing to undo.");
        }
    }

    public void redo() {
        if (canRedo()) {
            Command commandToRedo = undoneCommands.remove(undoneCommands.size() - 1);
            commandToRedo.execute();
            executedCommands.add(commandToRedo);
        } else {
            System.out.println("Nothing to redo.");
        }
    }

    public void showHistory() {
        if (executedCommands.isEmpty()) {
            System.out.println("No command history.");
            return;
        }
        
        System.out.println("Command History (most recent first):");
        for (int i = executedCommands.size() - 1; i >= 0; i--) {
            System.out.println((executedCommands.size() - i) + ". " + executedCommands.get(i).getDescription());
        }
        
        if (!undoneCommands.isEmpty()) {
            System.out.println("\nUndone commands (available for redo):");
            for (int i = undoneCommands.size() - 1; i >= 0; i--) {
                System.out.println((undoneCommands.size() - i) + ". " + undoneCommands.get(i).getDescription());
            }
        }
    }
}