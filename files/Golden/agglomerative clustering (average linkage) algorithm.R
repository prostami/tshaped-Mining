require(pvclust)
require(fastcluster)
require(cluster)
require(mclust)
require(data.table)
require(readr)

rm(list=ls())
datasetName <- readline(prompt = "Enter dataSet Name:")

path1 <-paste("C:/ECIR2018/Golden/",datasetName,"/",datasetName,"Similarity.csv",collapse = '',sep = '')
path2 <- paste("C:/ECIR2018/Golden/",datasetName,"/topFrequntTags.txt",collapse = '',sep = '')

SimilarityMatrix <- read_csv(path1, col_names = FALSE)
TagsList <<- read_csv(path2, col_names = FALSE)
colnames(SimilarityMatrix) <- TagsList$X1
rownames(SimilarityMatrix) <- TagsList$X1

d <- dist(SimilarityMatrix, method = "euclidean") # distance matrix
fit <- hclust(d,method ="average") # clustering
plot(fit)

##then export pdf from the dendogram at 12 * 45 Inch in Path: C:/ECIR2018/Golden/dataSetName