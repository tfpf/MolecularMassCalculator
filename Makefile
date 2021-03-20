SHELL    = /bin/sh
JC       = javac
JFLAGS   = -g -d Classes/
JVM      = java
JVMFLAGS = -cp Classes/
JAR      = jar cf

ClassName  = MolecularMassCalculator
Source     = src/*.java


.PHONY: comp exec run


comp:
	$(JC) $(JFLAGS) $(Source)

exec:
	$(JVM) $(JVMFLAGS) $(ClassName)

run: comp exec

