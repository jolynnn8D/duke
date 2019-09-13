package duke.util;

import duke.command.Command;
import duke.command.AddCommand;
import duke.command.DeleteCommand;
import duke.command.DoneCommand;
import duke.command.FindCommand;
import duke.command.ListCommand;
import duke.exception.DukeException;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;

import java.time.LocalDateTime;

/**
 * Contains all methods that parses user input and returns Commands that interact with TaskList and Storage to store
 * user input.
 */
public class Parser {

    /**
     * Parses a String representing user input of time and returns a LocalDateTime object representing
     * the input time.
     *
     * @param date String representing time in the format of DDDD/MM/YY HHMM.
     * @return LocalDateTime object representing input time.
     */
    public static LocalDateTime formatDate(String date) {
        String[] splitDateTime = date.split(" ", 2);
        String[] splitDate = splitDateTime[0].split("/", 3);
        System.out.println(splitDate[0]);
        LocalDateTime local = LocalDateTime.of(
                Integer.parseInt(splitDate[2]),  Integer.parseInt(splitDate[1]),  Integer.parseInt(splitDate[0]),
                Integer.parseInt(splitDateTime[1].substring(0, 2)),
                Integer.parseInt(splitDateTime[1].substring(2, 4)));
        return local;

    }

    /**
     * Parses a string representing one line of user input and returns a Command object that executes the
     * necessary commands triggered by the users.
     *
     * @param input String representing a line of user input.
     * @return Command object (e.g AddCommand, DoneCommand, DeleteCommand) based on the user command
     * @throws DukeException If user input is not recognised as a valid command.
     */
    public static Command parse(String input) throws DukeException {
            String arr[] = input.split(" ", 2);
            String command = arr[0];
            Command resultCommand;
            switch (command) {
            case "list":
                resultCommand = new ListCommand();
                break;
            case "done":
                if (arr.length == 1) {
                    throw new DukeException("OOPS!!! Please enter a task number to check as done e.g done 1");
                } else {
                    int num = Integer.parseInt(arr[1]);
                    resultCommand = new DoneCommand(num);
                }
                break;
            case "todo":
                if (arr.length == 1) {
                    throw new DukeException("OOPS!!! The description of a todo cannot be empty.");
                } else {
                    resultCommand = new AddCommand(new Task("T", arr[1]));
                }
                break;
            case "deadline":
                if (arr.length == 1) {
                    throw new DukeException("OOPS!!! The description of a deadline cannot be empty.");
                } else {
                    String[] deadline = arr[1].split(" /by ", 2);
                    if (deadline.length == 1) {
                        throw new DukeException("OOPS!!! Please enter a due date. e.g complete homework /by 1 Jan");
                    } else {
                        resultCommand = new AddCommand(new Deadline("D", deadline[0], formatDate(deadline[1])));
                    }
                }
                break;
            case "event":
                if (arr.length == 1) {
                    throw new DukeException("OOPS!!! The description of an event cannot be empty.");
                } else {
                    String[] event = arr[1].split(" /at ", 2);
                    if (event.length == 1) {
                        throw new DukeException("OOPS!!! Please enter an event date. e.g group meeting /at 1 Jan");
                    } else {
                        resultCommand = new AddCommand(new Event("E", event[0], formatDate(event[1])));
                    }
                }
                break;
            case "delete":
                if (arr.length == 1) {
                    throw new DukeException("OOPS!!! Please enter a task number to delete e.g delete 1");
                } else {
                    int num = Integer.parseInt(arr[1]);
                    resultCommand = new DeleteCommand(num);
                }
                break;
            case "find":
                if (arr.length == 1) {
                    throw new DukeException("OOPS!!! Please enter a keyword to find!");
                } else {
                    String phraseToFind = arr[1];
                    resultCommand = new FindCommand(phraseToFind);
                }
                break;
            default:
                throw new DukeException("OOPS!!! I'm sorry, but I don't know what that means :-(");
            }
            return resultCommand;
    }
}
