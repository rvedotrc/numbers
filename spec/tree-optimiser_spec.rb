require 'numbers'

describe Numbers::TreeOptimiser do

  it "should coalesce addition" do
    input = [
      :+,
      [ :+, 1, 4 ],
      [ :+, 7, 8 ],
    ]
    transformed = Numbers::TreeTransformer.transform(input)
    actual = Numbers::TreeOptimiser.coalesce(transformed)
    expect(actual).to eq(
      type: :+,
      positive: [ 1, 4, 7, 8 ],
      negative: [],
      value: 20,
    )
  end

  it "should coalesce subtraction" do
    input = [
      :-,
      [ :-, 8, 2 ],
      [ :-, 4, 1 ],
    ]
    transformed = Numbers::TreeTransformer.transform(input)
    actual = Numbers::TreeOptimiser.coalesce(transformed)
    expect(actual).to eq(
      type: :+,
      positive: [ 8, 1 ],
      negative: [ 4, 2 ],
      value: 3,
    )
  end

  it "should coalesce multiplication" do
    input = [
      :*,
      [ :*, 2, 4 ],
      [ :*, 7, 8 ],
    ]
    transformed = Numbers::TreeTransformer.transform(input)
    actual = Numbers::TreeOptimiser.coalesce(transformed)
    expect(actual).to eq(
      type: :*,
      positive: [ 2, 4, 7, 8 ],
      negative: [],
      value: 2*4*7*8,
    )
  end

  it "should coalesce division" do
    input = [
      :/,
      [ :/, 36, 3 ],
      [ :/, 6, 2 ],
    ]
    transformed = Numbers::TreeTransformer.transform(input)
    actual = Numbers::TreeOptimiser.coalesce(transformed)
    expect(actual).to eq(
      type: :*,
      positive: [ 36, 2 ],
      negative: [ 6, 3 ],
      value: 4,
    )
  end

  it "should not coalesce + and - with * & /" do
    # (1 + (((2*5)/1) - 3)) * 4
    input = [
      :*,
      [
        :+,
        1,
        [
          :-,
          [
            :/,
            [ :*, 2, 5 ],
            1,
          ],
          3,
        ],
      ],
      4,
    ]
    transformed = Numbers::TreeTransformer.transform(input)
    actual = Numbers::TreeOptimiser.coalesce(transformed)
    expect(actual).to eq(
      type: :*,
      positive: [
        {
          type: :+,
          positive: [ 1, {type: :*, positive: [2,5], negative: [1], value: 10} ],
          negative: [3],
          value: 8,
        },
        4,
      ],
      negative: [],
      value: 32,
    )
  end

end
