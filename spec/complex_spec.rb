require 'numbers'

describe Numbers do

  def try(revpol)
    tree = Numbers::RevpolToTree.parse revpol
    transformed_tree = Numbers::TreeTransformer.transform tree.first
    optimised_tree = Numbers::TreeOptimiser.coalesce transformed_tree
    normalised_tree = Numbers::TreeNormaliser.normalise optimised_tree
    Numbers::TreeToString.to_string normalised_tree
  end

  it "should handle trivial cases" do
    expect(try('7')).to eq('7')
  end

  it "should handle simple cases" do
    expect(try('7 2 + 3 *')).to eq('3 * (7 + 2)')
  end

  it "should handle simple cases 2" do
    expect(try('7 2 * 3 +')).to eq('3 + 7 * 2')
  end

  it "should handle complex cases" do
    expect(try('50 75 25 / - 6 * 3 * 100 +')).to eq('100 + 6 * 3 * (50 - 75 / 25)')
  end

end
