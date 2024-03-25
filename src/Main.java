import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        //Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Task Manager CLI!");
        System.out.println("Type 'help' to see available commands.");
        //String command;
        TaskManager taskManager = new TaskManager();
        while (true){
            System.out.print("Enter a command: ");
            String input = scanner.nextLine();

            String[] commandsPart = input.split(" ");
            String command = commandsPart[0].toLowerCase();
            switch (command){
                case  "add" :
                   ;
                    System.out.print("Enter task title: ");
                    String title = scanner.nextLine();

                    System.out.print("Enter task description: ");
                    String description = scanner.nextLine();

                    LocalDate dueDate = null;
                    boolean validDate = false;
                    while (!validDate) {
                        System.out.print("Enter task due date [dd.MM.yyyy](XX.XX.XXXX): ");
                        String dueDateInput = scanner.nextLine();
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                        try {
                            dueDate = LocalDate.parse(dueDateInput, dtf);
                            validDate = true;
                        } catch (Exception e) {
                            System.out.println("Invalid date format. Please enter the date in the format [dd.MM.yyyy].");
                        }
                    }
                    Task task = new Task(title,description,dueDate,false);
                    taskManager.addTask(task);
                    System.out.println("Task Added Successfully");
                    break;

                case "list":
                    List<Task> tasks = taskManager.getTasks();
                    if(tasks.isEmpty()){
                        System.out.println("No task found.");
                    }else{
                        System.out.println("Tasks : ");
                        for (Task eachtask : tasks){
                            System.out.println(eachtask);
                        }
                    }
                    break;
                case "view":
                    executeCommandWithID(commandsPart,"view",taskManager);
                    break;
                case "complete" :
                   executeCommandWithID(commandsPart,"complete",taskManager);
                    break;
                case "update":
                    executeCommandWithID(commandsPart,"update",taskManager);
                    break;
                case "delete":
                    executeCommandWithID(commandsPart,"delete",taskManager);
                    break;
                case "save":
                    taskManager.saveTasksToFile("tasks.txt");
                    break;
                case "load":
                    taskManager.loadTasksFromFile("tasks.txt");
                    break;
                case "help":
                    System.out.println("Available Commands:\n" +
                            "- add: Add a new task\n" +
                            "- list: List all tasks\n" +
                            "- view [task_id]: View details of a task\n" +
                            "- complete [task_id]: Mark a task as completed\n" +
                            "- update [task_id]: Update task details\n" +
                            "- delete [task_id]: Delete a task\n" +
                            "- save: Save tasks to a file\n" +
                            "- load: Load tasks from a file\n" +
                            "- help: Show available commands\n" +
                            "- exit: Exit the program");
                    break;
                case "exit":
                    System.out.println("Exiting Task Manager CLI. Goodbye!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println(command+": command not found.");
                    break;
            }

        }
    }
    private static void executeCommandWithID(String[] commandsPart, String commandType, TaskManager taskManager) {
        if (commandsPart.length == 2) {
            int taskID = Integer.parseInt(commandsPart[1]);

            if (commandType.equals("view")) {
                Task taskToView = taskManager.viewTask(taskID);
                if (taskToView != null) {
                    System.out.println("Task Details:");
                    System.out.println("Title: " + taskToView.getTitle());
                    System.out.println("Description: " + taskToView.getDescription());
                    System.out.println("Due Date: " + taskToView.getDueDate());
                    System.out.println("Status: " + (taskToView.isCompleted() ? "Completed" : "Incomplete"));
                } else {
                    System.out.println("Task not found.");
                }
            } else if (commandType.equals("complete")) {
                taskManager.completedTask(taskID);
                System.out.println("Marked as complete");
            }
            else if(commandType.equals("update")){
                System.out.print("Enter task title: ");
                String title = scanner.nextLine();

                System.out.print("Enter task description: ");
                String description = scanner.nextLine();

                LocalDate dueDate = null;
                boolean validDate = false;
                while (!validDate) {
                    System.out.print("Enter task due date [dd.MM.yyyy](XX.XX.XXXX): ");
                    String dueDateInput = scanner.nextLine();
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                    try {
                        dueDate = LocalDate.parse(dueDateInput, dtf);
                        validDate = true;
                    } catch (Exception e) {
                        System.out.println("Invalid date format. Please enter the date in the format [dd.MM.yyyy].");
                    }
                }
                taskManager.updateTask(taskID,title,description,dueDate);
                System.out.println("Updated Successfully!");
            } else if (commandType.equals("delete")) {
                boolean isDeleted = taskManager.deleteTask(taskID);
                if(isDeleted){
                    System.out.println("Task deleted successfully!");
                }else{
                    System.out.println("Task not found.");
                }
            }
        } else {
            System.out.println("Invalid command format. Use '" + commandType + " [task_id]'.");
        }
    }

}
