# Data Mining

[TOC]



- What is Data Mining?
    - Finding models for data.
- Statistical Modeling
    - underlying distribution, from which visible data is drawn
- Machine Learning
    - especially useful if unclear what is searched for, i.e. movie
      recommendations
    - examples: Bayes nets, support-vector machines, decision trees,
      hidden Markov models...
- Computational Approaches
    - data mining as algorithmic problem
    - model as answer to complex query
    - Summarization
        - represent / simplify data through a more simple form
        - e.g. "PageRank", clustering
    - Feature Extraction
        - find most extreme examples and use them to represent data
        - frequent itemsets (hamburger and ketchup are frequently bought together)
        - similar items (recommendation based on customers with similar taste)


## Statistical Limits on Data Mining

### Bonferroni's Principle
- Not all assumptions that might make sense are statistically significant.
- Rareness is often a critical factor.
- Bonferroni correction: clear data to avoid 'bogus' data 
- compare observations with expected number of positives (assuming randomness)
- e.g. exercise with evil-doers


## Useful Things to know

### Importance of Words in Documents

**TF (Term-Frequency)**: How often does a word occur in a document?
  $TF_{ij}=\frac{f_{ij}}{max_kf_{kj}}$

**IDF (Inverse Document Frequency)**: In how many documents does a word occur?

**TF.IDF (Multiplicatory combination of TF and IDF)**: What is the topic of a document? What words appear relatively rarely but if they do appear, they appear often within one document?

### Hash Functions

- represent elements of interest as numbers (e.g. through ASCII-Code)
- (build groups of elements if desired and sum up their values from previous step)
- create hash function (e.g. mod) that divides homogeneous data into equal "buckets"

### Indices

**Index**: Data structure with information on how to retrieve objects efficiently

**Object**: Record with fields (might be combined with hashing)

### Secondary Storage
- refers to secondary memory: disk
- relatively slow in comparison to primary memory: RAM
- big data requires "clever" algorithms that load data into RAM which is possibly needed soon.

### Natural Logarithms and Power Laws

- $e$ can be approximated by $lim (x) \rightarrow \infty: (1+1/2)^x$
- e.g. $(1+a)^b$ can be approximated by $e^{(ab)}$
- $e^x$ can be approximated by $sum 0 \rightarrow \infty: (x^i)/(i!)$
- power laws describe linear relationships between logarithms of two variables $x, y$
- e.g. $log_10(y) = 6 - 2 log_10(x)$ (1,000,000 \rightarrow 1,000) for $y$: of sales **and** $x$: rank of books by sales

**Mathew Effect**: features are likely to be strengthened further



***
***



# Finding Similar Items

## Similarities
### Jaccard Similarity of Sets
Similarity defined as Relative Size of Intersection: intersection of two sets / union of two sets (maximum: 1)

### Jaccard Similarity of Bags
- intersection size = minimum number of occurrences in both bags
- union size: sum of occurrences (intersections are counted twice)
- maximum similarity: $\frac{1}{2}$

### Similarity of Documents
- only considering syntax level, not semantics
- used for finding (near) duplicates (e.g.)
- applications:
    - plagiarism (similarity between documents)
    - online shopping (similarity between customers)
    - movie ratings (similarity between movies)

### Collaborative Filtering as a Similar-Sets Problem
- recommend items based on similar user's preferences
- applications:
    - On-Line Purchases
        - due to large dimensionality, similarity need not be high to be significant
        - possible gain through additional clustering (e.g. sci-fi books)
    - Movie Ratings
        - non-binary scale introduces complexity


## Shingling of Documents
- simplest way to compare lexical similarity of textual documents

### k-Shingles (=k-gram)
- using shingles on character level poses some difficulties (e.g. whitespaces), but has the advantage of ignoring possible typos

### Choosing the Shingle Size
- depends on size of documents and alphabet
- should be as small as possible to be little complex
- should be big enough so that not all shingles appear in all documents
- consider average frequency of alphabet-parts
- rule of thumb for standard english: $k=5$

### Hashing Shingles
- assumes that many shingles are unlikely
- reduces needed storage size

### Shingles Built from Words
- create shingle from stop words followed by other words (e.g. for finding similar news articles)


