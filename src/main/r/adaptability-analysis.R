#load metric data
ds = read.csv("c:/workspaces/git/pete/r-results.csv")
library(Rcmdr)

# sanity checks
nrow(ds)
prop.table(summary(ds$isCorrectNamespace))
prop.table(summary(ds$referencesIssuesFound))
prop.table(summary(ds$isExecutable))

# plot element occurences
constructs = read.csv("c:/workspaces/git/pete/raw.csv")
constructsSorted = constructs[order(constructs$number),]
constructsRemoved = constructsSorted[(constructsSorted$number > 150),]
constructsRemoved$number <- (constructsRemoved$number / 2995) * 100
par(mar=c(6, 12, 4, 2) + 0.1)
barplot(constructsRemoved$number, horiz=TRUE,names.arg=constructsRemoved$element, las=1,cex.names=0.8)

#group in exec, non-exec
exec <- subset(ds, ds$isExecutable=='true')
nonExec <- subset(ds, ds$isExecutable=='false')
nrow(exec)
nrow(nonExec)

# compute descriptive metric data
summary(exec$AD0.2)
summary(exec$AD0.4)
summary(exec$AD0.6)
summary(exec$AD0.8)
summary(exec$wAD)

summary(nonExec$AD0.2)
summary(nonExec$AD0.4)
summary(nonExec$AD0.6)
summary(nonExec$AD0.8)
summary(nonExec$wAD)

# compute amount of unique values
unique(exec$AD0.2)
unique(exec$AD0.4)
unique(exec$AD0.6)
unique(exec$AD0.8)
unique(exec$wAD)

unique(nonExec$AD0.2)
unique(nonExec$AD0.4)
unique(nonExec$AD0.6)
unique(nonExec$AD0.8)
unique(nonExec$wAD)

# correlation between wAD and AD0.6
summary(lm(exec$AD0.6 ~ exec$wAD))
summary(lm(nonExec$wAD ~ nonExec$AD0.6))

# compare small and large processes
summary(ds$elements)
small <- subset(ds,ds$elements < 7)
large <- subset(ds,ds$elements > 15)
nrow(small)
nrow(large)
wilcox.test(small$wAD,large$wAD, paired=TRUE)