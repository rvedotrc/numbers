.PHONY: all test

all: numbers-fast

test: numbers numbers-fast
	prove -r t
	rspec

