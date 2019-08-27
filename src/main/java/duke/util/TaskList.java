package duke.util;

import java.util.ArrayList;
import duke.task.Task;

/**
 * Handles all operations on the task list and tasks in the task list.
 */
public class TaskList {
    protected ArrayList<Task> taskList;

    /**
     * Initializes an empty TaskList.
     */
    public TaskList() {
        taskList = new ArrayList<>();
    }

    /**
     * Initializes a new TaskList with some pre-existing tasks.
     *
     * @param from      An ArrayList containing some Task objects
     */
    public TaskList(ArrayList<Task> from) {
        taskList = from;
    }

    /**
     * Returns the size of the task list.
     *
     * @return  Size of current task list
     */
    public int getSize() {
        return taskList.size();
    }

    /**
     * Returns a Task object from the task list by index. If the index is invalid, this
     * method will throw an assertion error.
     *
     * @param index     1-indexed value indicating position in the task list to return
     * @return          Task object at position index
     */
    public Task getTask(int index) {
        assert index > 0 && index <= taskList.size();
        return taskList.get(index - 1);
    }

    /**
     * Adds a new Task to the back of the task list.
     *
     * @param toAdd     The Task object to add
     */
    public void addNewTask(Task toAdd) {
        taskList.add(toAdd);
    }

    /**
     * Deletes the task given by index. If the index is invalid, this method will throw
     * an assertion error.
     *
     * @param index     1-indexed value indicating position of the task to delete
     */
    public void deleteTask(int index) {
        assert index > 0 && index <= taskList.size();
        taskList.remove(index - 1);
    }

    /**
     * Marks the task given by index as done. If the index is invalid, this method will throw
     * an assertion error.
     *
     * @param index     1-indexed value indicating position of the task to mark
     */
    public void markTaskAsDone(int index) {
        assert index > 0 && index <= taskList.size();
        taskList.get(index - 1).markAsDone();
    }

    /**
     * Checks if the description of a task given by index contains a given filter string.
     * If the index is invalid, this method will throw an assertion error. This search is
     * case-insensitive.
     *
     * @param index     1-indexed value indicating position of the task to check
     * @param filter    The string to check
     * @return          True if task description contains filter string
     */
    public boolean taskDescriptionContains(int index, String filter) {
        assert index > 0 && index <= taskList.size();
        String descriptionLower = taskList.get(index - 1).getDescription().toLowerCase();
        return descriptionLower.contains(filter.toLowerCase());
    }

    /**
     * Returns the entire task list. This method is intended for storage purposes only.
     *
     * @return      An ArrayList containing the current task list
     */
    ArrayList<Task> getFullTaskList() {
        return taskList;
    }
}
