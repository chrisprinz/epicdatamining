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