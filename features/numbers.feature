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

