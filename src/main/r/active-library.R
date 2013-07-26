ds = read.csv("c:/workspaces/git/pete/r-results.csv")
one <- subset(ds, NServ == 1)
two <- subset(ds, NServ == 2)
five <- subset(ds, NServ == 5)

numSummary(one$DDS,statistics=c("mean","sd"))
numSummary(one$EPC,statistics=c("mean","sd"))
numSummary(one$DE,statistics=c("mean","sd"))

numSummary(two$DDS,statistics=c("mean","sd"))
numSummary(two$EPC,statistics=c("mean","sd"))
numSummary(two$DE,statistics=c("mean","sd"))

numSummary(five$DDS,statistics=c("mean","sd"))
numSummary(five$EPC,statistics=c("mean","sd"))
numSummaryfive$DE,statistics=c("mean","sd"))