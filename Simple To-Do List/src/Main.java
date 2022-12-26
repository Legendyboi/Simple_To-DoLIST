import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> tasks = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        boolean run = true;

        File file = new File("tasks.txt");
        if (file.exists()) {
            try (Scanner fileScanner = new Scanner(file)) {
                while (fileScanner.hasNextLine()) {
                    String task = fileScanner.nextLine();
                    tasks.add(task);
                }
            } catch (IOException e) {
                System.out.println("Error reading from the text file: " + e.getMessage());
            }
        }

        while (run) {
            String menuOptions = """
                    Please choose an option:
                    1. Add task
                    2. Remove task
                    3. Mark task as complete
                    4. View all tasks
                    5. Quit
                    """;

            System.out.print(menuOptions);

            int option = 0;
            try {
                option = scanner.nextInt();
                scanner.nextLine(); // consume the newline character
            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid input. Please enter a valid number.");
                scanner.nextLine(); // consume the invalid input
                continue;
            }

            switch (option) {
                case 1:
                    // Add task
                    System.out.println("Enter the task to add:");
                    String task = scanner.nextLine();
                    tasks.add(task);

                    try (PrintWriter writer = new PrintWriter(new FileOutputStream(file, true))) {
                        writer.println(task);
                    } catch (IOException e) {
                        System.out.println("Error writing to the text file: " + e.getMessage());
                    }

                    System.out.println("Task added.");
                    break;
                case 2:
                    // Remove task
                    System.out.println("Enter the index of the task to remove:");
                    int index = -1;
                    try {
                        index = scanner.nextInt();
                        scanner.nextLine(); // consume the newline character
                    } catch (InputMismatchException e) {
                        System.out.println("Error: Invalid input. Please enter a valid number.");
                        scanner.nextLine(); // consume the invalid input
                        break;
                    }
                    if (index < 1 || index > tasks.size()) {
                        System.out.println("Error: Invalid task index. Please enter a valid number between 1 and " + tasks.size() + ".");
                        break;
                    }
                    tasks.remove(index - 1);

                    try (PrintWriter writer = new PrintWriter("tasks.txt")) {
                        for (String t : tasks) {
                            writer.println(t);
                        }
                    } catch (IOException e) {
                        System.out.println("Error writing to the text file: " + e.getMessage());
                    }

                    System.out.println("Task removed.");
                    break;
                case 3:
                    // Mark task as complete
                    System.out.println("Enter the index of the task to mark as complete:");
                    index = -1;
                    try {
                        index = scanner.nextInt();
                        scanner.nextLine(); // consume the newline character
                    } catch (InputMismatchException e) {
                        System.out.println("Error: Invalid input. Please enter a valid number.");
                        scanner.nextLine(); // consume the invalid input
                        break;
                    }
                    if (index < 1 || index > tasks.size()) {
                        System.out.println("Error: Invalid task index. Please enter a valid number between 1 and " + tasks.size() + ".");
                        break;
                    }
                    String completedTask = tasks.get(index - 1);
                    tasks.remove(index - 1);
                    completedTask = "[COMPLETED] " + completedTask;
                    tasks.add(completedTask);

                    try (PrintWriter writer = new PrintWriter("tasks.txt")) {
                        for (String t : tasks) {
                            writer.println(t);
                        }
                    } catch (IOException e) {
                        System.out.println("Error writing to the text file: " + e.getMessage());
                    }

                    System.out.println("Task marked as complete.");
                    break;
                case 4:
                    // View all tasks
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + ". " + tasks.get(i));
                    }
                    break;
                case 5:
                    run = false;
                    break;
                default:
                    System.out.println("Invalid option.");
                    break;
            }
        }

        scanner.close();
    }
}
