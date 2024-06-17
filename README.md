# Java exercises

Exercises I did to practice my Java skills.

## N2At3

Atividade da faculdade usando sockets para emular comunicação entre Client e Server.
Usamos Maven para importar a biblioteca GSON para fazer parsing de arquivos JSON.
Para rodar o programa, rode o arquivo Main.java.

## Concurrent merge sort

Merge sort algorithm that runs concurrently using Thread inheritance and join to wait for subthreads to finish running. Each time the main array is split into a subarray a new thread is created with the responsibility to sort the subarray.

A lot like recursive merge sort, but instead of calling the sort function recursively, I create a thread for each time I'd call the function recursively. Then I just wait for the subthreads to sort their arrays and then merge.
