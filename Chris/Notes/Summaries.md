# Summaries



## 1 Data Mining



✦ *Data Mining*: This term refers to the process of extracting useful models of data. Sometimes, a model can be a summary of the data, or it can be the set of most extreme features of the data.

✦ *Bonferroni’s Principle*: If we are willing to view as an interesting feature of data something of which many instances can be expected to exist in random data, then we cannot rely on such features being significant. This observation limits our ability to mine data for features that are not sufficiently rare in practice.

✦ *TF.IDF*: The measure called TF.IDF lets us identify words in a collection of documents that are useful for determining the topic of each document. A word has high TF.IDF score in a document if it appears in relatively few documents, but appears in this one, and when it appears in a document it tends to appear many times.

✦ *Hash Functions*: A hash function maps hash-keys of some data type to integer bucket numbers. A good hash function distributes the possible hash-key values approximately evenly among buckets. Any data type can be the domain of a hash function.

✦ *Indexes*: An index is a data structure that allows us to store and retrieve data records efficiently, given the value in one or more of the fields of the record. Hashing is one way to build an index.

✦ *Storage on Disk*: When data must be stored on disk (secondary memory), it takes very much more time to access a desired data item than if the same data were stored in main memory. When data is large, it is important that algorithms strive to keep needed data in main memory.

✦ *Power Laws*: Many phenomena obey a law that can be expressed as $y = cx^a$ for some power $a$, often around $−2$. Such phenomena include the sales of the $x$th most popular book, or the number of in-links to the $x$th most popular page.



## 3 Finding Similar Items



✦ *Jaccard Similarity*: The Jaccard similarity of sets is the ratio of the size of the intersection of the sets to the size of the union. This measure of similarity is suitable for many applications, including textual similarity of documents and similarity of buying habits of customers.

✦ *Shingling*: A $k$-shingle is any $k$ characters that appear consecutively in a document. If we represent a document by its set of $k$-shingles, then the Jaccard similarity of the shingle sets measures the textual similarity of documents. Sometimes, it is useful to hash shingles to bit strings of shorter length, and use sets of hash values to represent documents.

✦ *Minhashing*: A minhash function on sets is based on a permutation of the universal set. Given any such permutation, the minhash value for a set is that element of the set that appears first in the permuted order.

✦ *Minhash Signatures*: We may represent sets by picking some list of permutations and computing for each set its minhash signature, which is the sequence of minhash values obtained by applying each permutation on the list to that set. Given two sets, the expected fraction of the permutations that will yield the same minhash value is exactly the Jaccard similarity of the sets.

✦ *Efficient Minhashing*: Since it is not really possible to generate random permutations, it is normal to simulate a permutation by picking a random hash function and taking the minhash value for a set to be the least hash value of any of the set’s members.

✦ *Locality-Sensitive Hashing for Signatures*: This technique allows us to avoid computing the similarity of every pair of sets or their minhash signatures. If we are given signatures for the sets, we may divide them into bands, and only measure the similarity of a pair of sets if they are identical in at least one band. By choosing the size of bands appropriately, we can eliminate from consideration most of the pairs that do not meet our threshold of similarity.

✦ *Distance Measures*: A distance measure is a function on pairs of points in a space that satisfy certain axioms. The distance between two points is $0$ if the points are the same, but greater than $0 $ if the points are different. The distance is symmetric; it does not matter in which order we consider the two points. A distance measure must satisfy the triangle inequality: the distance between two points is never more than the sum of the distances between those points and some third point.

✦ *Euclidean Distance*: The most common notion of distance is the Euclidean distance in an $n$-dimensional space. This distance, sometimes called the $L_2$-norm, is the square root of the sum of the squares of the differences between the points in each dimension. Another distance suitable for Euclidean spaces, called Manhattan distance or the $L_1$-norm is the sum of the magnitudes of the differences between the points in each dimension.

✦ *Jaccard Distance*: One minus the Jaccard similarity is a distance measure, called the Jaccard distance.

✦ *Cosine Distance*: The angle between vectors in a vector space is the cosine distance measure. We can compute the cosine of that angle by taking the dot product of the vectors and dividing by the lengths of the vectors.

✦ *Edit Distance*: This distance measure applies to a space of strings, and is the number of insertions and/or deletions needed to convert one string into the other. The edit distance can also be computed as the sum of the lengths of the strings minus twice the length of the longest common subsequence of the strings.

✦ *Hamming Distance*: This distance measure applies to a space of vectors. The Hamming distance between two vectors is the number of positions in which the vectors differ.

