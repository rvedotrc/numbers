#!/usr/bin/perl
# vi: set ts=4 sw=4 :

use warnings;
use strict;

use Test::More tests => 4;

my @out;
my @expect;

@out = `./numbers 4 3`;
@expect = split /\n/, <<EOF;
4 = 4
3 = 3
4 + 3 = 7
4 - 3 = 1
4 * 3 = 12
EOF
check_all(\@out, \@expect);

@out = `./numbers 6 2`;
@expect = split /\n/, <<EOF;
6 = 6
2 = 2
6 + 2 = 8
6 - 2 = 4
6 * 2 = 12
6 / 2 = 3
EOF
check_all(\@out, \@expect);

@out=`./numbers -t 29 1 6 2 7`;
@expect = split /\n/, <<EOF;
6 * (7 - 2) - 1 = 29
7 * (6 - 2) + 1 = 29
EOF
ok(@out == 2 or $out[-3] !~ / = 29$/);
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

# eof slow.t
