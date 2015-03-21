module Numbers
  class RevpolToTree

    def self.parse(input)
      stack = []

      input.split(' ').each do |op|
        if op.match /^\d+$/
          stack << op.to_i
        else
          stack << { :+ => [ stack.pop, stack.pop ].reverse }
        end
      end

      stack

    end

  end
end
