Feature: the REST server
    As the site administrator
    I want to provide 'numbers' as a REST service
    So that I can host it separately from the rest of the web site

    Scenario: closest answers first
        Given numbers "3 7 8"
        And target "12"
        When I run the solver
        Then the solutions are presented closest-first

    Scenario: default to not verbose
        Given numbers "3 7 8"
        And target "12"
        When I run the solver
        Then the solutions are not verbose

    Scenario: can ask for verbose mode
        Given numbers "3 7 8"
        And target "12"
        And verbose mode
        When I run the solver
        Then the solutions are verbose

    Scenario Outline: various invalid inputs
        Given numbers "<numbers>"
        And target "<target>"
        Then the solver should <reject> the request
        Examples:
            | reject | target  | numbers       | comment          |
            | accept | 1       | 1 2 3         | ok               |
            | reject | 0       | 1 2 3         | target zero      |
            | reject | -1      | 1 2 3         | target negative  |
            | accept | 1000000 | 1 2 3         | target ok (max)  |
            | reject | 1000001 | 1 2 3         | target too big   |
            | reject | 1       | 0 2 3         | number 0         |
            | reject | 1       | 1 2 3 4 5 6 7 | too many numbers |
            | reject | 1       | -1 2 3        | number negative  |
            | reject | 1       | 1 2 100000    | number too big   |
            | reject | 1       | 1 2 1001      | number too big   |
            | accept | 1       | 1 2 1000      | number ok        |

