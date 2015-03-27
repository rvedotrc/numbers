require 'rest-client'

class NumbersServer
  include Singleton

  def self.get(url_suffix)
    url = "#{base_url}/#{url_suffix}"
    RestClient.get url
  end

  def self.base_url
    @base_url ||= get_base_url
  end

  def self.get_base_url
    ip = URI.parse(ENV["DOCKER_HOST"]).host

    container_id = IO.read("var/container_id")
    container = JSON.parse(`docker inspect #{container_id}`)
    port = container[0]["NetworkSettings"]["Ports"]["4567/tcp"][0]["HostPort"]

    "http://#{ip}:#{port}/numbers"
  end

end