## Similarity-Preserving Summaries of Sets
- Applying Jaccard Similarity on shingles of documents might lead to too many large sets
- instead estimate similarity based on signatures
- $\rightarrow$ estimate Jaccard similarity by computing the fraction of rows in the signature matrix that agree

### Matrix representation of sets
- Characteristic Matrix
- (columns ~ sets / e.g. documents, rows ~ possible elements / e.g. shingles)

### Minhashing
- Given any permutation of the universal set, the minhash value for a set is that element that appears first in the permuted order
- Calculate *Min-Hash*
    - permute rows randomly
    - find first identifier with $1$ (element)
- Probability of two Min-Hashes of Documents to be the same *equals* the Jaccard Similarity
- Proof
    - 3 Types of rows: $X$ if both $1$, $Y$ if one $1$, $Z$ if both $0$
    - Most rows are of type $Z$
    - ratio of type $X$ to type *$Y$
    - $x$: amount of type $X$ rows; $y$: amount of type $Y$ rows
    - Jaccard Similarity: $\frac{|S_1 \cap S_2|}{|S_1 \cup S_2} = \frac{x}{x + y}$
    - Probability of $X$ row appears before type $Y$ row = $\frac{x}{x + y}$
    - Becomes reasonable by repeating multiple times
- Efficient Minhashing
    - Since it is not really possible to generate random permutations,
      it is normal to simulate a permutation by picking a random hash
      function and taking the minhash value for a set to be the least hash
      value of any of the set’s members.

**Minhash Signature**: vector of hash values for set $S: [h_1(S), ...S)]$

**Minhash Matrix**: matrix of minhash signatures as columns (smaller than characteristic Matrix)

**Simulated Minhash**: compute minhash signatures by random hashes of row identifiers $\rightarrow$ new permutations

### Computing Minhash Signatures
**TODO**

## Locality-sensitive Hashing
- Finding pairs "most likely similar" (potentially because data is too big to compare all sets)
- hashing minhash signatures several times
- **choose hashes that map minhash signatures to buckets** for full similarity analysis
- for every signature, divide characteristic matrix into $b$ hash bands consisting of $r$ rows each
- if two columns map to the same bucket for any band, *candidate pair*
- Jaccard similarity of documents $\rightarrow$ probability of becoming a candidate pare in an s-curve fashion
- combination of approaches consists of locality-sensitive hashing first, then do actual comparison through minhash, possibly also followed by full Jaccard similarity

**Todo** look at first three slides of lecture 2017-11-21


## Distance Measures
- function on set of points (space)
- Distance axioms
    - $d(x,y) \geq 0$ (non-negativity)
    - $d(x,y) = 0 <\rightarrow x = y$ (identity)
    - $d(x,y) = d(y,x)$ (symmetry)
    - $d(x,y) \leq d(x,z) + d(z,y)$ (triangle-inequality)

### Jaccard Distance
- Jaccard similarity $sim \in 0..1$ $\rightarrow$ Jaccard distance $dist \in 0..1$, $ dist = 1 - sim$
### Euclidean Distance
- **L2-Norm**
    - $\sqrt{\sum_{0:n}^i d_i }$
    - shortest line between two points
- **LR-Norm**
    - like L2 norm but with exponent and root degree $r$ instead of $2$
- **Manhattan Distance**
    - = L1 Norm
    - $\sum_{0:n}^i d_i $
- **L-infinity Distance**
    - with $r$ getting larger, only the biggest difference matters
    - $max(d_i)$

### Cosine Distance
- angle between two vectors (regardless of length) (between $0$ and $180°$)
- calculate through $=arc cos(\frac{x * y}{|x| * |y|})$

### Edit Distance
- only for strings
- smallest number of insertions and deletions that convert $x$ to $y$

### Hamming Distance
- defined as number of component-wise-differences
- Family of functions is defined as creating one function for each component and $f_i(x) = f_i(y) \leftrightarrow x,y$ agree in component $i$


## Theory of Locality - sensitive functions
- function families like Jaccard Similarity have following properties:
  - close pairs more likely than distant pairs
  - statistically independent
  - efficient (better than manual checking)
