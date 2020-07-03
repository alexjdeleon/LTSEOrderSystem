#/bin/bash
find . -name "*.java" ! -name "*Test.java" > sources.txt
mkdir bin
javac -cp lib/* -d bin @sources.txt
jar cfm LTSEOrderSystem.jar manifest.txt -C lib . -C src/main/resources . -C bin .
rm sources.txt
rm -r bin