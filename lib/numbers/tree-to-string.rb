module Numbers
  class TreeToString

    NEGATE_OP = {
      :* => :/,
      :+ => :-,
    }

    def self.to_string(node)
      return node.to_s if node.kind_of? Fixnum

       p = node[:positive].map {|arg| to_string(arg)}
       n = node[:negative].map {|arg| to_string(arg)}
    
       plus = node[:type].to_s
       minus = NEGATE_OP[node[:type]].to_s
       p.join(" #{plus} ") + (n.map {|s| " #{minus} #{s}"}.join "")
    end

  end
end