✦ *Generalized Locality-Sensitive Hashing*: We may start with any collection of functions, such as the minhash functions, that can render a decision as to whether or not a pair of items should be candidates for similarity checking. The only constraint on these functions is that they provide a lower bound on the probability of saying “yes” if the distance (according to some distance measure) is below a given limit, and an upper bound on the probability of saying “yes” if the distance is above another given limit. We can then increase the probability of saying “yes” for nearby items and at the same time decrease the probability of saying “yes” for distant items to as great an extent as we wish, by applying an AND construction and an OR construction.

✦ *Random Hyperplanes and LSH for Cosine Distance*: We can get a set of basis functions to start a generalized LSH for the cosine distance measure by identifying each function with a list of randomly chosen vectors. We apply a function to a given vector $v$ by taking the dot product of $v$ with each vector on the list. The result is a sketch consisting of the signs ($+1$ or $−1$) of the dot products. The fraction of positions in which the sketches of two vectors agree, multiplied by $180$, is an estimate of the angle between the two vectors.



## 7 Clustering



✦ *Clustering*: Clusters are often a useful summary of data that is in the form of points in some space. To cluster points, we need a distance measure on that space. Ideally, points in the same cluster have small distances between them, while points in different clusters have large distances between them.

✦ *Clustering Algorithms*: Clustering algorithms generally have one of two forms. Hierarchical clustering algorithms begin with all points in a cluster of their own, and nearby clusters are merged iteratively. Point-assignment clustering algorithms consider points in turn and assign them to the cluster in which they best fit.

✦ *The Curse of Dimensionality*: Points in high-dimensional Euclidean spaces, as well as points in non-Euclidean spaces often behave unintuitively. Two unexpected properties of these spaces are that random points are almost always at about the same distance, and random vectors are almost always orthogonal.

✦ *Centroids and Clustroids*: In a Euclidean space, the members of a cluster can be averaged, and this average is called the centroid. In non-Euclidean spaces, there is no guarantee that points have an “average,” so we are forced to use one of the members of the cluster as a representative or typical element of the cluster. That representative is called the clustroid.

✦ *Choosing the Clustroid*: There are many ways we can define a typical point of a cluster in a non-Euclidean space. For example, we could choose the point with the smallest sum of distances to the other points, the smallest sum of the squares of those distances, or the smallest maximum distance to any other point in the cluster.

✦ *Radius and Diameter*: Whether or not the space is Euclidean, we can define the radius of a cluster to be the maximum distance from the centroid or clustroid to any point in that cluster. We can define the diameter of the cluster to be the maximum distance between any two points in the cluster. Alternative definitions, especially of the radius, are also known, for example, average distance from the centroid to the other points.

✦ *Hierarchical Clustering*: This family of algorithms has many variations, which differ primarily in two areas. First, we may chose in various ways which two clusters to merge next. Second, we may decide when to stop the merge process in various ways.

✦ *Picking Clusters to Merge*: One strategy for deciding on the best pair of clusters to merge in a hierarchical clustering is to pick the clusters with the closest centroids or clustroids. Another approach is to pick the pair of clusters with the closest points, one from each cluster. A third approach is to use the average distance between points from the two clusters.

✦ *Stopping the Merger Process*: A hierarchical clustering can proceed until there are a fixed number of clusters left. Alternatively, we could merge until it is impossible to find a pair of clusters whose merger is sufficiently compact, e.g., the merged cluster has a radius or diameter below some threshold. Another approach involves merging as long as the resulting cluster has a sufficiently high “density,” which can be defined in various ways, but is the number of points divided by some measure of the size of the cluster, e.g., the radius.

✦ *K-Means Algorithms*: This family of algorithms is of the point-assignment type and assumes a Euclidean space. It is assumed that there are exactly $k$ clusters for some known $k$. After picking $k$ initial cluster centroids, the points are considered one at a time and assigned to the closest centroid. The centroid of a cluster can migrate during point assignment, and an optional last step is to reassign all the points, while holding the centroids fixed at their final values obtained during the first pass.

✦ *Initializing K-Means Algorithms*: One way to find $k$ initial centroids is to pick a random point, and then choose $k − 1$ additional points, each as far away as possible from the previously chosen points. An alternative is to start with a small sample of points and use a hierarchical clustering to merge them into k clusters.

✦ *Picking K in a K-Means Algorithm*: If the number of clusters is unknown, we can use a binary-search technique, trying a $k$-means clustering with different values of $k$. We search for the largest value of k for which a decrease below k clusters results in a radically higher average diameter of the clusters. This search can be carried out in a number of clustering operations that is logarithmic in the true value of $k$.

✦ *The BFR Algorithm*: This algorithm is a version of $k$-means designed to handle data that is too large to fit in main memory. It assumes clusters are normally distributed about the axes.

