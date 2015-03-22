require 'numbers'

describe Numbers::RevpolToTree do

  it "should parse a number" do
    input = '7'
    actual = Numbers::RevpolToTree.parse input
    expect(actual).to eq([ 7 ])
  end

  it "should parse an addition" do
    input = '7 2 +'
    actual = Numbers::RevpolToTree.parse input
    expect(actual).to eq([ [ :+, 2, 7 ] ])
  end

  it "should should allow multiple trees to be returned" do
    input = '3 2 + 7'
    actual = Numbers::RevpolToTree.parse input
    expect(actual).to eq([ [ :+, 2, 3 ], 7 ])
  end

  it "should check for stack underrun on +" do
    expect {
      Numbers::RevpolToTree.parse '5 + 3'
    }.to raise_error Numbers::RevpolToTree::StackUnderrunException do |e|
      expect(e.stack).to eq([5])
      expect(e.remaining_tokens).to eq(['+', '3'])
    end
  end

  it "should parse multiplication" do
    input = '7 2 *'
    actual = Numbers::RevpolToTree.parse input
    expect(actual).to eq([ [ :*, 2, 7 ] ])
  end

  it "should parse division" do
    input = '2 14 /'
    actual = Numbers::RevpolToTree.parse input
    expect(actual).to eq([ [ :/, 14, 2 ] ])
  end

  it "should parse subtraction" do
    input = '2 14 -'
    actual = Numbers::RevpolToTree.parse input
    expect(actual).to eq([ [ :-, 14, 2 ] ])
  end

  it "should reject other tokens" do
    expect {
      Numbers::RevpolToTree.parse '5 3 ^'
    }.to raise_error Numbers::RevpolToTree::UnknownTokenException do |e|
      expect(e.stack).to eq([5, 3])
      expect(e.remaining_tokens).to eq(['^'])
    end
  end

  it "should deal with a complex case" do
    input = '25 50 75 3 6 100 + * * - /'
    actual = Numbers::RevpolToTree.parse input
    expect(actual).to eq(
      [
        [ :/, [ :-, [ :*, [ :*, [ :+, 100, 6 ], 3 ], 75 ], 50 ], 25 ]
      ],
    )
  end

  it "should deal with a complex case 2" do
    input = '6 2 10 + 3 7 - * /'
    actual = Numbers::RevpolToTree.parse input
    expect(actual).to eq(
      [
        [ :/, [
                :*,
                [:-, 7, 3],
                [:+, 10, 2],
              ], 6 ]
      ],
    )
  end

end
