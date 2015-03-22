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
      value_a = a.kind_of?(Fixnum) ? a : a[:value]
      value_b = b.kind_of?(Fixnum) ? b : b[:value]
      if value_a != value_b
        return value_b <=> value_a
      end
      0
    end

  end
end
