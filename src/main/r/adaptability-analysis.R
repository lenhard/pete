ds = read.csv("c:/workspaces/git/pete/r-results.csv")
library(Rcmdr)

prop.table(summary(ds$isCorrectNamespace))