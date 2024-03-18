# Java exercises from my uni course

Exercises I did to practice my Java skills.

## Concurrent merge sort

Merge sort algorithm that runs concurrently using Thread inheritance and join to wait for subthreads to finish running. Each time the main array is split into a subarray a new thread is created with the responsibility to sort the subarray.

A lot like recursive merge sort, but instead of calling the sort function recursively, I create a thread for each time I'd call the function recursively. Then I just wait for the subthreads to sort their arrays and then merge.
