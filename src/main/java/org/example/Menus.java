package org.example;

public class Menus {
    public static String listMenu(){
        return "==To do List== \n" +
                "1. List all the tasks \n" +
                "2. List all task to do \n" +
                "3. List all task in progress \n" +
                "4. List all task done \n" +
                "5. Add new task \n" +
                "6. Delete a task \n" +
                "7. Exit \n" +
                "Chose a option \n";
    }
    public static String listMenuToDo(){
        return "==To do List== \n" +
                "1. Mark a task in progress \n" +
                "2. Back \n" +
                "Chose a option \n";
    }
    public static String listMenuInProgress(){
        return  "==To do List== \n" +
                "1. Mark a task done \n" +
                "2. Back \n" +
                "Chose a option \n";
    }
    public static String listMenuDone(){
        return  "==To do List== \n" +
                "1. Delete a task \n" +
                "2. Back \n" +
                "Chose a option \n";
    }
}
