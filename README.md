# SchedulingCS140 with Hopfield Network Logic

## Overview

This repository contains a Java GUI application, SchedulingCS140, which implements a heuristic algorithm to solve the Unconstrained Examination Timetabling Problem. The goal is to distribute N exams within T timeslots such that no student has more than one exam in each timeslot, achieving an optimal solution when T is minimal.

The project was initially developed by Professor Suren Khachatryan (email: skhachat@aua.am) and extended with Hopfield network logic implemented by myself.

I extended the original project by integrating Hopfield network logic for improved scheduling. Specifically, I implemented the `Autoassociator` class, which is a Hopfield network to manage the exam scheduling process. Additionally, I extended the `CourseArray` class by adding the method `getTimeSlot(int index)` to return the specified timeslot as an array of 1 and -1 values.

I ran the original algorithm for various values of shifts, iterations, and slots, observing and summarizing the behavior in a log file. This log file records how these changes affect the number of clashes and is continuously updated in the repository. Furthermore, I created an instance of `Autoassociator` as a member of the `TimeTable` class and trained it with clash-free timeslots observed during the experiments. The used timeslots are saved in a log file with details on slots, shifts, iterations, and timeslot indices.

After training the autoassociator, I re-ran the algorithm using the trained autoassociator, experimenting with different strategies to minimize clashes. Each update instance is saved in a log file, documenting the strategies used and their effects on the number of clashes.

## Instructions

1. **Load Clashes**: Use the "Load" button to load clashes from a specified file.
2. **Specify Parameters**: Set the values for timeslots (T), exams (N), iterations (n), and shifts (s) in the respective input boxes.
3. **Start Scheduling**: Press the "Start" button to begin scheduling from the initial state.
4. **Step Through Iterations**: Use the "Step" button to run a single iteration from the current state.
5. **Print Configuration**: Press the "Print" button to print the current configuration in the Java console.
6. **Exit**: Press the "Exit" button to close the application.
7. **Continue**: Use the "Continue" button to proceed with the current configuration.
8. **Train**: Press the "Train" button to train the autoassociator with clash-free timeslots.
9. **Print Timeslot**: Use the "Print Timeslot" button to print the current timeslot configuration.
10. **Update**: Press the "Update" button to re-run the algorithm with unit updates using the trained autoassociator.

## Files

- `Autoassociator.java`: Implementation of the Hopfield network logic.
- `CourseArray.java`: Extended to include the `getTimeSlot` method.
- `TimeTable.java`: Includes the `Autoassociator` instance and training logic.
- `log.txt`: Contains observations and updates from running the scheduling algorithm and training the autoassociator.
