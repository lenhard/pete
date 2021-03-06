#load metric data; adjust this to the path of the result file written by pete
ds = read.csv("c:/workspaces/results.csv", sep=";")
library(Rcmdr)

# sanity checks
nrow(ds)
colnames(ds)
prop.table(summary(ds$isCorrectNamespace))
prop.table(summary(ds$referencesIssuesFound))
prop.table(summary(ds$isExecutable))

# reduced data set
#sane <- ds[ds$isCorrectNamespace=="true"&ds$referencesIssuesFound=="false",]
sane <- subset(ds,ds$isCorrectNamespace=='true' & ds$referencesIssuesFound=='false')

nrow(sane)
prop.table(summary(sane$isExecutable))

# plot element occurences
constructs = read.csv2("c:/workspaces/git/pete/raw.csv")
constructsSorted = constructs[order(constructs$number),]
constructsRemoved = constructsSorted[(constructsSorted$number > 150),]
constructsRemoved$number <- (constructsRemoved$number / 2995) * 100
list(constructsRemoved)
par(mar=c(6, 12, 4, 2) + 0.1)
#barplot(constructsRemoved$number, horiz=TRUE,names.arg=constructsRemoved$element, las=1,cex.names=0.8, xlab="Percentage of Processes", ylab="", xlim=c(0,100), mtext("BPMN Elements", side=2, line=10))
barplot(constructsRemoved$number, horiz=TRUE,names.arg=constructsRemoved$element, las=1,cex.names=0.8, xlab="Percentage of Processes", ylab="", xlim=c(0,100))

#group in executable and non-executable
exec <- subset(sane, sane$isExecutable=='true')
nonExec <- subset(sane, sane$isExecutable=='false')
nrow(exec)
nrow(nonExec)
list(exec)

# compute descriptive metric data
summary(exec$AD0.2)
sd(exec$AD0.2, na.rm=TRUE)
summary(exec$AD0.4)
sd(exec$AD0.4, na.rm=TRUE)
summary(exec$AD0.6)
sd(exec$AD0.6, na.rm=TRUE)
summary(exec$AD0.8)
sd(exec$AD0.8, na.rm=TRUE)
summary(exec$wAD)
sd(exec$wAD, na.rm=TRUE)

summary(nonExec$AD0.2)
sd(nonExec$AD0.2, na.rm=TRUE)
summary(nonExec$AD0.4)
sd(nonExec$AD0.4, na.rm=TRUE)
summary(nonExec$AD0.6)
sd(nonExec$AD0.6, na.rm=TRUE)
summary(nonExec$AD0.8)
sd(nonExec$AD0.8, na.rm=TRUE)
summary(nonExec$wAD)
sd(nonExec$wAD, na.rm=TRUE)

# check if the sets are normally distributed (Shapiro-Wilk)
shapiro.test(exec$AD0.2)
shapiro.test(exec$AD0.4)
shapiro.test(exec$AD0.6)
shapiro.test(exec$AD0.8)
shapiro.test(exec$wAD)

shapiro.test(nonExec$AD0.2)
shapiro.test(nonExec$AD0.4)
shapiro.test(nonExec$AD0.6)
shapiro.test(nonExec$AD0.8)
shapiro.test(nonExec$wAD)

# check for difference in the distributions (Mann-Whitney U)
wilcox.test(exec$AD0.2, nonExec$AD0.2, alternative="greater")
wilcox.test(exec$AD0.4, nonExec$AD0.4, alternative="greater")
wilcox.test(exec$AD0.6, nonExec$AD0.6, alternative="greater")
wilcox.test(exec$AD0.8, nonExec$AD0.8, alternative="greater")
wilcox.test(exec$wAD, nonExec$wAD, alternative="greater")

# compute amount of unique values
length(unique(exec$AD0.2)) / nrow(exec)
length(unique(exec$AD0.4)) / nrow(exec)
length(unique(exec$AD0.6)) / nrow(exec)
length(unique(exec$AD0.8)) / nrow(exec)
length(unique(exec$wAD)) / nrow(exec)

length(unique(nonExec$AD0.2)) / nrow(nonExec)
length(unique(nonExec$AD0.4)) / nrow(nonExec)
length(unique(nonExec$AD0.6)) / nrow(nonExec)
length(unique(nonExec$AD0.8)) / nrow(nonExec)
length(unique(nonExec$wAD)) / nrow(nonExec)

# correlation between wAD and AD0.6
summary(lm(exec$AD0.6 ~ exec$wAD))
summary(lm(nonExec$wAD ~ nonExec$AD0.6))

# compare small and large processes
summary(sane$elements)
small <- subset(ds,ds$elements < 7)
large <- subset(ds,ds$elements > 15)
nrow(small)
nrow(large)
wilcox.test(small$wAD,large$wAD, alternative="greater")

summary(exec$elements)
smallExec <- subset(exec,exec$elements < 10)
largeExec <- subset(exec,exec$elements > 50)
nrow(smallExec)
nrow(largeExec)
summary(smallExec$AD0.6)
summary(largeExec$AD0.6)
wilcox.test(smallExec$AD0.6,largeExec$AD0.6, alternative="greater")
summary(smallExec$wAD)
summary(largeExec$wAD)
wilcox.test(smallExec$wAD,largeExec$wAD, alternative="greater")

summary(nonExec$elements)
smallNonExec <- subset(nonExec,nonExec$elements < 7)
largeNonExec <- subset(nonExec,nonExec$elements > 13)
nrow(smallNonExec)
nrow(largeNonExec)
summary(smallNonExec$AD0.6)
summary(largeNonExec$AD0.6)
wilcox.test(smallNonExec$AD0.6,largeNonExec$AD0.6, alternative="greater")
summary(smallNonExec$wAD)
summary(largeNonExec$wAD)
wilcox.test(smallNonExec$wAD,largeNonExec$wAD, alternative="greater")