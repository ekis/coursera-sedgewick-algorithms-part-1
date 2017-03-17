# Algorithms 

Implementations of most significant algorithms and some of their applications.
All implementations are in Java, closely following the book "Algorithms, 4th ed." by Sedgewick, R. and Wayne, K. (2012 edition).

Codebase also mirrors the Coursera course on Algorithms, structuring code in weeks. 
Build is done via [SBT](http://www.scala-sbt.org/download.html), and uses automatic dependency management - except for [algs4.jar](http://algs4.cs.princeton.edu/code/algs4.jar) which is an unmanaged dependency.

## Current index:

1. (Week 1) Union-find
  - [x] Percolation (solves dynamic connectivity problem using Weighted Quick UF in linearithmic time)
2. (Week 2) Elementary data structures
  - [x] Simple Stack (backed by a singly-linked list, LIFO)
  - [x] Stack with Maximum (stack which tracks the maximum element currently on stack)
  - [x] Simple Queue (backed by doubly-linked, circular linked list, FIFO)
  - [x] Two-stack Queue (backed by two simple stacks)
  - [x] Deque (double-ended queue backed by doubly-linked list - all operations O(1))
  - [x] Randomized Queue (queue backed by array - all operations amortized O(1))
  - [x] Floyd's cycle detection (finds a repeated value on stack)
  - [x] Clone random linked list (clone doubly-linked list with random pointer in linear time without modifying the linked list itself)
3. (Week 3) Sorting
  - [x] Selection sort (in-place, O(N²/2))
  - [x] Insertion sort (in-place, stable, O(N²/4) for random arrays, O(kN) for partially sorted arrays)
  - [x] Shell sort (in-place: O(N<sup>3/2</sup>), tight code, generalised insertion sort, complexity varies with h-sequence function)
  - [x] Shell sort - via array (ditto as above but pre-computes h-sequence and stores the values in an array)
  - [x] Merge sort - top-down (stable, O(NlgN))
  - [x] Merge sort - bottom-up (stable, O(NlgN), does not use recursion)
  - [x] Quick sort (in-place, O(NlgN), probabilistic guarantee, fastest in practice)
  - [x] Quick sort - Dijkstra's 3-way (ditto as above, improves performance in presence of duplicate keys)
  - [x] Quick sort - Entropy Optimal (ditto as above, average number of compares within constant factor of best-possible compare-based algorithm)
  - [x] Quick select (finds order statistic - k<sup>th</sup> smallest number in O(N), probabilistic guarantee)
  - [x] Heap sort (in-place, O(2NlgN), tight code, suffers from data non-locality performance penalty)
  - [x] Priority queue - Max (backed by binary heap, build O(N), insert/remove max O(lgN), generalised stack/queue)
  - [x] Planar intersection (count identical 2D points in two sets without using hash-based data structure, sub-quadratic)
  - [x] Planar permutation (check if two sets of 2D points are permutation of each other without using hash-based data structure, sub-quadratic)
  - [x] Dutch national flag sort (Dijkstra's precursor idea for 3-way quick sort)
  - [x] Binary search (requires pre-sorted input, O(lgN)) 
4. (Week 4) Searching
  - [x] Symbol table (binary search tree, all ops O(lgN))