- locality sensitive function: $f(x) = f(y) \leftrightarrow$ make $(x,y)$ a candidate pair
- sensitive if for any function in function family
  - $d(x,y) \leq d_1 \rightarrow p(f(x)=f(y)) \geq p_1$
  - $d(x,y) \geq d_2 \rightarrow p(f(x)=f(y)) \geq p_2$
- combine multiple functions within family to *tune* S-curve
- is sensitive if $p_1 := 1 - d_1 \land p_2 := 1 - d_2$

### Amplification of locality-sensitive families
- given a sensitive family $F$, form new $F'$ by the **AND**-construct so that for any function in $F'$ such that it gives an equal result for $x, y$ if and only if for any function in $F$, this is also the case
  - $p_1 := p_1^r$, where $r$ is the number of rows in banding technique
  - similar with $p_2$
- same with **OR**-construct if at least one function in $F$ gives the same value
  - $1 - (1 - p_1)^b$, where $b$ is the number of functions in $F$
- if cascading **AND** with **OR** for $r = b = 4$, false positives and false negatives are reduced
- if cascading **OR** with **AND** for $r = b = 4$, false positives increase


***
***



# Clustering
**Input**: Collection of Points
**Goal**: Group them based on some measure (i.e. distance)
- useful summary of data (points in some space)
- needs a distance measure on that space
- Ideally, intra-cluster points have small distances inter-cluster points have large distances



## General Considerations
### Strategies
- Hierarchical
    - each point starts in own cluster
    - clusters are then merged until done
    - agglomerative (bottom-up)
- Assignment
    - (usually) initial cluster estimation
    - then point-wise assignment to best fitting cluster
    - divisive (top-down)
    - variations:
        - combine / split clusters on the go
        - leave outliers unassigned
- Distinctions
    - By space type
        - Euclidean $\rightarrow$ numerical summary in Centroid
        - Non-Euclidean $\rightarrow$ non-numerical summary in Clustroid
    - By Size
        - Main memory only
        - Secondary memory necessary

### Curse of Dimensionality
In high-dimensional Euclidean or non-Euclidean spaces:
- Almost all points have *equal* distance (e.g. Euclidean)
- almost all vectors are orthogonal (e.g. cosine)

==> Difficult to apply conventional distance measures
==> Solution: Dimensionality reduction

#### Equal Distances

- $d$ dimensions
- $n$ random points in unit cube
- $x = [x_1, ... , x_n]$
- $x_i = [0, ... , 1]$
- for two random points $x, y$, consider L-2 norm
  - for large $d$ probability of $|x_1 - y_1|$ close to $1$ is high
  - $\rightarrow$ relative contrast vanishes:
  - $\frac{(D_{max} - D_{min})}{D_{min}}\rightarrow 0 $

#### Orthogonal Vectors
- consider angle between vectors
- $B$ is origin
- for two random points $B, C$, consider Cosine norm
  - $BA$ and $BC$ are vectors
  - numerator is sum or random positive and negative values $\rightarrow 0$
  - denominator grows linearly with number of dimensions
  - $\rightarrow$ cosine for arbitrary vectors $\rightarrow 0 \rightarrow ~ 90°$



## Hierarchical Clustering
Decide in advance:
- how to represent clusters
- how to choose which clusters to merge
- when to stop

### Procedure for Euclidean Space
1. Find $2$ closest points (or if multiple candidates, an arbitrary pair)
2. Replace $2$ points from 1. by cluster with centroid (point at center of cluster)
3. Find $2$ closest points (or one point and cluster (consider centroid))
4. Replace old centroids by new ones as average of all points in cluster
5. Repeat until
    - fixed amount of clusters reached
    - adequacy of clusters is reached (e.g. threshold for distances, or density inside clusters)

- very inefficient ($O(n^3)$) $\rightarrow$ priority queue $\rightarrow$ ($O(n^2)$)

### Control Rules for Merging Clusters
- smallest distance between centroids
- cluster distance := average point-wise distance
- cluster distance := minimum point-wise distance
- radius := maximum distance between cluster's points and centroid $\rightarrow$ merge $2$ clusters with lowest resulting radius
- diameter := maximum distance between cluster's points $\rightarrow$ merge $2$ clusters with lowest resulting diameter

