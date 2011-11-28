#!/usr/bin/env rackup
#\ -E deployment

use Rack::ContentLength

app = Rack::Directory.new Dir.pwd
run app
