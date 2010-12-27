#include <stdio.h>
#include <stdlib.h>

#define MAX_NUMS 10

int target = 0;
int best_diff = -1;

union operand {
	int n;
	char o;
};

struct op {
	int is_number;
	union operand operand;
};

struct state {
	int num_nums;
	int nums[MAX_NUMS];
	int num_ops;
	struct op ops[MAX_NUMS*2];
	int num_stack;
	int stack[MAX_NUMS];
};

void logdie(const char *s) {
	fprintf(stderr, "%s\n", s);
	fflush(stderr);
	exit(1);
}

int parsearg(const char *s) {
	int ans = 0;
	if (!*s) logdie("Failed to parse arguments");
	while (*s) {
		if (ans>1000) logdie("Argument too big");
		if (*s < '0' || *s > '9') logdie("Failed to parse arguments");
		ans = 10 * ans + (*s - '0');
		++s;
	}
	// fprintf(stdout, "arg=%d\n", ans);
	return ans;
}

void show_state(const struct state *s, const char *n) {
	int i;
	printf("%s =", n);

	printf(" nums=%d ", s->num_nums);
	for (i=0; i < s->num_nums; ++i) {
		printf("%c%d", (i ? ' ' : '('), s->nums[i]);
	}
	if (!i) printf("(");
	printf(")");

	printf(" ops=%d ", s->num_ops);
	for (i=0; i < s->num_ops; ++i) {
		if (s->ops[i].is_number) {
			printf("%c%d", (i ? ' ' : '('), s->ops[i].operand.n);
		} else {
			printf("%c%c", (i ? ' ' : '('), s->ops[i].operand.o);
		}
	}
	if (!i) printf("(");
	printf(")");

	printf(" stack=%d ", s->num_stack);
	for (i=0; i < s->num_stack; ++i) {
		printf("%c%d", (i ? ' ' : '('), s->stack[i]);
	}
	if (!i) printf("(");
	printf(")");

	printf("\n");
	// fflush(stdout);
}

void leaf(const struct state *s) {
	if (target == 0) {
		show_state(s, "leaf");
	} else {
		int diff = abs(s->stack[0] - target);
		if (best_diff < 0 || diff <= best_diff) {
			show_state(s, "leaf");
			best_diff = diff;
		}
	}
}

void try(const struct state *s) {
	struct state next_st;
	// printf("s=%p next_st=%p\n", s, &next_st);
	// show_state(s, "s");

	if (s->num_stack == 1) {
		leaf(s);
	} else if (s->num_stack >= 2) {
		int x = s->stack[ s->num_stack-1 ];
		int y = s->stack[ s->num_stack-2 ];

		next_st = *s;
		next_st.num_ops++;
		next_st.num_stack--;
		next_st.ops[ next_st.num_ops-1 ].is_number = 0;

		if (x>=y) { // shortcut
			// printf("push +\n");
			next_st.ops[ next_st.num_ops-1 ].operand.o = '+';
			next_st.stack[ next_st.num_stack-1 ] = x + y;
			try(&next_st);
		}

		if (x > y) { // avoid making 0
			// printf("push -\n");
			next_st.ops[ next_st.num_ops-1 ].operand.o = '-';
			next_st.stack[ next_st.num_stack-1 ] = x - y;
			try(&next_st);
		}

		if (x >= y && x > 1 && y > 1) { // shortcut, and avoid *1
			// printf("push *\n");
			next_st.ops[ next_st.num_ops-1 ].operand.o = '*';
			next_st.stack[ next_st.num_stack-1 ] = x * y;
			try(&next_st);
		}

		if (y > 1 && (x % y) == 0) { // avoid /1
			// printf("push /\n");
			next_st.ops[ next_st.num_ops-1 ].operand.o = '/';
			next_st.stack[ next_st.num_stack-1 ] = x / y;
			try(&next_st);
		}
	}

	if (s->num_nums) {
		int i,j;
		// push a number

		next_st = *s;
		next_st.num_nums--;
		next_st.num_ops++;
		next_st.ops[ next_st.num_ops-1 ].is_number = 1;
		next_st.num_stack++;

		for (i=0; i<s->num_nums; ++i) {
			int doneit = 0;
			for (j=0; j<i; ++j) {
				if (s->nums[i] == s->nums[j]) {
					doneit = 1;
					break;
				}
			}
			if (doneit) continue;

			// printf("push %d\n", s->nums[i]);

			next_st.nums[i] = next_st.nums[next_st.num_nums];
			next_st.ops[ next_st.num_ops-1 ].operand.n = s->nums[i];
			next_st.stack[ next_st.num_stack-1 ] = s->nums[i];
			try(&next_st);
			next_st.nums[i] = s->nums[i];
		}
	}
}

int main(int argc, char **argv) {
	int i;
	struct state st;

	--argc;
	++argv;

	if (argc > MAX_NUMS) logdie("Too many arguments");

	if (argc) {
		target = parsearg(argv[0]);
		--argc;
		++argv;
	}

	for (i=0; i<argc; ++i) {
		st.nums[i] = parsearg(argv[i]);
	}
	st.num_nums = argc;
	st.num_ops = 0;
	st.num_stack = 0;

	try(&st);

	// fprintf(stdout, "OK\n");
	fflush(stdout);
	exit(0);
}