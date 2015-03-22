describe Numbers::Solver do

  it "should solve and give all the results" do
    output = <<EOF
leaf = nums=1 (2) ops=1 (2) stack=1 (2)
leaf = nums=0 () ops=3 (2 2 +) stack=1 (4)
leaf = nums=0 () ops=3 (2 2 *) stack=1 (4)
EOF

    runner = double("runner")
    expect(runner).to receive(:run).with([2,2], 4) { output }

    actual = Numbers::Solver.new(runner).solve([2,2], 4)
    expect(actual.count).to eq(3)

    expect(actual.map {|r| r[:unused_numbers]}).to eq([ [2], [], [] ])
    expect(actual.map {|r| r[:reverse_polish]}).to eq([ '2', '2 2 +', '2 2 *' ])
    expect(actual.map {|r| r[:string]}).to eq([ '2', '2 + 2', '2 * 2' ])
    expect(actual.map {|r| r[:result]}).to eq([ 2, 4, 4 ])
  end

end
