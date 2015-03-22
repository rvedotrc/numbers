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

end