### Non-Euclidean Space (no centroid as average)
- choose one point of cluster as representative (=: clusteroid)
    - minimize sum of distances to other cluster points
    - minimize the maximum distance to other cluster points
    - minimize the sum of squared distances to other cluster points
- other concepts like radius, diameter, or when to stop stay the same (more or less)



## k-means Clustering
- point assignment for euclidean space
- $k$ is known

### procedure
- choose $k$ initial points as centroids
- assign each point to the closest of these centroids
- shift centroids to new center of cluster
- possibly repeat

### Initilizing clusters
- ideally in different clusters
- point picking approaches
    - as far away as possible
        - start with random point
        - then, add farthest points
    - perform sample clustering

### Choosing the right k
- trade off between average diameter and number of clusters
- find *elbow* in curve between the two parameters from above
- search for *bump* in diameter if reducing $k$
    - start with power of $2$'s as $k$
    - look for bump, then perform binary search within neighbors

### BFR Algorithm
- prerequisites
    - data does not fit into memory
    - euclidean space
    - axes of clusters align with axes of space
- choose $k$ points
- partition data into chunks that fit
- in main memory
    1. **discard set**: summaries of clusters (represented points are discarded)
    2. **compressed set**: summaries of points not close to any cluster centroid but close to each other (miniclusters, represented points are discarded)
    3. **retained set**: neither close to cluster nor to other points (held in memory exactly as they are)
- summaries' definition
    - count $N$ of points
    - all points' coordinates' $SUM$ and variance $SUMSQ$
    - centroid's coordinate defined as $\frac{SUM}{N}$ for each dimension
    - standard deviation can be calculated from variance
    - this representation makes it mathematically easy to extend / merge
      clusters
- closeness:
    - assume random order of points in all chunks
        - assign $\leftrightarrow$ unlikely that there will be a better (closer) fit
    - Mahalanobis distance
        - euclidean distance between point $p$ and centroid $c$
          normalized by standard deviation of all points within cluster
        - in **BFR** compute between each point $p$ and each centroid $c$
            - if $\lt$ threshold, add to closest cluster, else resume with miniclusters

#### Process
For each chunk:
1. if point close to centroid
  - add point to cluster
  - update $N$, $SUM$, $SUMSQ$
  - discard point
2. else cluster with retained set (outliers) and form miniclusters via other main memory cluster algorithm
3. add miniclusters' summaries to compressed set and discard point
4. actual point assignment for discard / compressed set stored in secondary memory

After last chunk:
- merge retained / compressed set with closest cluster or treat as outliers



## CURE Algorithm
- **C**lustering **U**sing **RE**presentatives
- point assignment for Euclidean space
- clusters don't have to be aligned with space
- uses representatives instead of centroid

### Process
1. Initialize
    - cluster small sample of data in main memory (hierarchically)
    - select representatives for each cluster (e.g. as far away as possible)
    - move representatives slightly towards centroid
2. Completion
    - merge clusters if pair of representatives from both clusters are close
    - read all points from secondary memory and assign to closest cluster via representative



## Clustering in Non-Euclidean Spaces
### GRGPF Algorithm
- represent clusters by sample points in main memory
- organize clusters hierarchically in tree
- point assignment via passing it down the tree
- the further we go up in the tree, the less information we have as parent is only a subset of union of children
- $ROWSUM(p)$ sum of squares of distances from $p$ to other points in cluster
- cluster representation
    - $N$ number of points
    - clustroid defined as point that minimizes $ROWSUM(p)$
    - $ROWSUM(clustroid)$
    - some $k$ closest points to the clustroid and their ROWSUMs (in case the clustroid changes, these are the candidates)
    - some $k$ farthest points to the clustroid and their ROWSUMs (used to determin whether two clusters are close)

#### Process
- Initialize
    - cluster sample data in main memory hierarchically with desired cluster size $n$ $\rightarrow$ leads to tree $T$
    - select from $T$ clusters with approx. size $n$ $\rightarrow$ leaves
    - group clusters with common ancestor in $T$ to interior nodes
    - possibly rebalance
