package duke.stubs;

import java.util.ArrayList;
import duke.util.TaskList;
import duke.task.Task;

/**
 * Simplified version of {@link duke.util.TaskList TaskList}. TaskListStub has fixed matching patterns
 * and task names. Only one task can be marked as done.
 */
public class TaskListStub extends TaskList {
    private int taskListSize;
    private int matchType = 0; //type of task indices to match
    private int markedDonePosition = -1;

    /**
     * Creates a new TaskListStub with <i>taskListSize</i> tasks in the task list.
     *
     * @param taskListSize  number of tasks in the task list
     */
    public TaskListStub(int taskListSize) {
        super();
        this.taskListSize = taskListSize;
    }

    /**
     * Sets the match type of {@link #taskDescriptionContains(int, String) taskDescription}.
     * List of match types used: 0 - match all, 1 - match none, 2 - match odd indices, 3 - match last
     *
     * @param matchType type of matching to use
     */
    public void setMatchType(int matchType) {
        this.matchType = matchType;
    }

    @Override
    public Task getTask(int index) {
        assert index <= taskListSize && index > 0;
        TaskStub taskToReturn = new TaskStub("task" + index);
        if (index == markedDonePosition) {
            taskToReturn.setDone(true);
        }

        return taskToReturn;
    }

    @Override
    public ArrayList<Task> getTasks(ArrayList<Integer> indexes) {
        ArrayList<Task> tasks = new ArrayList<>();
        for (int index : indexes) {
            assert index <= taskListSize && index > 0;
            TaskStub taskToReturn = new TaskStub("task" + index);
            if (index == markedDonePosition) {
                taskToReturn.setDone(true);
            }
            tasks.add(taskToReturn);
        }

        return tasks;
    }

    @Override
    public void addNewTask(Task task) {
        taskListSize += 1;
    }

    @Override
    public void deleteTask(int index) {
        assert index <= taskListSize && index > 0;
        taskListSize -= 1;
    }

    @Override
    public void deleteTasks(ArrayList<Integer> indexes) {
        assert taskListSize >= indexes.size(); //indexes array assumed unique
        taskListSize -= indexes.size();
    }

    @Override
    public void markTaskAsDone(int index) { //assume only called once for each test
        assert index <= taskListSize && index > 0;
        markedDonePosition = index;
    }

    @Override
    public boolean taskDescriptionContains(int index, String filter) {
        boolean isMatch;

        switch (matchType) {
        case 0: //match all
            isMatch = true;
            break;
        case 1: //match none
            isMatch = false;
            break;
        case 2: //match only odd
            isMatch = (index % 2 == 1);
            break;
        default: //match only index taskListSize
            isMatch = (index == taskListSize);
        }

        return isMatch;
    }

    @Override
    public int getSize() {
        return taskListSize;
    }

    ArrayList<Task> getAllTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        for (int index = 1; index <= taskListSize; index++) {
            TaskStub taskToReturn = new TaskStub("task" + index);
            if (index == markedDonePosition) {
                taskToReturn.setDone(true);
            }
            tasks.add(taskToReturn);
        }

        return tasks;
    }
}
