module Numbers
  class RevpolToTree

    def self.parse(input)
      stack = []
      remaining_tokens = input.split ' '

      while !remaining_tokens.empty?
        op = remaining_tokens.shift

        if op.match /^\d+$/
          stack << op.to_i
        else
          if stack.count < 2
            raise StackUnderrunException.new(stack, [op, remaining_tokens].flatten)
          end
          stack << { :+ => [ stack.pop, stack.pop ].reverse }
        end
      end

      stack
    end

    class StackUnderrunException < Exception
      attr_reader :stack, :remaining_tokens
      def initialize(stack, remaining_tokens)
        @stack = stack
        @remaining_tokens = remaining_tokens
      end
    end

  end

end