- Add points
    - read points from secondary memory
    - insert into nearest cluster
        - start at root
        - choose child that has closest centroid
        - continue until leave
        - adjust representation of cluster after adding new point
            - update $N$
            - update $ROWSUM(q)$, if $q$ is one of the closest or furthest points, i.e. part of the representation
            - if new point $p$ is one of the closest or furthers, estimate $ROWSUM(p)$ as $ROWSUM(c) + N * (d(p,c))^2$, because calculation is impossible since all points of
              cluster would be required but are discarded, due to space efficiency (**N and $ROWSUM(C)$ here are before correction**)
- merge and split clusters, adjust


***
***



# Recommendation Systems

**content-based systems**: examine properties of items


**collaborative filter systems**: examine similarities between users and or items


**The Long Tail**: In digital world, many more items can be made available to customers than in analog world (e.g. through limits of store). This requires individual recommendations.

- Applications for recommendation systems
    - products
    - movies
    - news articles

### Utility Matrix
- assigns each user to an item with a certain preference
- if incomplete, goal of recommendation systems to fill blanks - or at least those ones that can be expected to be high
- populated either using
    - explicit user feedback (e.g. ratings)
    - implicit user feedback (i.e. behaviour)



## Content-Based Recommendations

**Item Profiles**: collection of features representing important characteristics of the item

### Discovering Features of Documents

- identify characterizing words from documents
  - remove stop words
  - determine $TF.IDF$ 
  - words highest scores characterize the document
- represent documents by set of words, either count- or threshold based
- measure similarity with word vectors
  - meaning content, not lexical similarity
  - Jaccard distance
  - Cosine distance ($\frac{a.b}{|a|*|b|}$)


- if feature values not boolean, but numeric, normalize them

### User Profiles

- best guess consists of aggregation of item profiles with available data
- subtract average value for numerical values
- recommending items to users based on content
  - use cosine distance to find similarities between user profile and item profiles

## Collaborative Filtering

- instead of comparing users to items, this approach compares users with other users and recommends the highest valued items from the most similar users

### Measure similarity between users

- Problem: Two users watched same movies but rated opposite ratings
- boolean scale (e.g. liked and disliked):
    - Jaccard distance
- non-boolean scale (e.g. 1-5):
    - make boolean (through rounding) and then Jaccard distance
    - cosine distance
        - not necessarily good differentiation between similar and non-
          similar users
        - larger cosine -> smaller angle & smaller distance
        - rounding first, differentiation is clearer
        - normalization
            - subtract from each rating the average rating of users
            - ignore users with all equal ratings
            - differentiation even clearer

### Duality of Symmetry

- while items usually are unambiguously assignable to features (e.g. genre), users might not be (i.e. they might like multiple genres)
- predicting the value for a certain user-item-pair $(U,I)$, either 
  - find the most similar users to $U$ that rated $I$, and average their normalized values
  - find the most similar items to $I$ that $U$ rated, and average their normalized values
