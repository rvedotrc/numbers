require 'numbers'

describe Numbers::TreeTransformer do

  it "should transform a number" do
    input = 7
    actual = Numbers::TreeTransformer.transform(input)
    expect(actual).to eq(7)
  end

  it "should transform addition" do
    input = [ :+, 12, 3 ]
    actual = Numbers::TreeTransformer.transform(input)
    expect(actual).to eq(type: :+, positive: [12,3], negative: [], value: 15)
  end

  it "should transform subtraction" do
    input = [ :-, 12, 3 ]
    actual = Numbers::TreeTransformer.transform(input)
    expect(actual).to eq(type: :+, positive: [12], negative: [3], value: 9)
  end

  it "should transform multiplication" do
    input = [ :*, 12, 3 ]
    actual = Numbers::TreeTransformer.transform(input)
    expect(actual).to eq(type: :*, positive: [12,3], negative: [], value: 36)
  end

  it "should transform division" do
    input = [ :/, 12, 3 ]
    actual = Numbers::TreeTransformer.transform(input)
    expect(actual).to eq(type: :*, positive: [12], negative: [3], value: 4)
  end

  it "should transform complex" do
    input = [ :/, [
                :*,
                [:+, 10, 2],
                [:-, 7, 3],
              ], 6 ]
    actual = Numbers::TreeTransformer.transform(input)
    expect(actual).to eq(
      type: :*,
      positive: [
        {
          type: :*,
          positive: [
            {type: :+, positive: [10,2], negative: [], value: 12},
            {type: :+, positive: [7], negative: [3], value: 4},
          ],
          negative: [],
          value: 48,
        }
      ],
      negative: [6],
      value: 8,
    )
  end

end
