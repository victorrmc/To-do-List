package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;


public class Services {
    boolean running = true;
    Scanner sc = new Scanner(System.in);
    ArrayList<Task> taskList = new ArrayList<>();
    List<Task> taskToDo;
    List<Task> taskInProgress;
    ObjectMapper objectMapper = new ObjectMapper();
    public final File file = new File("ToDoList.json");

    public void runToDoList(){
        ReadTheFile();
        while (running){
            handlerOption();
        }
    }
    public void handlerOption() {
        try {
            int option = Integer.parseInt(input(Menus.listMenu()));

            switch (option) {
                case 1 -> listAllTask();
                case 2 -> {
                    taskToDo = listTaskByState(State.TO_DO);
                    handlerOptionToDo();
                }
                case 3 -> {
                    taskInProgress = listTaskByState(State.IN_PROGRESS);
                    handlerOptionInProgress();
                }
                case 4 -> {
                    listTaskByState(State.DONE);
                    handlerOptionDone();
                }
                case 5 -> addNewTask();
                case 6 -> deleteTask();
                case 7 -> {
                    writeTheFile(taskList);
                    running = false;
                }
                default -> throw new IllegalArgumentException("Opción inválida");
            }
        } catch (NumberFormatException e) {
            System.out.println("Por favor, ingrese un número válido.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
    public void handlerOptionToDo(){
        boolean isValidOption = false;
        while (!isValidOption) {
            String option = input(Menus.listMenuToDo());
            switch (option){
                case "1":
                    markTaskInProgress();
                    isValidOption = true;
                    break;
                case "2":
                    isValidOption = true;
                    break;
                default:
                    System.out.println("Insert a correct option");
                    break;
            }
        }
    }
    public void handlerOptionInProgress(){
        String option = input(Menus.listMenuInProgress());
        switch (option){
            case "1":
                markTaskDone();
                break;
            case "2":
                break;
            default:
                System.out.println("Insert a correct option");
                break;
        }
    }
    public void handlerOptionDone(){
        String option = input(Menus.listMenuDone());
        switch (option){
            case "1":
                markTaskDone();
                break;
            case "2":
                break;
            default:
                System.out.println("Insert a correct option");
                break;
        }
    }
    public List<Task> listTaskByState(State state){
        List<Task> filteredTasks = taskList.stream()
                .filter(task -> task.getState() == state).toList();
        for (int i = 0; i < filteredTasks.size(); i++) {
            System.out.println( i + ". " + filteredTasks.get(i));
        }
        return filteredTasks;
    }
    public void listAllTask(){
        taskList.forEach(task -> System.out.println(task.toString()));
    }
    public void addNewTask(){
        String taskDescription = input("Type the description of the task:");
        Task task = new Task(taskDescription, State.TO_DO);
        taskList.add(task);
        writeTheFile(taskList);
    }
    public void markTaskInProgress(){
        try {
            String option = input("Type the task number of the list");
            taskToDo.get(Integer.parseInt(option)).setState(State.IN_PROGRESS);
            System.out.println("Task marked In Progress:" + taskToDo.get(Integer.parseInt(option)).getDescription());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Enter a list index");
        }
    }
    public void markTaskDone(){
        try {
        String option = input("Type the task number of the list");
        taskToDo.get(Integer.parseInt(option)).setState(State.DONE);
        System.out.println("Task marked Done:" + taskToDo.get(Integer.parseInt(option)).getDescription());
    } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Enter a list index");
    }
    }
    public void deleteTask(){
        String option = input("Type the task number of the list");
        taskToDo.get(Integer.parseInt(option)).setState(State.DONE);
        System.out.println("Task marked Done:" + taskToDo.get(Integer.parseInt(option)).getDescription());
    }
    public String input(String text){
        System.out.println(text);
        return sc.nextLine();
    }
    public void writeTheFile(List<Task> taskList){
        try (FileWriter fw = new FileWriter(file);
             PrintWriter pw = new PrintWriter(fw);
             ) {
            String jsonString = objectMapper.writeValueAsString(taskList);
            pw.println(jsonString);
        } catch (IOException e) {
            System.out.println("No se ha encontrado con el fichero");
            throw new RuntimeException(e);
        }
    }
    public void ReadTheFile(){
        try(
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferReader = new BufferedReader(fileReader)
                ) {
            String line;
            StringBuilder fullText = new StringBuilder();
            while((line = bufferReader.readLine()) != null){
                fullText.append(line);
            }
            taskList =  objectMapper.readValue( fullText.toString(), new TypeReference<ArrayList<Task>>(){});
            //tranformar el texto a una serializacion del objeto
            //guardar en el arraylist
        } catch (FileNotFoundException e) {
            System.out.println("No se ha encontrado con el fichero");
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
