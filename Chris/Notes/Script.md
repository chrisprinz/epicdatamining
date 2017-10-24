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
**Summarize**
If we are willing to view as an interesting feature of data something of
which many instances can be expected to exist in random data, then we
cannot rely on such features being significant. This observation limits
our ability to mine data for features that are not sufficiently rare in
practice.

Not all assumptions that might make sense are statistically significant. 

## Useful Things to know

### Importance of Words in Documents

TF (Term-Frequency)
  : How often does a word occur in a document?
  
IDF (Inverse Document Frequency)
  : 
  
TF.IDF (Combination of TF and IDF)
  : **Summarize** The measure called TF.IDF lets us identify words in a collection of
  documents that are useful for determining the topic of each document.
  A word has high TF.IDF score in a document if it appears in relatively
  few documents, but appears in this one, and when it appears in a
  document it tends to appear many times.

### Hash Functions

- represent elements of interest as numbers (e.g. through ASCII-Code)
- (build groups of elements if desired and sum up their values from
previous step)
- create hash function (e.g. mod) that divides homogeneous data into
equal "buckets"

**Summarize**
A hash function maps hash-keys of some data type to integer bucket
numbers. A good hash function distributes the possible hash-key values
approximately evenly among buckets. Any data type can be the domain of
a hash function.

### Indices

Index
  : Data structure with information on how to retrieve objects
  efficiently
  
Object
  : Record with fields
  
- might be combined with hashing

**Summarize**
An index is a data structure that allows us to store and retrieve data
records efficiently, given the value in one or more of the fields of the
record. Hashing is one way to build an index.

### Secondary Storage

**Summarize**
When data must be stored on disk (secondary memory), it takes very much
more time to access a desired data item than if the same data were
stored in main memory. When data is large, it is important that
algorithms strive to keep needed data in main memory.

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

**Summarize**
Many phenomena obey a law that can be expressed as y = cxa for some
power a, often around −2. Such phenomena include the sales of the xth
most popular book, or the number of in-links to the xth most popular
page.