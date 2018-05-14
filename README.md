[![Build Status](https://travis-ci.org/tango-controls-webapp/tango-webapp-user-context.svg?branch=master)](https://travis-ci.org/tango-controls-webapp/tango-webapp-user-context) 
[![release](https://img.shields.io/github/release/tango-controls-webapp/tango-webapp-user-context.svg?style=flat)](https://github.com/tango-controls-webapp/tango-webapp-user-context/releases/latest)
[![License](https://img.shields.io/badge/license-LGPL--3.0-blue.svg)](https://github.com/tango-controls-webapp/tango-webapp-user-context/blob/master/LICENSE)

# TangoWebapp UserContext external storage

This project provides microservice to load/store UserContext instances from [TangoWebapp](https://github.com/tango-controls/tango-webapp)

This microservice has very simple API:

1. `GET user-context/cache?id=<userName>` -- returns serialized in base64, gzipped UserContext instnace or null
2. `POST user-context/cache?id=<userName>&data=<base64 UserContext>` -- stores data
3. `POST user-context/cache?id=<userName>` -- deletes data
