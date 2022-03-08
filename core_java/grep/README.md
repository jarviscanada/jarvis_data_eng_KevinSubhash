# Introduction
This is a Java Grep App which acts like the grep command in the Linux command line tool. This application recursively searches the files in the directory following a regex pattern and outputs the lines 
found into a file. The technologies used for this project are Java, Maven, Lambda & Stream API, and Intellij.

# Quick Start
```
1. mvn clean compile project
2. java -jar target/grep-1.0-SNAPSHOT.jar  <regex> <rootDirectory> <outputFile>
3. cat <outputFile>
```
# Implemenation
## Pseudocode
`process` method pseudocode.
```
matchedLines = []
for every file in listFilesRecursively(rootDir)
    for every line in readLines(file)
        if line follows regex pattern
            add line to matchedLines
write every from matchedLines to a file
```
## Performance Issue
This implementation has memory issues. In particular, it has "not enough memory" issues. This is because the files being used are large. To fix this issue, 
introducing lamba functions as well as streams will help with the amount of memory being used.

# Test
In terms of testing, few sample files were used to test the application via the terminal in Intellij.

# Deployment
I dockerized my app for easier distribution. For this to be done, I created the neccessary dockerfile. After this was done, I packaged the java app, built a new docker image locally, verified the image 
ran the docker contrainer then finally pushed the image to Docker Hub to kevinsub/grep.

# Improvement
1. Improve on the memory issues further
2. Adding flag capabilities 
3. Indicating the user with any errors to save time instead of just opening the file and finding out it failed.
