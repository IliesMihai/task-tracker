package src.main.java;

import src.main.java.command.Command;
import src.main.java.command.CommandFactory;
import src.main.java.command.CommandHistory;
import src.main.java.repository.TaskRepository;
import src.main.java.repository.TaskRepositoryImpl;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        TaskRepository jsonRepository = new TaskRepositoryImpl();
        CommandHistory history = new CommandHistory();
        CommandFactory commandFactory = new CommandFactory(jsonRepository, scanner, history);

        System.out.println("Welcome to the Java Task CLI App! Type 'help' to see commands.");

        while (isRunning) {
            System.out.print("task-cli > ");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("exit")) {
                handleExit();
                isRunning = false;
            } else {
                handleCommand(input, commandFactory, history);
            }
        }
        scanner.close();
    }

    public static void handleCommand(String input, CommandFactory commandFactory, CommandHistory history) {
        Command command = commandFactory.createCommand(input);
        
        if (command != null) {
            if (input.equals("undo") || input.equals("redo") || input.equals("history")) {
                // These commands handle themselves and shouldn't go through history
                command.execute();
            } else {
                // Regular commands go through history for undo/redo tracking
                history.executeCommand(command);
            }
        } else {
            System.out.println("Unknown command. Type 'help' for a list of commands.");
        }
    }

    private static void handleExit() {
        System.out.println("Exiting... Goodbye!");
    }
}