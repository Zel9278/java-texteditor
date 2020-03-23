javac -encoding UTF-8 -Xdiags:verbose -sourcepath src -d classes src/texteditor/Main.java
jar cvfm texteditor.jar META-INF/MANIFEST.MF -C classes .
java -jar texteditor.jar