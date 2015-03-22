module Numbers
  class TreeNormaliser

    # Normalise a tree by deterministically ordering operand arguments
    # (The actual ordering is somewhat arbitrary)

    def self.normalise(node)
      return node if node.kind_of? Fixnum

      node.merge(
        positive: sort_args(node[:positive]),
        negative: sort_args(node[:negative]),
      )
    end

    private

    def self.sort_args(list)
      list.sort.reverse
    end

  end
end
