(ns foo.aliased
  (:require [clojure.spec.alpha :as s]))

(s/def ::bar string?)

(s/def ::map
  (s/keys :req [::bar]))
