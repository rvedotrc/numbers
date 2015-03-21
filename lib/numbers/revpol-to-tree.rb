module Numbers
  class RevpolToTree

    def self.parse(input)
      stack = []
      remaining_tokens = input.split ' '

      while !remaining_tokens.empty?
        op = remaining_tokens.shift

        if op.match /^\d+$/
          stack << op.to_i
        elsif %w[ + - * / ].include? op
          do_binop op, stack, remaining_tokens
        else
          raise UnknownTokenException.new(stack, [op, remaining_tokens].flatten)
        end
      end

      stack
    end

    def self.do_binop(op, stack, remaining_tokens)
      if stack.count < 2
        raise StackUnderrunException.new(stack, [op, remaining_tokens].flatten)
      end
      stack << [ op.to_sym, [ stack.pop, stack.pop ].reverse ].flatten
    end

    class ProcessingException < Exception
      attr_reader :stack, :remaining_tokens
      def initialize(stack, remaining_tokens)
        @stack = stack
        @remaining_tokens = remaining_tokens
      end
    end

    class StackUnderrunException < ProcessingException
    end

    class UnknownTokenException < ProcessingException
    end

  end

end
