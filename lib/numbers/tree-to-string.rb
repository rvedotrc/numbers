module Numbers
  class TreeToString

    NEGATE_OP = {
      :* => :/,
      :+ => :-,
    }

    def self.to_string(node, need_brackets = false)
      return node.to_s if node.kind_of? Fixnum

      p = node[:positive].map {|arg| to_string(arg, node[:type] == :*)}
      n = node[:negative].map {|arg| to_string(arg, node[:type] == :*)}
    
      plus = node[:type].to_s
      minus = NEGATE_OP[node[:type]].to_s
      answer = p.join(" #{plus} ") + (n.map {|s| " #{minus} #{s}"}.join "")

      answer = "(#{answer})" if need_brackets

      answer
    end

  end
end