✦ *Representing Clusters in BFR*: Points are read from disk one chunk at a time. Clusters are represented in main memory by the count of the number of points, the vector sum of all the points, and the vector formed by summing the squares of the components of the points in each dimension. Other collection of points, too far from a cluster centroid to be included in a cluster, are represented as “miniclusters” in the same way as the $k$ clusters, while still other points, which are not near any other point will be represented as themselves and called “retained” points.

✦ *Processing Points in BFR*: Most of the points in a main-memory load will be assigned to a nearby cluster and the parameters for that cluster will be adjusted to account for the new points. Unassigned points can be formed into new miniclusters, and these miniclusters can be merged with previously discovered miniclusters or retained points. After the last memory load, the miniclusters and retained points can be merged to their nearest cluster or kept as outliers.

✦ *The GRGPF Algorithm*: This algorithm is of the point-assignment type. It handles data that is too big to fit in main memory, and it does not assume a Euclidean space.

✦ *Representing Clusters in GRGPF*: A cluster is represented by the count of points in the cluster, the clustroid, a set of points nearest the clustroid and a set of points furthest from the clustroid. The nearby points allow us to change the clustroid if the cluster evolves, and the distant points allow for merging clusters efficiently in appropriate circumstances. For each of these points, we also record the rowsum, that is the square root of the sum of the squares of the distances from that point to all the other points of the cluster.

✦ *Tree Organization of Clusters in GRGPF*: Cluster representations are organized into a tree structure like a B-tree, where nodes of the tree are typically disk blocks and contain information about many clusters. The leaves hold the representation of as many clusters as possible, while interior nodes hold a sample of the clustroids of the clusters at their descendant leaves. We organize the tree so that the clusters whose representatives are in any subtree are as close as possible.

✦ *Processing Points in GRGPF*: After initializing clusters from a sample of points, we insert each point into the cluster with the nearest clustroid. Because of the tree structure, we can start at the root and choose to visit the child with the sample clustroid nearest to the given point. Following this rule down one path in the tree leads us to a leaf, where we insert the point into the cluster with the nearest clustroid on that leaf.



## 9 Recommendation Systems



✦ *Utility Matrices*: Recommendation systems deal with users and items. A utility matrix offers known information about the degree to which a user likes an item. Normally, most entries are unknown, and the essential problem of recommending items to users is predicting the values of the unknown entries based on the values of the known entries.

✦ *Two Classes of Recommendation Systems*: These systems attempt to predict a user’s response to an item by discovering similar items and the response of the user to those. One class of recommendation system is content-based; it measures similarity by looking for common features of the items. A second class of recommendation system uses collaborative filtering; these measure similarity of users by their item preferences and/or measure similarity of items by the users who like them.

✦ *Item Profiles*: These consist of features of items. Different kinds of items have different features on which content-based similarity can be based. Features of documents are typically important or unusual words. Products have attributes such as screen size for a television. Media such as movies have a genre and details such as actor or performer. Tags can also be used as features if they can be acquired from interested users.

✦ *User Profiles*: A content-based collaborative filtering system can construct profiles for users by measuring the frequency with which features appear in the items the user likes. We can then estimate the degree to which a user will like an item by the closeness of the item’s profile to the user’s profile.

✦ *Similarity of Rows and Columns of the Utility Matrix*: Collaborative filtering algorithms must measure the similarity of rows and/or columns of the utility matrix. Jaccard distance is appropriate when the matrix consists only of 1’s and blanks (for “not rated”). Cosine distance works for more general values in the utility matrix. It is often useful to normalize the utility matrix by subtracting the average value (either by row, by column, or both) before measuring the cosine distance.

✦ *UV-Decomposition*: One way of predicting the blank values in a utility matrix is to find two long, thin matrices $U$ and $V$, whose product is an approximation to the given utility matrix. Since the matrix product $UV$ gives values for all user-item pairs, that value can be used to predict the value of a blank in the utility matrix. The intuitive reason this method makes sense is that often there are a relatively small number of issues (that number is the “thin” dimension of $U$ and $V$) that determine whether or not a user likes an item.

✦ *Root-Mean-Square Error*: A good measure of how close the product $UV$ is to the given utility matrix is the **RMSE** (root-mean-square error). The RMSE is computed by averaging the square of the differences between $UV$ and the utility matrix, in those elements where the utility matrix is nonblank. The square root of this average is the RMSE.

✦ *Computing U and V*: One way of finding a good choice for $U$ and $V$ in a UV-decomposition is to start with arbitrary matrices $U$ and $V$. Repeatedly adjust one of the elements of $U$ or $V$ to minimize the RMSE between the product $UV$ and the given utility matrix. The process converges to a local optimum, although to have a good chance of obtaining a global optimum we must either repeat the process from many starting matrices, or search from the starting point in many different ways.



## 11 Dimensionality Reduction



