# Data Mining

## What is Data Mining?
This term refers to the process of extracting useful models of data.
Sometimes, a model can be a summary of the data, or it can be the set of
most extreme features of the data.

### Statistical Modeling

### Machine Learning

### Computational Approaches

### Summary

### Feature Extraction

***

## Statistical Limits on Data Mining

### Total Information Awareness

### Bonferroni's Principle
Not all assumptions that might make sense are statistically significant.
Rareness is often a critical factor.

## Useful Things to know

### Importance of Words in Documents

TF (Term-Frequency)
  : How often does a word occur in a document?
  
IDF (Inverse Document Frequency)
  : **TODO**
  
TF.IDF (Combination of TF and IDF)
  : What is the topic of a document? What words appear relatively rarely
   but if they do appear, they appear often within one document?

### Hash Functions

- represent elements of interest as numbers (e.g. through ASCII-Code)
- (build groups of elements if desired and sum up their values from
previous step)
- create hash function (e.g. mod) that divides homogeneous data into
equal "buckets"

### Indices

Index
  : Data structure with information on how to retrieve objects
  efficiently
  
Object
  : Record with fields
  
- might be combined with hashing

### Secondary Storage
- refers to secondary memory: disk
- relatively slow in comparison to primary memory: RAM
- big data requires "clever" algorithms that load data into RAM which is
possibly needed soon.

### Natural Logarithms and Power Laws

- `e` can be approximated by `lim x -> inf: (1+1/2)^x`
- e.g. `(1+a)^b` can be approximated by `e^(ab)`
- `e^x` can be approximated by `sum 0 -> inf: (x^i)/(i!)`
- power laws describe linear relationships between logarithms of two
variables `x, y`
- e.g. `log_10(y) = 6 - 2 log_10(x)` (1,000,000 --> 1,000) for `y:
number of sales` and `x: rank of books by sales`

Mathew Effect
  :Strong features are likely to be strengthened further

***

# Finding Similar Items
**TODO**

## Applications of Near-Neighbor Search
**TODO**

### Jaccard Similarity of Sets
Similarity defined as Relative Size of Intersection
  : intersection of two sets / union of two sets (maximum: 1)

### Jaccard Similarity of Bags
- intersection size = minimum number of occurrences in both bags
- union size: sum of occurrences (intersections are counted twice)
- maximum similarity: 1/2
### Similarity of Documents
- only considering syntax level, not semantics
- used for finding (near) duplicates (e.g.)
- applications:
    - plagiarism (similarity between documents)
    - online shopping (similarity between customers)
    - movie ratings (similarity between movies)

### Collaborative Filtering as a Similar-Sets Problem
**TODO**

## Shingling of Documents
**TODO**

### k-Shingles (=k-gram)
- using shingles on character level poses some difficulties (e.g.
whitespaces), but has the advantage of ignoring possible typos

### Choosing the Shingle Size
- depends on size of documents and alphabet
- should be as small as possible to be little complex
- should be big enough so that not all shingles appear in all documents
- consider average frequency of alphabet-parts
- rule of thumb for standard english: `k=5`

### Hashing Shingles
- assumes that many shingles are unlikely
- reduces needed storage size

### Shingles Built from Words
**TODO**

## Similarity-Preserving Summaries of Sets
- Applying Jaccard Similarity on shingles of documents might lead to too
many large sets
- instead estimate similarity based on signatures
- --> estimate Jaccard similarity by computing the fraction of rows in
the signature matrix that agree

**Characteristic Matrix**
  : Matrix representation of sets (columns ~ sets / e.g. documents, rows
  ~ possible elements / e.g. shingles)

- Calculate *Min-Hash*
  - permute rows randomly 
  - find first identifier with `1` (element)
- Probability of two Min-Hashes of Documents to be the same *equals* the
Jaccard Similarity
- Proof
  - 3 Types of rows: *X* if both `1`, *Y* if one `1`, *Z* if both `0`
  - Most rows are of type *Z*
  - ratio of type *X* to type *Y*
  - *x*: amount of type *X* rows; *y*: amount of type *Y* rows
  - Jaccard Similarity: `|S_1 \intersection S_2| / |S_1 \union S_2| =
  x / x + y`
  - Probability of *X* row appears before type *Y* row = `x / x + y`
  - Becomes reasonable by repeating multiple times

**Minhash Signature**
  : vector of hash values for set `S: [h_1(S), ...
  , h_n(S)]`

**Minhash Matrix**
  : matrix of minhash signatures as columns (smaller than characteristic
  Matrix)

**Simulated Minhash**
  : compute minhash signatures by random hashes of row identifiers -->
  new permutations

## Locality-sensitive Hashing
- Finding pairs "most similar"
- hashing minhash signatures several times
- **choose hashes that map minhash signatures to buckets**
- for every signature hash bands consisting of `r` rows each
- if two columns map to the same bucket for any band, *candidate pair*


