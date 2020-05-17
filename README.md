[![Build Status](https://travis-ci.org/waltz-controls/user-context.svg?branch=master)](https://travis-ci.org/waltz-controls/user-context)
[![release](https://img.shields.io/github/release/tango-controls-webapp/tango-webapp-user-context.svg?style=flat)](https://github.com/tango-controls-webapp/tango-webapp-user-context/releases/latest)

# Waltz UserContext external storage

This project provides microservice to load/store UserContext instances from [Waltz](https://github.com/waltz-controls/waltz)

This microservice has very simple API:

1. `GET user-context/cache?id=<userName>` -- returns serialized in base64, gzipped UserContext instance or null
2. `POST user-context/cache?id=<userName>&data=<base64 UserContext>` -- stores data
3. `POST user-context/cache?id=<userName>` -- deletes data

# Waltz plugin

Refer to [waltz-user-context-plugin](https://github.com/waltz-controls/waltz-user-context-plugin) for js API
