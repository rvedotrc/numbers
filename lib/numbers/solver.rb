module Numbers
  class Solver

    def initialize(runner)
      @runner = runner
    end

    def solve(numbers, target)
      output = @runner.run(numbers, target)

      results = []

      output.each_line do |line|
        m = line.match /^leaf = nums=\d+ \((?<unused_numbers>.*?)\) ops=\d+ \((?<operators>.*?)\) stack=1 \((?<result>\d+)\)$/
        next unless m

        tree = Numbers::RevpolToTree.parse m["operators"]
        transformed_tree = Numbers::TreeTransformer.transform tree.first
        optimised_tree = Numbers::TreeOptimiser.coalesce transformed_tree
        normalised_tree = Numbers::TreeNormaliser.normalise optimised_tree
        string = Numbers::TreeToString.to_string normalised_tree

        results << {
          unused_numbers: m["unused_numbers"].split(' ').map(&:to_i),
          reverse_polish: m["operators"],
          # BEGIN untested
          tree1: tree,
          tree2: transformed_tree,
          tree3: optimised_tree,
          tree4: normalised_tree,
          # END untested
          string: string,
          result: m["result"].to_i,
        }
      end

      results
    end

  end

end
