module Numbers
  class Runner

    def initialize(path)
      @path = path
    end

    def run(numbers, target)
      cmd_and_args = [ @path, target.to_s ] + numbers.map(&:to_s)
      r, w = IO.pipe

      p = Process.spawn(
        *cmd_and_args,
        :in => :close,
        :out => w,
      )
      w.close

      lines = r.read
      r.close
      Process.wait p
      $?.success? or raise "Invocation of #{cmd_and_args} failed"
      lines
    end

  end

end
