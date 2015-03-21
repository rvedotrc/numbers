require 'numbers'

describe Numbers::RevpolToTree do

  it "should parse a number" do
    input = '7'
    actual = Numbers::RevpolToTree.parse input
    expect(actual).to eq(7)
  end

  it "should parse an addition" do
    input = '7 2 +'
    actual = Numbers::RevpolToTree.parse input
    expect(actual).to eq({ :+ => [ 7, 2 ] })
  end

end
