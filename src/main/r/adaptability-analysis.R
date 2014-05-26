ds = read.csv("c:/workspaces/git/pete/r-results.csv")
library(Rcmdr)

prop.table(summary(ds$isCorrectNamespace))
prop.table(summary(ds$referencesIssuesFound))
prop.table(summary(ds$isExecutable))

constructs = read.csv("c:/workspaces/git/pete/raw.csv")
constructsSorted = constructs[order(constructs$number),]
constructsRemoved = constructsSorted[(constructsSorted$number > 80),]
par(mar=c(6, 12, 4, 2) + 0.1)
barplot(constructsRemoved$number, horiz=TRUE,names.arg=constructsRemoved$element, las=1,cex.names=0.8)