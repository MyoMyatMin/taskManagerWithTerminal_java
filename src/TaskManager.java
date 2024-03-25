import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private List<Task> tasks;
    private int currentID ;
    public TaskManager() {
        this.tasks = new ArrayList<>();
        this.currentID = 1;
    }

    public void addTask(Task task){
        task.setId(currentID);
        tasks.add(task);
        currentID++;
    }

    public Task viewTask(int i){
        for(Task task: tasks){
            if (task.getId() == i){
                return task;
            }
        }
        return null;
    }

    public void completedTask(int id){
        for(Task task: tasks){
            if (task.getId() == id){
            task.setCompleted(true);
        }
    }}

    public List<Task> getTasks() {
        return tasks;
    }

    public void updateTask(int taskID, String title, String description, LocalDate dueDate) {
        Task task = viewTask(taskID);
        if(task != null) {
            task.setTitle(title);
            task.setDescription(description);
            task.setDueDate(dueDate);
        }else{
            System.out.println("Task not found.");
        }
    }

    public boolean deleteTask(int taskID) {
        Task task = viewTask(taskID);
        if(task != null){
            tasks.remove(task);
            return true;
        }
        return false;
    }

    public void saveTasksToFile(String file) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))){
            for(Task task : tasks){
                writer.write(taskToFileString(task));
                writer.newLine();
            }
            System.out.println("Save tasks to file successfully!");
        }catch (IOException e){
            System.out.println("There was an error while saving tasks to the file.");
        }
    }

    private String taskToFileString(Task task) {
        return String.format("%d,%s,%s,%s,%b", task.getId(), task.getTitle(), task.getDescription(), task.getDueDate(), task.isCompleted());
    }

    public void loadTasksFromFile(String file) {
        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            while ((line = reader.readLine()) != null){
                Task task = parseTaskFromString(line);
                if(task != null){
                    addTask(task);
                }
            }

            System.out.println("Task loaded form the file successfully.");
        }catch (IOException e){
            System.out.println("There was an error while loading tasks from files.");
        }
    }

    private Task parseTaskFromString(String line){
        String[] parts = line.split(",");
        if(parts.length == 5){
           // int id = Integer.parseInt(parts[0]);
            String title = parts[1];
            String description  = parts[2];
            LocalDate dueDate = LocalDate.parse(parts[3]);
            boolean isCompleted = Boolean.parseBoolean(parts[4]);
            return new Task(title,description,dueDate,isCompleted);
        }
        return null;
    }
}
