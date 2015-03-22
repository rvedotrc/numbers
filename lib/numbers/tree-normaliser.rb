module Numbers
  class TreeNormaliser

    # Normalise a tree by deterministically ordering operand arguments
    # (The actual ordering is somewhat arbitrary)

    def self.normalise(node)
      return node if node.kind_of? Fixnum

      node.merge(
        positive: node[:positive].sort {|a,b| compare_operands a, b},
        negative: node[:negative].sort {|a,b| compare_operands a, b},
      )
    end

    private

    def self.compare_operands(a,b)
      if a.kind_of? Fixnum
        if b.kind_of? Fixnum
          b <=> a
        else
          -1 # a is num, b isn't, a goes before b, a < b
        end
      else
        if b.kind_of? Fixnum
          +1 # a isn't num, b is, a goes after b, a > b
        else
          b[:value] <=> a[:value] # TODO how to break ties?
        end
      end
    end

  end
end
