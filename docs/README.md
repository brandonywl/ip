# User Guide

## Features 
- Single Word Commands
    - Exit
    - List
    - Save
- Double Word Commands
    - Done
    - Delete
    - Find
    - Todo
- Triple Word Commands
    - Deadline
    - Event

### Single Word Commands
#### Exit
Exits Duke.

Command:
```
exit
```

Expected:
```
bye
------------------------------------------------
Bye. Hope to see you again soon!
------------------------------------------------
```

#### List
Prints all the current tasks in Duke.

Command:
```
list
```

Example:
```
list
------------------------------------------------
Here are the tasks we found!

1.	[E][✗] CS2113 lecture on Friday 18 September 2020 (at: 4 pm to 6pm)
2.	[T][✗] tutorial for cs2113
3.	[E][✗] buy dinner (at: 2pm)
4.	[D][✗] cs2113 ip user guide (by: 9pm)
5.	[D][✗] homework (by: 2pm)
------------------------------------------------
```

#### Save
Saves the entire list of tasks to dump/dump.txt

Command:
```
save
```

Expected:
```
save
------------------------------------------------
Done!
------------------------------------------------
```

### Double Word Commands
#### Done
Sets a specific task to be completed.
Input index should be the one from list command.

Command:
```
done {index printed}
```

Expected:
```
done  2
------------------------------------------------
Nice! I've marked this task as done:
	[T][✓] tutorial for cs2113
------------------------------------------------
```

#### Delete
Deletes a specific task.
Input index should be the one from the list command.

Command:
```
delete {index printed}
```

Example:
```
delete 2
------------------------------------------------
Noted. I have removed this task:
[T][✓] cs2113
Now you have 4 tasks in the list.
------------------------------------------------
```

#### Find
Prints all tasks that have the specified keywords in the task descriptions.
Search is case insensitive.

Command:
```
find {keyword}
```

Example:
```
find cs2113
------------------------------------------------
Here are the tasks we found!

1.  [E][✗] CS2113 lecture on Friday 18 September 2020 (at: 4 pm to 6pm)
2.	[T][✗] tutorial for cs2113
3.	[D][✗] cs2113 ip user guide (by: 9pm)
------------------------------------------------
```

#### Todo
Creates a Todo task and store it in Duke.
Holds a description and the current status of the task.

Command:
```
todo {description}
```

Expected:
```
todo cs2113 tp repository
------------------------------------------------
Got it. I've added this task:
	[T][✗] cs2113 tp repository
Now you have 5 tasks in the list.
------------------------------------------------

```

### Triple Word Commands
Triple word commands requires a specfier after the descriptor before specifying a time for the tasks.
#### Deadline
Creates a Deadline task and store it in Duke.
Holds a description, the current status of the task as well as a deadline that the task needs to be completed by.

Specifier: /by

Command:
```
deadline {descriptor} /by {deadline time}
```

Expected:
```
deadline cs2113 jar file /by 2 Oct 2359 hrs
------------------------------------------------
Got it. I've added this task:
	[D][✗] cs2113 jar file (by: 2 Oct 2359 hrs)
Now you have 6 tasks in the list.
------------------------------------------------
```

#### Event
Creates an Event task and store it in Duke.
Holds a description, the current status of the task as well as a time that the event should start at.

Specifier: /at

Command:
```
event {descriptor} /at {event time}
```

Expected:
```
event cs2113 tp meeting /at Monday 6pm
------------------------------------------------
Got it. I've added this task:
	[E][✗] cs2113 tp meeting (at: Monday 6pm)
Now you have 7 tasks in the list.
------------------------------------------------
```
