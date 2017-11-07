# Bonferroni’s Principle
```
N_p:= e+9            // number of people
h:= e-3              // hotel going frequency per day
N_h:= e+5            // number of hotels
N_d:= e+3            // number of days with observed records

P(two people going to a hotel on the same day) = h*h = e-5 // := P(x1)
P(x1 & they choose the same hotel) = (h * h) / N_h = e-9   // := P(x2)
P(x2 twice) = ((h * h) / N_h)^2 = e-18                     // := P(E)

N_PP = (e+9)^2 / 2 = 5e+17            // number of people pairs
N_DP = (e+3)^2 / 2 = 5e+5             // number of day pairs

E(N_E) = N_PP * N_DP * P(E) = 250,000 // expected number of events
```

## a (=e+6)
```
N_p:= e+9            // number of people
h:= e-3              // hotel going frequency per day
N_h:= e+5            // number of hotels
N_d:= 2e+3           // number of days with observed records

P(two people going to a hotel on the same day) = h*h = e-5 // := P(x1)
P(x1 & they choose the same hotel) = (h * h) / N_h = e-9   // := P(x2)
P(x2 twice) = ((h * h) / N_h)^2 = e-18                     // := P(E)

N_PP = (e+9)^2 / 2 = 5e+17                // number of people pairs
N_DP = (2 * e+3)^2 / 2 = 2e+6             // number of day pairs

E(N_E) = N_PP * N_DP * P(E) = 1,000,000   // expected number of events
```

## b (=25)
```
N_p:= 2e+9            // number of people
h:= e-3               // hotel going frequency per day
N_h:= 2e+5            // number of hotels
N_d:= e+3             // number of days with observed records

P(two people going to a hotel on the same day) = h*h = e-5   // := P(x1)
P(x1 & they choose the same hotel) = (h * h) / N_h = 5e-12   // := P(x2)
P(x2 twice) = ((h * h) / N_h)^2 = 2.5e-23                    // := P(E)

N_PP = (2e+9)^2 / 2 = 2e+18              // number of people pairs
N_DP = (e+3)^2 / 2 = 5e+5                // number of day pairs

E(N_E) = N_PP * N_DP * P(E) = 25         // expected number of events
```
## c (=-6.1969123e+25)
```
N_p:= e+9            // number of people
h:= e-3              // hotel going frequency per day
N_h:= e+5            // number of hotels
N_d:= e+3            // number of days with observed records

P(two people going to a hotel on the same day) = h*h = e-5 // := P(x1)
P(x1 & they choose the same hotel) = (h * h) / N_h = e-9   // := P(x2)
P(x2 three times) = ((h * h) / N_h)^3 = -247.876493432     // := P(E)

N_PP = (e+9)^2 / 2 = 5e+17                   // number of people pairs
N_DP = (e+3)^2 / 2 = 5e+5                    // number of day pairs

E(N_E) = N_PP * N_DP * P(E) = -6.1969123e+25 // expected number of events
```
# Base of the natural logarithm

## a
```
a) (1.01)^500 ~ e^(0.01 * 500) = e ^ 5
b) (1.05)^1000 ~ e^(0.05 * 1000) = e ^ 50
c) (0.9)^40 ~ e^(-0.1 * 40) = e ^(-4)
```

## b

```
a) e^(1/10) ~ 1 + 1/10 + 1/200 + 1/6000 + 1/240000 ~ 1.105
b) e^(-1/10) ~ 1 - 1/10 + 1/200 - 1/6000 + 1/240000 ~ 0.905
c) e^2 ~ 1 + 2 + 4/2 + 8/6 + 16/24 ~ 7
```