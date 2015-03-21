module Numbers
  class TreeOptimiser

    # Transform a tree from [ :op, arg1, arg2 ] style
    # into { type, positive, negative, value } style

    MAKE_POSITIVE = {
      :- => :+,
      :/ => :*,
    }

    def self.transform(node)
      return node if node.kind_of? Fixnum
      op, x, y = node
      x = transform x
      y = transform y
      value = value_of(x).send(op, value_of(y))

      if MAKE_POSITIVE.has_key? op
        { type: MAKE_POSITIVE[op], positive: [x],   negative: [y], value: value }
      else
        { type: op,                positive: [x,y], negative: [],  value: value }
      end
    end

    def self.value_of(node)
      node.kind_of?(Fixnum) ? node : node[:value]
    end

  end
end
