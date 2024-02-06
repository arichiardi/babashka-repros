(ns build
  (:require
   [clojure.edn :as edn]
   [clojure.java.io :as io]
   [clojure.tools.build.api :as b]))

(def target-dir "target")
(def class-dir (io/file target-dir "classes"))
(def meta-inf-dir (io/file class-dir "META-INF"))
(def bb-edn-meta-path (io/file meta-inf-dir "bb.edn"))

(def lib-name 'bb/repro)

(def uberjar-name "bb-repro.jar")
(def uberjar-file (io/file target-dir uberjar-name))

(def basis (delay (b/create-basis {:project "deps.edn"})))

(def copy-srcs ["src"])
(def src-dirs copy-srcs)

(defn clean [_]
  (b/delete {:path (.getAbsolutePath bb-edn-meta-path)})
  (b/delete {:path (.getAbsolutePath uberjar-file)}))

(defn spit-meta-inf-bb-edn!
  "Having bb.edn at META-INF/bb.edn will make sure we download pods at runtime.

  Employing the following trick for making the uberjar pod-friedly
    https://github.com/babashka/babashka/wiki/Self-contained-executable#tasks"
  []
  (let [bb-edn (-> (io/file "bb.edn")
                   slurp
                   edn/read-string
                   ;; Uncomment me to see it working
                   ;; (dissoc :deps)
                   )]
    (-> (.getAbsolutePath bb-edn-meta-path)
        (spit bb-edn))))

(defn uber
  [_]
  (let [basis @basis
        class-dir (.getAbsolutePath class-dir)
        uber-file (.getAbsolutePath uberjar-file)]
    (clean nil)
    (spit-meta-inf-bb-edn!)
    (b/copy-dir {:src-dirs copy-srcs :target-dir class-dir})
    ;; Disabling compilation as it triggers the following org.babashka/spec.alpha error:
    ;;
    ;; Caused by: java.lang.Exception: #object[clojure.spec.alpha$and_spec_impl$reify__1061
    ;; 0x508a65bf "clojure.spec.alpha$and_spec_impl$reify__1061@508a65bf"] is not a fn, expected
    ;; predicate fn
    ;;
    ;; (b/compile-clj {:basis basis
    ;;                 :class-dir class-dir
    ;;                 :src-dirs src-dirs})
    (b/write-pom {:class-dir class-dir
                  :lib lib-name
                  :version "0.0.0"
                  :basis basis
                  :src-dirs src-dirs})
    (b/uber {:class-dir class-dir :uber-file uber-file :basis basis})))
