# Jaccard similarity

## Sets

### {1,2,3,4} and {2,3,5,7}
```
sim = |{2,3}| / |{1,2,3,4,5,7}| = 2 / 6 = 1 / 3
```
### {1,2,3,4} and {2,4,6}
```
sim = |{2,4}| / |{1,2,3,4,6}| = 2 / 5 = 0.4
```
### {2,3,5,7} and {2,4,6}
```
sim = |{2}| / |{2,3,4,5,6,7}| = 1 / 6
```

## Bags

### {1,1,1,2} and {1,1,2,2,3}
```
sim = |{1,1,2}| / |{1,1,1,1,1,2,2,2,3}| = 3 / 9 = 1 / 3
```
### {1,1,1,2} and {1,2,3,4}
```
sim = |{1,2}| / |{1,1,1,1,2,2,3,4}| = 2 / 8 = 1 / 4
```
### {1,1,2,2,3} and {1,2,3,4}
```
sim = |{1,2,3}| / |{1,1,1,2,2,2,3,3,4}| = 3 / 9 = 1 / 3
```


# Shingling

**Assumption**: white spaces are not ignored.

Sentence: *"The most effective way to represent documents as sets, for
the purpose of identifying lexically similar documents is to construct
from the document the set of short strings that appear within it."*

## k-shingles, k = 3
1. "The"
2. "he "
3. "e m"
4. " mo"
5. "mos"
6. "ost"
7. "st "
8. "t e"
9. " ef"
10. "eff"

## stop-word-shingles, k = 3
1. "The most effective"
2. "way to represent"
3. "to represent documents"
4. "as sets for"
5. "for the purpose"
6. "the purpose of"
7. "of identifying lexically"
8. "is to construct"
9. "to construct from"
10. "the document the"
11. "the set of"
12. "set of short"
13. "of short strings"

