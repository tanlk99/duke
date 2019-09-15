package duck.util;

import java.util.ArrayList;
import java.util.Collections;
import duck.task.Task;

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
     * Returns a list of Task objects given by a list of indexes. If any index is invalid,
     * this method will throw an assertion error.
     *
     * @param indexes   List of 1-indexed values indicating position in the task list to return
     * @return          A list of Task objects at the specified positions
     */
    public ArrayList<Task> getTasks(ArrayList<Integer> indexes) {
        ArrayList<Task> result = new ArrayList<>();

        for (int index : indexes) {
            assert index > 0 && index <= taskList.size();
            result.add(taskList.get(index - 1));
        }

        return result;
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
     * Deletes the tasks given by a list of indexes. If any index is invalid, this method will throw
     * an assertion error.
     *
     * @param indexes   List of 1-indexed values indicating position of the task to delete
     */
    public void deleteTasks(ArrayList<Integer> indexes) {
        Collections.sort(indexes);
        assert indexes.get(0) > 0 && indexes.get(0) <= taskList.size();
        assert indexes.get(indexes.size() - 1) > 0 && indexes.get(indexes.size() - 1) <= taskList.size();

        for (int i = indexes.size() - 1; i >= 0; i--) { //remove from the back
            taskList.remove(indexes.get(i) - 1);
        }
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
        String taskDescriptionLower = taskList.get(index - 1).getDescription().toLowerCase();
        return taskDescriptionLower.contains(filter.toLowerCase());
    }

    /**
     * Returns the entire task list. This method is intended for file storage purposes only.
     *
     * @return      An ArrayList containing the current task list
     */
    ArrayList<Task> getAllTasks() {
        return taskList;
    }
}
