#!/bin/bash

cd /home/ced/java-text
javac -Xdiags:verbose -sourcepath src -d classes src/texteditor/Main.java
jar cvfm texteditor.jar META-INF/MANIFEST.MF -C classes . resources/*
java -jar texteditor.jar
