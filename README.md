# Short Id

[![Build Status](https://travis-ci.org/alexanderkiel/shortid.svg?branch=master)](https://travis-ci.org/alexanderkiel/shortid)

Generates short random base62 encoded identifiers.

## Install

To install, just add the following to your project dependencies:

```clojure
[org.clojars.akiel/shortid "0.1"]
```

## Usage

```clojure
(require 'shortid.core)

(shortid.core/generate 5)
;; => "iCK15"
```

## License

Copyright © 2015 Alexander Kiel

Distributed under the Eclipse Public License, the same as Clojure.
