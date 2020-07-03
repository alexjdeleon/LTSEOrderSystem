dir /s /B *.java | findstr /v /i "\Test*" > sources.txt
mkdir bin
javac -cp lib/* -d bin @sources.txt
jar cfm LTSEOrderSystem.jar manifest.txt -C lib . -C src/main/resources . -C bin .
rmdir /s /Q bin
del sources.txt