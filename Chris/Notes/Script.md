# Data Mining

## What is Data Mining?
This term refers to the process of extracting useful models of data.
Sometimes, a model can be a summary of the data, or it can be the set of
most extreme features of the data.

### Statistical Modeling

### Machine Learning

### Computational Approaches

### Summarization

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

# Finding Similar Items

## Applications of Near-Neighbor Search

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

## Shingling of Documents

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


## Similarity-Preserving Summaries of Sets