✦ *Eigenvalues and Eigenvectors*: A matrix may have several eigenvectors such that when the matrix multiplies the eigenvector, the result is a constant multiple of the eigenvector. That constant is the eigenvalue associated with this eigenvector. Together the eigenvector and its eigenvalue are called an eigenpair.

✦ *Singular-Value Decomposition*: The singular-value decomposition of a matrix consists of three matrices, $U$, $Σ$, and $V$. The matrices $U$ and $V$ are column-orthonormal, meaning that as vectors, the columns are orthogonal, and their lengths are $1$. The matrix $Σ$ is a diagonal matrix, and the values along its diagonal are called singular values. The product of $U$, $Σ$, and the transpose of $V$ equals the original matrix.

✦ *Concepts*: SVD is useful when there are a small number of concepts that connect the rows and columns of the original matrix. For example, if the original matrix represents the ratings given by movie viewers (rows) to movies (columns), the concepts might be the genres of the movies. The matrix $U$ connects rows to concepts, $Σ$ represents the strengths of the concepts, and $V$ connects the concepts to columns.

✦ *Queries Using the Singular-Value Decomposition*: We can use the decomposition to relate new or hypothetical rows of the original matrix to the concepts represented by the decomposition. Multiply a row by the matrix $V$ of the decomposition to get a vector indicating the extent to which that row matches each of the concepts.



✦ *Using SVD for Dimensionality Reduction*: In a complete SVD for a matrix, $U$ and $V$ are typically as large as the original. To use fewer columns for $U$ and $V$, delete the columns corresponding to the smallest singular values from $U$, $V$, and $Σ$. This choice minimizes the error in reconstructing the original matrix from the modified $U$, $Σ$, and $V$.

✦ *Decomposing Sparse Matrices*: Even in the common case where the given matrix is sparse, the matrices constructed by SVD are dense. The CUR decomposition seeks to decompose a sparse matrix into sparse, smaller matrices whose product approximates the original matrix.

✦ *CUR Decomposition*: This method chooses from a given sparse matrix a set of columns $C$ and a set of rows $R$, which play the role of $U$ and $V^T$ in SVD; the user can pick any number of rows and columns. The choice of rows and columns is made randomly with a distribution that depends on the Frobenius norm, or the square root of the sum of the squares of the elements. Between $C$ and $R$ is a square matrix called $U$ that is constructed by a pseudo-inverse of the intersection of the chosen rows and columns.



## 12 Large-Scale Machine Learning



✦ *Training Sets*: A training set consists of a feature vector, each component of which is a feature, and a label indicating the class to which the object represented by the feature vector belongs. Features can be categorical – belonging to an enumerated list of values – or numerical.

✦ *Test Sets and Overfitting*: When training some classifier on a training set, it is useful to remove some of the training set and use the removed data as a test set. After producing a model or classifier without using the test set, we can run the classifier on the test set to see how well it does. If the classifier does not perform as well on the test set as on the training set used, then we have overfit the training set by conforming to peculiarities of the training-set data which is not present in the data as a whole.

✦ *Batch Versus On-Line Learning*: In batch learning, the training set is available at any time and can be used in repeated passes. On-line learning uses a stream of training examples, each of which can be used only once.

✦ *Perceptrons*: This machine-learning method assumes the training set has only two class labels, positive and negative. Perceptrons work when there is a hyperplane that separates the feature vectors of the positive examples from those of the negative examples. We converge to that hyperplane by adjusting our estimate of the hyperplane by a fraction – the learning rate – of the direction that is the average of the currently misclassified points.

✦ *The Winnow Algorithm*: This algorithm is a variant of the perceptron algorithm that requires components of the feature vectors to be $0$ or $1$. Training examples are examined in a round-robin fashion, and if the current classification of a training example is incorrect, the components of the estimated separator where the feature vector has $1$ are adjusted up or down, in the direction that will make it more likely this training example is correctly classified in the next round.

✦ *Nonlinear Separators*: When the training points do not have a linear function that separates two classes, it may still be possible to use a perceptron to classify them. We must find a function we can use to transform the points so that in the transformed space, the separator is a hyperplane.

✦ *Support-Vector Machines*: The SVM improves upon perceptrons by finding a separating hyperplane that not only separates the positive and negative points, but does so in a way that maximizes the margin – the distance perpendicular to the hyperplane to the nearest points. The points that lie exactly at this minimum distance are the support vectors. Alternatively, the SVM can be designed to allow points that are too close to the hyperplane, or even on the wrong side of the hyperplane, but minimize the error due to such misplaced points.

✦ *Nearest-Neighbor Learning*: In this approach to machine learning, the entire training set is used as the model. For each (“query”) point to be classified, we search for its k nearest neighbors in the training set. The classification of the query point is some function of the labels of these $k$ neighbors. The simplest case is when $k = 1$, in which case we can take the label of the query point to be the label of the nearest neighbor.