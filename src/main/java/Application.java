package src.main.java;

import src.main.java.repository.TaskRepositoryImpl;
import src.main.java.model.Task;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        TaskRepositoryImpl jsonRepository = new TaskRepositoryImpl();

        System.out.println("Welcome to the Java Task CLI App! Type 'help' to see commands.");

        while (isRunning) {
            System.out.print("task-cli > ");
            String input = scanner.nextLine().trim().toLowerCase();
            Long id;

            switch (input) {
                case "help":
                    System.out.println("Available commands:");
                    System.out.println("  - help : Show available commands");
                    System.out.println("  - list : List all tasks");
                    System.out.println("  - add : Add a new task");
                    System.out.println("  - update : Update a task");
                    System.out.println("  - mark-in-progress : Mark a task as \"in progress\"");
                    System.out.println("  - mark-done : Mark a task as \"done\"");
                    System.out.println("  - delete : Delete a task");
                    System.out.println("  - exit : Exit the application");
                    break;

                case "list":
                    System.out.println("Tasks:");
                    System.out.println(jsonRepository.getAllTasks());
                    break;

                case "add":
                    Task task = new Task();
                    System.out.println("Enter the task description:");
                    task.setDescription(scanner.nextLine());
                    jsonRepository.addTask(task);
                    System.out.println("Task added: " + task.getDescription());
                    break;

                case "update":
                    System.out.println("Enter the task id:");
                    id = Long.parseLong(scanner.nextLine());
                    System.out.println("Enter the new task description:");
                    var description = scanner.nextLine();
                    if (!jsonRepository.checkIfTaskExists(id)){
                        System.out.println("Task not found. Run \"list\" to see all tasks.");
                        break;
                    }
                    jsonRepository.updateTaskDescription(id, description);
                    System.out.println("Task updated.");
                    break;

                case "mark-in-progress":
                    System.out.println("Enter the task id to mark as \"in progress\":");
                    id = Long.parseLong(scanner.nextLine());
                    if (!jsonRepository.checkIfTaskExists(id)){
                        System.out.println("Task not found. Run \"list\" to see all tasks.");
                        break;
                    }
                    jsonRepository.updateTaskStatus((id), "IN_PROGRESS");
                    System.out.println("Task status updated.");
                    break;

                case "mark-done":
                    System.out.println("Enter the task id to mark as \"done\":");
                    id = Long.parseLong(scanner.nextLine());
                    if (!jsonRepository.checkIfTaskExists(id)){
                        System.out.println("Task not found. Run \"list\" to see all tasks.");
                        break;
                    }
                    jsonRepository.updateTaskStatus((id), "DONE");
                    System.out.println("Task status updated.");
                    break;

                case "delete":
                    System.out.println("Enter the task id to delete:");
                    jsonRepository.deleteTask(Long.parseLong(scanner.nextLine()));
                    System.out.println("Task deleted.");
                    break;

                case "exit":
                    System.out.println("Exiting... Goodbye!");
                    isRunning = false;
                    break;

                default:
                    System.out.println("Unknown command. Type 'help' for a list of commands.");
            }
        }
        scanner.close();
    }
}