 #Examples commands
 
 * To start the application:
 * mvn package
 * java -cp ./target/L01-maven.jar ru.otus.l011.ru.otus.generics.Main //java.lang.NoClassDefFoundError: com/google/common/collect/Lists
 * java -jar ./target/L01-maven.jar
 * java -cp ./target/L01-maven.jar;{home_dir}\.m2\repository\com\google\guava\guava\23.0\guava-23.0.jar ru.otus.l011.ru.otus.generics.Main
 *
 * To unzip the jar:
 * 7z x -oJAR ./target/L01-maven.jar
 * unzip -d JAR ./target/L01-maven.jar
 * unzip -l target/L01-maven.jar
 *
 *
 * To build:
 * mvn package
 * mvn clean compile
 * mvn assembly:single
 * mvn clean compile assembly:single
