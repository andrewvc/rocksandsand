# rocksandsand

A website written in noir. 

## Usage

You must have a recent copy of redis (with MULTI) running. Disable connection timeouts.

```bash
cp resources/config.json.sample resources/config.json
$EDITOR resources/config.json
lein deps
lein run
```

## License

Copyright (C) 2011 Andrew Cholakian
Distributed under the Eclipse Public License, the same as Clojure.
