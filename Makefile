all: numbers-fast test

test:
	# -./numbers a b c
	# -./numbers 1 2 ""
	# -./numbers 6 7 9991919
	-./numbers-fast 6 7 23

