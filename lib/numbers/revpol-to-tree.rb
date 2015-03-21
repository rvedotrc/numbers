module Numbers
  class RevpolToTree

    def self.parse(input)
      stack = []

      input.split(' ').each do |op|
        if op.match /^\d+$/
          stack << op.to_i
        else
          if stack.count < 2
            raise StackUnderrunException.new
          end
          stack << { :+ => [ stack.pop, stack.pop ].reverse }
        end
      end

      stack
    end

    class StackUnderrunException < Exception
    end

  end

end
