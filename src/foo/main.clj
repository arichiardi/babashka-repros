(ns  foo.main
  (:require
   [foo.aliased :as-alias al]))

(defn -main
  [& _]
  (prn #::al{:bar "a bar"}))
