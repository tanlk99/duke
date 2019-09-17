# Duck User Guide

Duck is a portable lightweight task manager. You can manage your tasks in Duck using only
a command line, without any need for complex GUI interfaces.

## Table of Contents

- [Requirements](#requirements)
- [Features](#features)
   - [Simple and intuitive user interface](#simple-and-intuitive-user-interface)
   - [Manages multiple types of tasks](#manages-multiple-types-of-tasks)
   - [Saves your updates automatically](#saves-your-updates-automatically)
   - [Archives your tasks](#archives-your-tasks)
- [Usage](#usage)
   - [Adding a new task](#adding-a-new-task)
   - [Listing all tasks](#listing-all-tasks)
   - [Marking a task as done](#marking-a-task-as-done)
   - [Deleting a task](#deleting-a-task)
   - [Finding a task by description](#find-a-task-by-description)
   - [Archiving multiple tasks](#archiving-multiple-tasks)
   - [Listing all archived tasks](#listing-all-archived-tasks)
   - [Changing configuration settings](#changing-configuration-settings)
   - [Exiting Duck](#exiting-duck)

## Requirements

- Java 11 or later

## Features 

### Simple and intuitive user interface 

Interacting with Duke is just like interacting with one of your friends on social media.
To issue a command, simply type it in the command bar and press 'Enter' (or click the 'Send' button).

![](Ui.png)

### Manages multiple types of tasks

Duck isn't limited to a single type of task. Duck can manage to-dos, deadlines and events all at the same time!

### Saves your updates automatically

Duck saves your task list automatically to `data/cache/duck-cache.txt` after each update.
If you don't like the location, don't worry! Type `set cachePath [new-path]` in the command line, then restart Duck.
The next time it runs, it will use a file at your new location.

> Note: Ensure that there is no file already at the specified location.
> Otherwise, Duck will not be able to use the file.

### Archives your tasks

If you hate to see a cluttered list, Duck can archive specific tasks by deleting them from your tasks list
and saving them to an archive file. This archive file is by default at `data/archive/duck-archive.txt`, but you can
change this using a `set archivePath [new-path]` command.

## Usage

### Adding a new task

Adds a new task to your task list.

<strong>Command format</strong>

`todo [description]` <br>
`deadline [description] /by [time]` <br>
`event [description] /at [time]` <br>

<strong>Usage examples</strong>

- `todo wash dishes`<br>
Adds a new to-do with description `wash dishes`.

- `deadline math homework /by 10-10-2019`<br>
Adds a new deadline with description `math homework` by time `10-10-2019`.

- `event team meeting /at 13-10-2019`<br>
Adds a new event with description `team meeting` at time `13-10-2019`.

***

### Listing all tasks

Lists all tasks currently in your list.

<strong>Command format</strong>

`list` <br>

***

### Marking a task as done

Marks a task in your list as done.

<strong>Command format</strong>

`done [index]` <br>

<strong>Usage example</strong>

- `done 1`<br>
Marks the first task in your list as done.

***

### Deleting a task

Deletes a task in your list. 

> Warning: Deleted tasks will not be saved and are lost forever.

<strong>Command format</strong>

`delete [index]` <br>

<strong>Usage example</strong>

- `delete 1`<br>
Deletes the first task in your list.

***

### Finding a task by description

Finds all tasks in your list whose description contains a search string.

<strong>Command format</strong>

`find [search-string]` <br>

<strong>Usage example</strong>

- `find project`<br>
Finds all tasks in your list whose description contains the string
`project`, and displays them in a list.

***

### Archiving multiple tasks

Archives some tasks in your list. These tasks are deleted from your task list
and saved in the archive file.

<strong>Command format</strong>

`archive add [index1] [index2] ...`<br>
`archive add *`<br>
`archive add all`

> Duck also accepts `insert` as a command word instead of `add`.

<strong>Usage examples</strong>

- `archive add 1 3 4`<br>
Archives the 1st, 3rd and 4th task in your list.

- `archive add *` or `archive add all`<br>
Archives all the tasks in your list. In other words, it transfers your task list to the archive file.

***

### Listing all archived tasks

Lists all the tasks in the archive file.

<strong>Command format</strong>

`archive list`

> Duck also accepts `archive view` for the same command.

***

### Changing configuration settings

Changes Duck's configuration settings.

<strong>Command format</strong>

`set cachePath [new-cache-path]`<br>
`set archivePath [new-archive-path]`

<strong>Usage examples</strong>

- `set cachePath new-cache-file.txt`<br>
Sets the cache location to `new-cache-file.txt`.

- `set archivePath new-archive-file.txt`<br>
Sets the archive location to `new-cache-file.txt`.

> Note: Paths are given relative to the root directory, i.e. the directory where the
> JAR file is placed.

***

### Exiting Duck

Exits the Duck interface.

<strong>Command format</strong>

`bye`