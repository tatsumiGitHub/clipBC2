.SUFFIXES: .java .class

JAVAC	= javac -encoding utf-8
JAVA	= java
SRC	= $(wildcard *.java)
CLASS	= $(SRC:.java=.class)
PROGRAM	= $(shell pwd)
MAIN	= Main

all: $(CLASS)

test: $(CLASS)
	$(JAVA) $(MAIN)

$(CLASS): $(SRC)

.java.class:
	$(JAVAC) $<

clean:;	\rm -f *.jar *.class */*.class */*/*.class */*/*/*.class \
		*~ */*~ */*/*~ */*/*/*~ \

jar:; jar -cvfm clipBC2.jar manifest.mf \
		*.class \
		*/*.class \
		*/*/*.class \
		*/*/*/*.class \

zip:;
	zip -r -o clipBC2.zip \
		*.java \
		*/*.java \
		*/*/*.java \
		*/*/*/*.java \
		configs/setup.config \
		img/* \
		manifest.mf \
		Makefile \
