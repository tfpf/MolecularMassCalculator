SHELL    = /bin/sh
JC       = javac
JFLAGS   = -g -d classes/
JVM      = java
JVMFLAGS = -cp classes/
JAR      = jar cf

ClassName  = MolecularMassCalculator
Source     = src/*.java


.PHONY: comp exec run


comp:
	$(JC) $(JFLAGS) $(Source)

exec:
	$(JVM) $(JVMFLAGS) $(ClassName)

run: comp exec

