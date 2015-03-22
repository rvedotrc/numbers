module Numbers
  class TreeNormaliser

    # Normalise a tree by deterministically ordering operand arguments
    # (The actual ordering is somewhat arbitrary)

    def self.normalise(node)
      return node if node.kind_of? Fixnum

      node.merge(
        positive: node[:positive].map {|c| normalise c}.sort {|a,b| compare_operands a, b},
        negative: node[:negative].map {|c| normalise c}.sort {|a,b| compare_operands a, b},
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
          if a[:value] != b[:value]
            b[:value] <=> a[:value]
          else
            # Two operators, which evaluate to the same result,
            # and (assuming proper coalescing has already happened) will be of
            # the same type, i.e. both :+ or both :*.
            # Compare by comparing their children.
            r = compare_operand_lists(a[:positive], b[:positive])
            r = compare_operand_lists(a[:negative], b[:negative]) if r == 0
            r
          end
        end
      end
    end

    def self.compare_operand_lists(list_a, list_b)
      list_a.zip list_b do |element_a, element_b|
        r = compare_operands element_a, element_b
        return r if r != 0
      end

      list_a.count <=> list_b.count
    end

  end
end
