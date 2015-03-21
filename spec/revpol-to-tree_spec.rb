require 'numbers'

describe Numbers::RevpolToTree do

  it "should parse a number" do
    input = '7'
    actual = Numbers::RevpolToTree.parse input
    expect(actual).to eq(7)
  end

end
