# Social Network Comments

Data Analysis & Machine Learning on popular German supermarket's Social Network Comments published by Slovak users in Slovak language. Use Java Natural Language Processing (NLP) tools and the Apache Spark environment to gain insights about Slovak users most demanding topics. Most of NLP tools is for English language, there for the hardest part was to develop path in the field of limited language resources (e.g. non English or non popular world languages), to analyze real Slovak language data. The project is not self runnable, because the aim of the published work is to show hints for processing language which is limited in NLP resources as well as using Java / Scala on the computing cluster (well known in the field of Big Data).

## Basic Data Analysis & Machine Learning
* Text Analysis, e.g. word frequencies
* Sentiment Classification, e.g. the given user text comment expresses positive / neutral / negative sentiments (i.e. emotions)?
* Graph Analysis, e.g. how to discover hidden connections between words?
* Latent Dirichlet Allocation, e.g. use unsupervised learning to explore social network comment's clusters

Analysis & Machine Learning is developed in the Zeppelin Notebooks (you can find *.json files in src/ directory). If you don't have Zeppeline installed on your local computer, please look at *.pdf files in the doc/ directory.

## Data Acquisition
Use Python script to collect comments from the most popular social network page in Slovakia (the script is left out).

## Data Preprocessing
Only part of the data preprocessing Java program is published to prepare non English language data for analysis & machine learning tasks. The shared Java program deals with word frequencies on the Spark cluster computing environment. In case of interest for the whole program please drop a line.

## Used Technologies
* Python
* Java 
* Scala 
* Zeppelin Notebooks 
* Apache Hadoop / Apache Spark
* Docker Containers

## Dependencies
* Data Acquisition: Python script with libraries (random, pickle, time & requests).
* Data Preprocessing: Java program with packages (Jsoup, Spark) & local Apache Spark cluster
* Basic Data Analysis & Machine Learning: Scala with Zeppelin Notebooks on local Apache Spark cluster

## Run
Docker containers are in use to run parts of project. Let's see the directory Docker/ where the individual Bash scripts uses different containers. I'd like to share the collected social network comments for you to run parts of the project, but I'm not sure if this is legal (more detail available on [General Data Protection Regulation](https://ec.europa.eu/info/law/law-topic/data-protection_en)).

# License
Licensed under a MIT License.

# Help
As always, improvements, issues (socialnetworkanalysis/issues) & suggestions are well come. Feel free to connect with me ;)

## Roadmap
- [ ] put legal or simulation data to make project runnable
- [ ] interpret results - for non technical users e.g. the word frequencies, cluster labels, comment's sentiment classes, ... are almost self-explainable for someone, but the precise visual representation has a potential to add more insight
- [ ] develop unit tests
