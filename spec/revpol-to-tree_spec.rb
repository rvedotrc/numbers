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
    expect(actual).to eq([ { :+ => [ 7, 2 ] } ])
  end

  it "should should allow multiple trees to be returned" do
    input = '3 2 + 7'
    actual = Numbers::RevpolToTree.parse input
    expect(actual).to eq([ { :+ => [ 3, 2 ] }, 7 ])
  end

  it "should check for stack underrun on +" do
    expect {
      Numbers::RevpolToTree.parse '5 +'
    }.to raise_error Numbers::RevpolToTree::StackUnderrunException
  end

end
