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
  - [x] Deque (double-ended queue backed by doubly-linked list - all operations O(_1_))
  - [x] Randomized Queue (queue backed by array - all operations amortized O(_1_))
  - [x] Floyd's cycle detection (finds a repeated value on stack)
  - [x] Clone random linked list (clone doubly-linked list with random pointer in linear time without modifying the linked list itself)
3. (Week 3) Sorting
  - [x] Selection sort (in-place, O(_N²/2_))
  - [x] Insertion sort (in-place, stable, O(_N²/4_) for random arrays, O(_kN_) for partially sorted arrays)
  - [x] Shell sort (in-place: O(_N<sup>3/2</sup>_), tight code, generalised insertion sort, complexity varies with h-sequence function)
  - [x] Shell sort - via array (ditto as above but pre-computes h-sequence and stores the values in an array)
  - [x] Merge sort - top-down (stable, O(_NlgN_))
  - [x] Merge sort - bottom-up (stable, O(_NlgN_), does not use recursion)
  - [x] Quick sort (in-place, O(_NlgN_), probabilistic guarantee, fastest in practice)
  - [x] Quick sort - Dijkstra's 3-way (ditto as above, improves performance in presence of duplicate keys)
  - [x] Quick sort - Entropy Optimal (ditto as above, average number of compares within constant factor of best-possible compare-based algorithm)
  - [x] Quick select (finds order statistic - k<sup>th</sup> smallest number in O(_N_), probabilistic guarantee)
  - [x] Heap sort (in-place, O(_2NlgN_), tight code, suffers from data non-locality performance penalty)
  - [x] Priority queue - Max (backed by binary heap, build O(_N_), insert/remove max O(_lgN_), generalised stack/queue)
  - [x] Planar intersection (count identical 2D points in two sets without using hash-based data structure, sub-quadratic)
  - [x] Planar permutation (check if two sets of 2D points are permutation of each other without using hash-based data structure, sub-quadratic)
  - [x] Dutch national flag sort (Dijkstra's precursor idea for 3-way quick sort)
  - [x] Binary search (requires pre-sorted input, O(_lgN_))
  - [ ] Selection filter (Returns number of 3D points closest to the origin in Euclidean distance)
  - [ ] Index priority queue
  - [ ] Min/max priority queue (supports `insert()`, `deleteMax()`, `deleteMin()` in O(_lgN_), and `max()`, `min()` in O(_1_))
  - [ ] Dynamic median finding (supports `insert()` in O(_lgN_), `findMedian()` in O(_1_) and `deleteMedian()` in O(_lgN_))
  - [ ] Fast PQ insert (minimum priority queue that supports `insert()` in O(_loglogN_) compares and `deleteMin()` in O(_~2logN_) compares)
  - [ ] Taxicab numbers (taxicab number is an integer that can be expressed as the sum of two integers in two different ways: 
        _a<sup>3</sup> + b<sup>3</sup> = c<sup>3</sup> + d<sup>3</sup>_, e.g. 1729 = 9<sup>3</sup> + 10<sup>3</sup> = 1<sup>3</sup> + 12<sup>3</sup>. 
        Algorithm finds all taxicab numbers _a_, _b_, _c_, _d_ smaller that N in time proportional to O(_N²logN_) time and space proportional to O(_N²_).)
4. (Week 4) Searching
  - [x] Symbol table (binary search tree, all ops O(_lgN_), except delete operation which is O(_sqrt(N)_))
  - [ ] Check if a binary tree is a BST (uses extra space proportional to height of tree)
  - [ ] Inorder traversal with constant extra space
  - [ ] Self-organising search (algorithm that rearranges items to make those accessed frequently likely to be found early in the search)
  - [ ] Interpolation search (binary search variation that mimics the process of looking near the beginning of a dictionary when the word begins with a 
        letter near the beginning of the alphabet )