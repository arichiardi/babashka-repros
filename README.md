# Babashka Repros

A repository dedicated to reproducing [babashka](https://github.com/babashka/babashka) bugs.

## How to repro

```shell
clojure -T:build uber
bb -jar target/bb-repro.jar --main foo.main/-main
```

Uncomment `build.clj` line 36 in order to see the following error at runtime:

```bash
Clojure tools not yet in expected location: /home/app/.deps.clj/1.11.1.1435/ClojureTools/clojure-tools-1.11.1.1435.jarDownloading https://github.com/clojure/brew-install/releases/download/1.11.1.1435/clojure-tools.zip to /home/app/.deps.clj/1.11.1.1435/ClojureTools/clojure-tools.zip
Unzipping /home/app/.deps.clj/1.11.1.1435/ClojureTools/clojure-tools.zip ...
Successfully installed clojure tools!Exception in thread "main" java.io.IOException: Cannot run program "/usr/lib/jvm/java-11-openjdk-11.0.22.0.7-2.el8.x86_64/bin/java" (in directory "jar:file:/app/upgrade-scripts/cardiodi-scripting.jar!/META-INF"): error=2, No such file or directory
	at java.base@21.0.1/java.lang.ProcessBuilder.start(ProcessBuilder.java:1170)
	at java.base@21.0.1/java.lang.ProcessBuilder.start(ProcessBuilder.java:1089)
	at babashka.process$process_STAR_.invokeStatic(process.cljc:367)
	at babashka.process$shell.invokeStatic(process.cljc:648)
	at babashka.impl.deps$add_deps$fn__27336.invoke(deps.clj:100)
	at borkdude.deps$_main.invokeStatic(deps.clj:1052)
	at borkdude.deps$_main.doInvoke(deps.clj:882)
```
