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

  it "should compare operators by their operands" do
    ten_a = { type: :+, positive: [9, 1], negative: [], value: 10 }
    ten_b = { type: :+, positive: [8, 2], negative: [], value: 10 }
    input = { type: :*, positive: [ten_a, ten_b, ten_a], negative: [ten_b, ten_a, ten_b], value: 1 }
    actual = Numbers::TreeNormaliser.normalise(input)
    expect(actual).to eq(type: :*, positive: [ten_a, ten_a, ten_b], negative: [ten_a, ten_b, ten_b], value: 1)
  end

  it "should normalise recursively" do
    ten_a = { type: :+, positive: [4,6], negative: [], value: 10 }
    ten_b = { type: :+, positive: [3,7], negative: [], value: 10 }
    input = { type: :*, positive: [ten_b], negative: [ten_a], value: 1 }
    actual = Numbers::TreeNormaliser.normalise(input)
    opt_ten_a = { type: :+, positive: [6,4], negative: [], value: 10 }
    opt_ten_b = { type: :+, positive: [7,3], negative: [], value: 10 }
    expect(actual).to eq(type: :*, positive: [opt_ten_b], negative: [opt_ten_a], value: 1)
  end

end
