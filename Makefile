.PHONY: all test

all: numbers-fast

test: numbers numbers-fast
	prove -vr t

