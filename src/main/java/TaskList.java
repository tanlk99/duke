import java.util.ArrayList;

public class TaskList {
    ArrayList<Task> taskList;

    /**
     * Initializes an empty TaskList.
     */
    public TaskList() {
        taskList = new ArrayList<>();
    }

    /**
     * Initializes a new TaskList with some pre-existing tasks.
     *
     * @param from      an ArrayList containing some Task objects
     */
    public TaskList(ArrayList<Task> from) {
        taskList = from;
    }

    /**
     * Returns the size of the task list.
     *
     * @return  size of current task list
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
    public Task getTask(int index) throws DukeException {
        assert index > 0 && index <= taskList.size();
        return taskList.get(index - 1);
    }

    /**
     * Adds a new Task to the back of the task list.
     *
     * @param toAdd     the Task object to add
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
    public void deleteTask(int index) throws DukeException {
        assert index > 0 && index <= taskList.size();
        taskList.remove(index - 1);
    }

    /**
     * Returns the entire task list. This method is intended for storage purposes only.
     *
     * @return      an ArrayList containing the current task list
     */
    public ArrayList<Task> getFullTaskList() {
        return taskList;
    }
}
