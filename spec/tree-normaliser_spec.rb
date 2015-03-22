require 'numbers'

describe Numbers::TreeNormaliser do

  it "should normalise a number" do
    input = 3
    actual = Numbers::TreeNormaliser.normalise(input)
    expect(actual).to eq(3)
  end

  it "should normalise numeric operands by descending value" do
    input = { type: :+, positive: [17, 100], negative: [2,10], value: 105 }
    actual = Numbers::TreeNormaliser.normalise(input)
    expect(actual).to eq(type: :+, positive: [100,17], negative: [10,2], value: 105)
  end

  it "should normalise numbers before operators" do
    seventeen = { type: :+, positive: [20], negative: [3], value: 17 }
    twelve = { type: :+, positive: [20], negative: [8], value: 12 }
    input = { type: :+, positive: [5, seventeen, 100], negative: [2, twelve, 10], value: 98 }
    actual = Numbers::TreeNormaliser.normalise(input)
    expect(actual).to eq(type: :+, positive: [100,5,seventeen], negative: [10,2,twelve], value: 98)
  end

end
