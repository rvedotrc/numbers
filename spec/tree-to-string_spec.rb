require 'numbers'

describe Numbers::TreeToString do

  it "should handle a number" do
    input = 7
    actual = Numbers::TreeToString.to_string input
    expect(actual).to eq("7")
  end

  it "should handle addition" do
    input = { type: :+, positive: [6,5,4], negative: [3,2,1], value: 9 }
    actual = Numbers::TreeToString.to_string input
    expect(actual).to eq("6 + 5 + 4 - 3 - 2 - 1")
  end

  it "should handle multiplication" do
    input = { type: :*, positive: [6,5,4], negative: [3,2,1], value: 20 }
    actual = Numbers::TreeToString.to_string input
    expect(actual).to eq("6 * 5 * 4 / 3 / 2 / 1")
  end

  it "should bracket addition within multiplication" do
    ten = { type: :+, positive: [ 9, 1 ], negative: [], value: 10 }
    input = { type: :*, positive: [ 3, ten ], negative: [], value: 30 }
    actual = Numbers::TreeToString.to_string input
    expect(actual).to eq("3 * (9 + 1)")
  end

  it "should not bracket multiplication within addition" do
    ten = { type: :*, positive: [ 5, 2 ], negative: [], value: 10 }
    input = { type: :+, positive: [ 3, ten ], negative: [], value: 13 }
    actual = Numbers::TreeToString.to_string input
    expect(actual).to eq("3 + 5 * 2")
  end

end
