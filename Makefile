SHELL   = /bin/sh
JC      = javac
JVM     = java
JFLAGS  = -g

Class  = MolecularMassCalculator
Source = $(Class).java


.PHONY: comp exec run


comp:
	$(JC) $(JFLAGS) $(Source)

exec:
	$(JVM) $(Class)

run: comp exec

