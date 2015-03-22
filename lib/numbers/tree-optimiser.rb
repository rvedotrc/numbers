module Numbers
  class TreeOptimiser

    # Optimise a tree by joining together addition/subtraction, and
    # multiplication/division nodes where possible.

    def self.coalesce(node)
      return node if node.kind_of? Fixnum

      pos = node[:positive].map {|n| coalesce n}
      neg = node[:negative].map {|n| coalesce n}

      node.merge(
        positive: positive_negative(pos, neg, node[:type]),
        negative: positive_negative(neg, pos, node[:type]),
      )
    end

    private

    def self.positive_negative(pos, neg, type)
      (pos.map do |child|
        if child.kind_of? Fixnum or child[:type] != type
          child
        else
          child[:positive]
        end
      end + neg.map do |child|
        if child.kind_of? Fixnum or child[:type] != type
          []
        else
          child[:negative]
        end
      end).flatten
    end

  end
end
