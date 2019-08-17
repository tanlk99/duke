class Parser {
    public static Command parseInput(String rawInput) {
        String commandPhrase = rawInput.split(" ", 2)[0];
        Command command = null;

        switch (commandPhrase) {
        case "bye":
            command = new ExitCommand();
            break;
        case "list":
            command = new ListCommand();
            break;
        case "done":
            int index = Integer.parseInt(rawInput.split(" ", 2)[1]) - 1;
            command = new DoneCommand(index);
            break;
        case "todo":
        case "event":
        case "deadline":
            command = new AddCommand(rawInput);
            break;
        default:
        }

        return command;
    }

    public static Task parseTask(String rawInput) {
        String taskType = rawInput.split(" ", 2)[0];
        String taskRawDesc = rawInput.split(" ", 2)[1];
        String taskDesc;
        String taskTime;

        Task newTask = null;

        switch (taskType) {
        case "todo":
            newTask = new ToDo(taskRawDesc);
            break;
        case "deadline":
            taskDesc = taskRawDesc.split(" /by ", 2)[0];
            taskTime = taskRawDesc.split(" /by ", 2)[1];
            newTask = new Deadline(taskDesc, taskTime);
            break;
        case "event":
            taskDesc = taskRawDesc.split(" /at ", 2)[0];
            taskTime = taskRawDesc.split(" /at ", 2)[1];
            newTask = new Event(taskDesc, taskTime);
            break;
        default:
        }

        return newTask;
    }
}
