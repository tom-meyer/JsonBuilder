#!/bin/sh
cd test
javac -cp ..:junit-4.12.jar JsonBuilderTest.java && \
java -cp .:..:junit-4.12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore JsonBuilderTest