**Todo** look at first three slides of lecture 2017-11-21

## Distance Measures
- Jaccard similarity `0..1` --> Jaccard distance `0..1`, = 1 - Jaccard
similarity
- Defined as a set of points (*space*), with e.g. points `x` and `y`
- function
- Distance axioms
  - `d(x,y) >= 0` (non-negativity)
  - `d(x,y) = 0 <--> x = y` (identity)
  - `d(x,y) = d(y,x)` (symmetry)
  - `d(x,y) <= d(x,z) + d(z,y)` (triangle-inequality)

**Todo** Details on different Distance measures from book

## Theory of Locality - sensitive functions
- function families like Jaccard Similarity have following properties:
  - close pairs more likely than distant pairs
  - statistically independent
  - efficient (better than manual checking)
- locality sensitive function: `f(x) = f(y) <--> make x,y a candidate
pair`
- sensitive if for any function in function family
  - `d(x,y) <= d_1 --> p(f(x)=f(y)) >= p_1`
  - `d(x,y) >= d_2 --> p(f(x)=f(y)) >= p_2`
- combine multiple functions within family to *tune* S-curve
- is sensitive if `p_1 := 1 - d_1 && p_2 := 1 - d_2`

### Amplification of locality-sensitive families
- given a sensitive family `F`, form new `F'` by the **AND**-construct
so that for any function in `F'` such that it gives an equal result for
`x, y` if and only if for any function in `F`, this is also the case
  - `p_1 := p_1^r`, where `r` is the number of rows in banding technique
  - similar with `p_2`
- same with **OR**-construct if at least one function in `F` gives the
same value
  - `1 - (1 - p_1)^b`, where `b` is the number of functions in `F`
- if cascading **AND** with **OR** for `r = b = 4`, false positives and
false negatives are reduced
- if cascading **OR** with **AND** for `r = b = 4`, false positives
increase

## LSH families for other distance measures

### Hamming Distance
- defined as number of component-wise-differences
- Family of functions is defined as creating one function for each
component and `f_i(x) = f_i(y)` iff `x,y` agree in component `i`

***

# Clustering
Input:
  Collection of Points
Goal:
  Group them based on some measure (i.e. distance)

## General Considerations

### Strategies
- Hierarchical
  - agglomerative (bottom-up)
  - divisive (top-down)
- Assignment
  - TODO
- Other distinctions
  - Euclidean --> Centroid
  - Non-Euclidean --> Clustroid
- Size
  - Main memory only
  - Secondary memory necessary

### Curse of Dimensionality
1. All points have "equal" distance (e.g. euclidean)
2. almost all vectors are orthogonal (e.g. cosine)

==> Difficult to apply conventional distance measures
==> Solution: Dimensionality reduction

#### 1.
- `d` dimensions
- `n` random points in unit cube
- `x = [x_1, ... , x_n]`
- `x_i = [0, ... , 1]`
- for two random points `x, y`, consider L-2 norm
  - for large `d` probability of `|x_1 - y_1|` close to 1 is high
  - ==> relative contrast vanishes:
  - `(D_max - D_min) / D_min` --> 0

#### 2
- consider angle between vectors
- `B`is origin
- for two random points `B, C`, consider Cosine norm
  - `BA` and `BC` are vectors
  - numerator is sum or random values --> 0
  - denominator grows linearly with number of dimensions
  - ==> cosine for arbitrary vectors --> 0 --> ~ 90°

## Hierarchical Clustering
- 1 point = 1 cluster
- merge combine clusters

