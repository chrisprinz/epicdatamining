#  Hierarchical clustering

## a
Note that I use the following notation: `{x|y}` denotes a cluster with
content(s) `x` and (rounded) centroid `y`.
```
{1} {4} {9} {16} {25} {36} {49} {64} {81}

{1, 4 | 2.5} {9} {16} {25} {36} {49} {64} {81}

{1, 4, 9 | 4.7} {16} {25} {36} {49} {64} {81}

{1, 4, 9 | 4.7} {16, 25 | 20.5} {36} {49} {64} {81}

{1, 4, 9 | 4.7} {16, 25 | 20.5} {36, 49 | 42.5} {64} {81}

{1, 4, 9, 16, 25 | 11} {36, 49 | 42.5} {64} {81}

{1, 4, 9, 16, 25 | 11} {36, 49 | 42.5} {64, 81 | 72.5}

{1, 4, 9, 16, 25 | 11} {36, 49, 64, 81 | 57.5}

{1, 4, 9, 16, 25, 36, 49, 64, 81 | 31.7}
```

## b

assumption: euclidean distance

### Bottom left
`{(2,2), (3,4), (5,2)}`

sum of distances to other nodes:
```
(2,2): sqrt(2) + 3
(3,4): sqrt(2) + sqrt(8)
(5,2): sqrt(8) + 3
```

Result: `(3,4)`

### Top
`{(4,8), (4,10), (6,8), (7,10)}`

sum of distances to other nodes:
```
(4,8): 2 + 2 + sqrt(13)
(4,10): 2 + sqrt(4) + 3
(6,8): 2 + sqrt(4) + sqrt(5)
(7,10): sqrt(13) + 3 + sqrt(5)
```

Result: `(6,8)`

### Bottom right
`{(9,3), (10,5), (11,4), (12,3), (12,6)}`

sum of distances to other nodes:
```
(9,3): sqrt(5) + sqrt(5) + 3 + sqrt(18)
(10,5): sqrt(5) + sqrt(2) + sqrt(4) + sqrt(5)
(11,4): sqrt(5) + sqrt(2) + sqrt(2) + sqrt(5)
(12,3): 3 + sqrt(4) + sqrt(2) + 3
(12,6): sqrt(18) + sqrt(5) + sqrt(5) + 3
```

Result: `(11,4)`

# K-means Clustering

## a and b

### Bottom left
```
{(2,2), (3,4), (5,2)}

N: 3
SUM: (10, 8)
SUMSQ: (38, 24)
Variance: (1.56, 0.89)  // :=SUMSQ_i / N − (SUM_i/N)^2
StdDev: (sqrt(1.56), sqrt(0.89))
```

### Top
```
{(4,8), (4,10), (6,8), (7,10)}

N: 4
SUM: (21, 36)
SUMSQ: (115, 328)
Variance: (1.19, 1)  // :=SUMSQ_i / N − (SUM_i/N)^2
StdDev: (sqrt(1.19), 1)
```

### Bottom right
```
{(9,3), (10,5), (11,4), (12,3), (12,6)}

N: 5
SUM: (54, 21)
SUMSQ: (597, 97)
Variance: (2.76, 1.76)  // :=SUMSQ_i / N − (SUM_i/N)^2
StdDev: (sqrt(2.76), sqrt(1.76))
```

## c

```
Mahalanobis distance: sqrt((1/4) + (1) + (16/25)) = sqrt(1.89) ~ 1.37
```
