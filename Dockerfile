FROM ubuntu
MAINTAINER Rachel Evans <github.com/rvedotrc>

RUN apt-get update
RUN apt-get install -y ruby2.0 ruby2.0-dev bundler build-essential
COPY ./ /root/numbers
WORKDIR /root/numbers
RUN bundle install
RUN rm -f ./numbers-fast && make
EXPOSE 4567
CMD bundle exec ruby -Ilib ./bin/numbers-server