- if one wants to predict the best items for a given user, one has to fill all missing or at least the ones with the highest expected values $\rightarrow$ one has to fill the whole row of the utility matrix
  - if working user-based, one has to find similar users only once (but it's usually worse)
  - if working item-based, one has to find similar items for each item (but it's usually better)

### UV-Decomposition

- special case of [dimensionality reduction](#dimensionality-reduction)
- reduce complexity by trying to reduce size of Utility Matrix $M$ by
  dividing it into two long but thin matrices ($U$, $V$) that reduces MSE to original $M$
  - $M: n \times m$
  - $U: n \times d$
  - $V: d \times m$
  - $d$ dimensions that characterize users and items


#### Root-Mean-Square Error (RMSE)

  - measure of how close $UV$ is to $M$


Calculation

- sum squared difference between $UV$ product and $M$ per entry
- average by dividing by count of nonblank entries
- square root of mean

#### Incremental Computation of a UV-Decomposition

- use random starting point for $U,V$ and solve gradually by minimizing **RMSE** 
- not guaranteed to find global minimum
- try by picking different starting points

#### Exemplary Process

- initialize $U$ and $V$ with $1$'s 
- start with $U_{1,1}$ as $x$, then calculate RMSE dependent on $x$
- minimize by setting derivative equal to $0$
- next repeat for $V_{1,1}$ as $y$ ...
- there is a more general form optimizing each term by only considering the according rows or columns in $U,V$ and $M$

#### Complete UV-Decomposition Algorithm

- Preprocessing
  - normalizing $M$ through average user- and item-value
  - remember to *undo* this when done
- Initilizing $U,V$
  - ensure randomness to find global minimum by vary initial values or way of seeking
  - each element in $U,V$ with same value (so that average of products equals average of $M$)
  - $\sqrt{\frac{a}{d}}$ , where $a$ is the average of $M$ and $d$ is the number of concepts (= dimensionality)
  - possibly add normal distribution over starting values
- Ordering the optimization of the elements of $U,V$
  - choose order in which to optimize $U,V$ entries
  - possibly optimize same entry multiple times
  - either row-by-row or randomly permuted
- Ending the attempt at optimization
  - RMSE converges towards $0$ or some minimum
  - possibly use threshold or (stochastic) gradient descent
  - avoid overfitting (only move fraction, stop before convergence or average multiple different UV-Decompositions)

# Dimensionality Reduction

- general goal: high-dimensioned space but some are linear combinations
  of others \rightarrow find subspace with fewer dimensions

## Eigenpair Analysis
- **TODO**

## Singular Value Decomposition (SVD)
- goal: dimensionality reduction of matrices

### Definition
- Matrix $M$ with $m$ rows and $n$ columns
- rank $r$(number of independent rows)
- $SVD = U x SIGMA x V$, where
  - $U$ has $m,r$ dimensions, columns are unit vectors and orthogonal
    \rightarrow column-orthonormal
    - connects people to concepts
  - $V$ has $n,r$ dimensions, also column-orthonormal \rightarrow $V^T$ is
    row-orthonormal
    - connects items to concepts
  - $SIMGA$ is an $r,r$ diagonal matrix
    - specifies strengths of concepts
- Lossy reduction possible, by ignoring the least strong concept(s)
  ($SIGMA$)
  - compute sum of squares of singular values and retain at least 90%
  - measure of difference: Frobenius norm $||M||$ (proportional to RMSE)
  - error proportional to those $SIMGA_i$ changed

### Querying
-

## CUR Decomposition


# Machine Learning

## Types

### Supervised Learning (covered)
- labeled data

### Unsupervised Learning (not covered)
- unlabeled data
- example problem: clustering

## (Classification) Model
- known data (training set & test set)
    - feature vectors
    - correct classification
- train model on known data to learn some function $f$
- goal of finding best $f$
- Feature Selection

### Special cases
- Regression ($f(x)$ is a real number)
- Binary Classification ($f(x)$ is a boolean)
- Multiclass classification ($f(x)$ is a member of some finite set)

### Approaches
- Decision Tree
- Perceptron
    - Winnow algorithm
        - for positive examples (y = 1) that are wrongly classified,
          multiply weight vector by a factor > 1 but only for components of
          input vector that are 1
        - for negative examples, analogously multiply by a factor < 1
        - this addresses the weights that are responsible for the wrong
          classification directly
- Neural Network
- Instance-based Learning (includes not only input but all available
  data)
- Support Vector Machine (SVM)

### Machine Learning Architecture
- Split data into training- and test data sets
- avoid overfitting
    - cross validation
- Batch vs. online learning (learn once, continuous improvement)


## Learning from Nearest Neighbors
- training set is given
- find closest training examples for new query point
- estimate label by combining labels from closest neighbors
- open questions
    - what distance measure
    - how many nearest neighbors
    - weighting of neighbors
    - function for labeling from given neighbor labels

### Learning 1D-Functions
- for new point $x$ \rightarrow new label $y = f(x')$, where $x'$ are nearest
  neighbors of $x$
- possible methods for labeling $x$
    - copy label of nearest neighbor
    - average over $k$ nearest neighbors without or even with weighting
        - weighting ~ inverse distance
        - normalization over sum of weights



# Q & A
- content based recommendations \rightarrow user profiles
    - what do the values in a user profile mean intuitively?
        - only gives you information relative to other users, or in
          relation to items directly
        - can be useful in first step in analysis, e.g. for rating
          prediction, using the cosine distance