### Procedure
1. Find 2 closest points (or if multiple candidates, an arbitrary pair)
2. Replace 2 points from 1. by cluster with centroid (point at center of
cluster
3. Find 2 closest points (or one point and cluster (consider centroid)
4. Replace old centroids by new ones as average of all points in cluster
5. Repeat until
    - fixed amount of clusters reached
    - adequacy of clusters is reached (e.g. threshold for distances
    inside clusters)

### Control Rules for Merging Clusters
- smallest distance between centroids
- cluster distance := average point-wise distance
- cluster distance := minimum point-wise distance
- radius := maximum distance between cluster's points and centroid-->
merge 2 clusters with lowest radius
- diameter := maximum distance between cluster's points

### Non-Euclidean Space (no centroid as average)
- choose one point of cluster as representative (=: clusteroid)
  - minimize sum of distances to other cluster points
  - minimize the maximum distance to other cluster points
  - minimize the squared distance to other cluster points

## k-means Clustering
- point assignment for euclidean space
- `k` is known

### procedure
- choose `k` initial points as centroids
- assign each point to the closest of these centroids
- shift centroids to new center of cluster

### Choosing the right k
- trade off between average diameter and number of clusters

### BFR Algorithm
- prerequisites
  - data does not fit into memory
- choose `k` points
- partition data into chunks that fit
- in main memory
  1. discard set --> summaries of clusters
  2. compressed set --> summaries of points not close to cluster
  centroid but close to each other
  3. retained set --> neither close to cluster nor to other points
- summary of a set defined as count `N` of points, centroid's
coordinates `SUM` and a variance `SUMSQ`
- closeness:
  - assume random order of points in all chunks
    - assign <--> unlikely that there will be a better (closer) fit
  - Mahalanobis distance
    - euclidean space
    - axes of clusters align with axes of space
    - euclidean distance between point `p` and centroid `c` by standard
    deviation of all points within cluster
    - in BFR compute between each point `p` and each centroid `c`
      - if < threshold, add to closest cluster, else resume with mini-
      clusters

#### Process
For each chunk:
1. if point close to centroid
  - add point to cluster
  - update `N`, `SUM`, `SUMSQ`
2. else cluster with retained set (outliers) and form miniclusters via
other cluster algorithm
3. add minicluster's summaries to compressed set
4. actual point assignment for discard / compressed set stored in
secondary memory

After last chunk:
- merge retained / compressed set with closest cluster or treat as
outliers

## CURE Algorithm
- point assignment for euclidean space
- clusters don't have to be aligned with space

### Process
1. Initialize
  - cluster small sample of data in main memory
  - select representatives for each cluster (e.g. far away points inside)
  - move representatives slightly towards centroid
2. Completion
  - merge clusters if pair of representatives from both clusters are
  close
  - read all points from secondary memory and assign to closest cluster
  via representative

## GRGPF Algorithm
- represent clusters by sample points in main memory
- organize clusters in tree
- point assignment via passing it down the tree
- the further we go up in the tree, the less information we have as
parent is only a subset of union of children
- `ROWSUM(p)` sum of squares of distances from `p` to other points in
  cluster
- clusteroid defined as point that minimizes `ROWSUM(p)`

### Process
- Initialize
  - cluster sample data in main memory hierarchically with desired
  cluster size `n`
  - select from `T` clusters with approx. size `n` --> leaves
  - zusammenfassen of clusters with common ancestors
- Add points
  - read points from secondary memory
  - insert into nearest cluster
    - start at root
    - choose child that has closest centroid
    - continue until leave
    - adjust representation of cluster after adding new point
      - update `N`
      - update `ROWSUM(q)`, if `q` is one of the closest or furthest
      points
      - if `p` is one of the closest or furthers, estimate `ROWSUM(p)`
      as `ROWSUM(c) + N * (d(p,c))^2`, because calculation is impossible
      since all points of cluster would be required but are discarded,
      due to space efficiency
- merge and split clusters, adjust

# Collaborative Filtering

## Measure similarity between users

- Problem: Two users watched same movies but rated opposite ratings
- boolean scale (e.g. liked and disliked):
    - Jaccard distance
- non-boolean scale (e.g. 1-5):
    - make boolean (through rounding) and then Jaccard distance
    - cosine distance
        - not necessarily good differentiation between similar and non-
        similar users
        - larger cosine -> smaller angle & smaller distance
        - adding rounding first, differentiation is clearer
        - normalization
            - subtract from each rating the average rating of users
            - ignore users with all equal ratings
            - differentiation even clearer
- recommendation process
    - find most similar users
    - recommend most highly rated movies

### UV-Decomposition
- reduce complexity by trying to reduce size of Utility Matrix `M` by
dividing it into two matrices (`U`, `V`) that reduces MSE to original
`M`
    - use random starting point and solve gradually
    - not guaranteed to find global minimum


# Dimensionality Reduction
- general goal: high-dimensioned space but some are linear combinations
of others --> find subspace with fewer dimensions

## Eigenpair Analysis
- **TODO**

## Singular Value Decomposition (SVD)
- goal: dimensionality reduction of matrices

### Definition
- Matrix `M` with `m` rows and `n` columns
- rank `r`(number of independent rows)
- `SVD = U x SIGMA x V`, where
  - `U` has `m,r` dimensions, columns are unit vectors and orthogonal
  --> column-orthonormal
    - connects people to concepts
  - `V` has `n,r` dimensions, also column-orthonormal --> `V^T` is
  row-orthonormal
    - connects items to concepts
  - `SIMGA` is an `r,r` diagonal matrix
    - specifies strengths of concepts
- Lossy reduction possible, by ignoring the least strong concept(s)
(`SIGMA`)
  - compute sum of squares of singular values and retain at least 90%
  - measure of difference: Frobenius norm `||M||` (proportional to RMSE)
  - error proportional to those `SIMGA_i` changed

### Querying
-

## CUR Decomposition