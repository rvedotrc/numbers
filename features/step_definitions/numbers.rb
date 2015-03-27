Given(/^numbers "(.*?)"$/) do |numbers|
  @numbers = numbers.split.map &:to_i
end

Given(/^target "(.*?)"$/) do |target|
  @target = target.to_i
end

Given(/^verbose mode$/) do
  @verbose = true
end

When(/^I run the solver$/) do
  url = "target/#{@target}/numbers/#{@numbers.join "+"}"
  url += "/verbose" if @verbose
  content = NumbersServer.get url
  @data = JSON.parse content
end

Then(/^the solutions are presented closest\-first$/) do
  distances = @data.map {|s| (@target - s["result"]).abs}
  expect(distances).to eq(distances.sort)
  expect(distances.first).not_to eq(distances.last), "Test invalid - need results of varying distance"
end

Then(/^the solutions are (?<not>not )?verbose$/) do |not_verbose|
  if not_verbose
    expect(@data[0]).not_to include("tree1")
    expect(@data[0]).not_to include("tree2")
    expect(@data[0]).not_to include("tree3")
    expect(@data[0]).not_to include("tree4")
  else
    expect(@data[0]).to include("tree1")
    expect(@data[0]).to include("tree2")
    expect(@data[0]).to include("tree3")
    expect(@data[0]).to include("tree4")
  end
end

Then(/^the solver should reject the request$/) do
  url = "target/#{@target}/numbers/#{@numbers.join "+"}"
  url += "/verbose" if @verbose
  expect {
    NumbersServer.get url
  }.to raise_error(RestClient::ResourceNotFound)
end

Then(/^the solver should accept the request$/) do
  url = "target/#{@target}/numbers/#{@numbers.join "+"}"
  url += "/verbose" if @verbose
  NumbersServer.get url
end

