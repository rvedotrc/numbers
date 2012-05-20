#!/usr/bin/perl
# vi: set ts=4 sw=4 :

use warnings;
use strict;

use Test::More tests => 4;

my @out;
my @expect;

# target==0 means try all combinations
# for 3 and 4, no divide
@out = `./numbers-fast 0 4 3`;
@expect = split /\n/, <<EOF;
leaf = nums=1 (3) ops=1 (4) stack=1 (4)
leaf = nums=1 (4) ops=1 (3) stack=1 (3)
leaf = nums=0 () ops=3 (3 4 +) stack=1 (7)
leaf = nums=0 () ops=3 (3 4 -) stack=1 (1)
leaf = nums=0 () ops=3 (3 4 *) stack=1 (12)
EOF
check_all(\@out, \@expect);

# target==0 means try all combinations
# for 6 and 2, all four operators are possible
@out = `./numbers-fast 0 6 2`;
@expect = split /\n/, <<EOF;
leaf = nums=1 (2) ops=1 (6) stack=1 (6)
leaf = nums=1 (6) ops=1 (2) stack=1 (2)
leaf = nums=0 () ops=3 (2 6 +) stack=1 (8)
leaf = nums=0 () ops=3 (2 6 -) stack=1 (4)
leaf = nums=0 () ops=3 (2 6 *) stack=1 (12)
leaf = nums=0 () ops=3 (2 6 /) stack=1 (3)
EOF
check_all(\@out, \@expect);

# Specifying a target means we get progressively closer to the target
@out=`./numbers-fast 29 1 6 2 7`;
@expect = split /\n/, <<EOF;
leaf = nums=0 () ops=7 (1 2 7 - 6 * -) stack=1 (29)
leaf = nums=0 () ops=7 (1 2 6 - 7 * +) stack=1 (29)
EOF
ok(@out == 2 or $out[-3] !~ / \(29\)$/);
@out = @out[-2,-1];
check_all(\@out, \@expect);

sub check_all {
	my ($got, $want, $name) = @_;
	chomp @$got;
	chomp @$got;
	@$got = sort @$got;
	@$want = sort @$want;
	is_deeply $got, $want, $name;
}

# eof fast.t
