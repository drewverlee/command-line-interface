(ns command-line-interface.core
  (:require [clojure.repl :as r]
            [com.gfredericks.like-format-but-with-named-args :refer [named-format]]
            [datascript.core :as d]
            [clojure.string :as str]
            [clojure.set :as set]))




(set/intersection #{1 2 3} #{3 4 5})


(defn foo
  "x | is a thing"
  [x]
  x)

(defn doo
  [x y]
  (+ x y))

(apply doo [1 2])

;; add support for def, macros, etc..
(def zar 1)

(meta (var zar))

(defn bar
  "hi"
  [{:keys [x y]} & g] x)

(defn car
  "a    | hot
     b    | cold
     call them together to get the mix"
  ([a] a)
  ([a b] (+ a b)))

(:doc (meta (var car)))

;; just grap first line...
;; goal to make them readable at cli based off common usage in clojure
;; TODO make this an option
;; TODO one for read function alone vs group

;; this is group
;; grap first line
;; remove spaces
;; cut off after 50 chars
(defn str-handler
  [s]
  (str (take 40 s) "..."))

(subvec)

(str-handler "i really love the tv show the moose jaw")


(str-handler "this\n is a very good function it does so         much     ")

(str/trim "this\n is a very good function it does so much     ")


;; we need some place to store them

(def schema {})

(d/empty-db schema)

(def conn (d/create-conn schema))

;; TODO change type to something else CLI should be for the 'homescreen' functions
(defn store
  [v]
  (assoc (meta v) :var v :type :cli))

(d/transact! conn [(store (var foo)) (store (var bar))])


;; then query them out
;; this would be query all



(def get-all-default
  (d/q '[:find [(pull ?e [:name :doc :arglists]) ...]
         :where [?e :type :cli]]
       @conn))

;; print it to console
(clojure.pprint/print-table get-all)

(d/q '[:find [(pull ?e [:name :doc :arglists :var]) ...]
       :where [?e :type :cli]]
     @conn)


;; way to get fn

(defn get-fn
  [fn]
  (d/q '[:find ?v .
         :in $ ?fn
         :where
         [?e :name ?fn]
         [?e :var ?v]]
       @conn (symbol fn)))

;; consider saving as a string?



;; tell them to change there main function to be something

(let ['([x])])

(cli "foo" 1)

(get-fn ["foo" 1])

;; normal
(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  args)

(-main)

(defn zoup
  ([& args] args)
  ([] "hi"))

(defn cli-main
  ([]) ;; print help screen
  ([fn & args]
   ((get-fn fn) args)))

(defn get-all-cli-top-fns
  [{:name}])


(def default-help
  {:intro "Welcome"
   :fns get-all-default})

(def console-print-default
  (do
   (println (:intro default-help))
   (clojure.pprint/print-table (:fns default-help))))

(defn default-help-console)

(-main "hi" "\there" "you")

(read-string "\"here\"")

;; default behavior is symbols...
;; or maybe not

(clojure.pprint/print-table [{:a "hi" :b "low"} {:a "love" :b "car"}])

;; now to create a console display




;; so you type
["help", "query", "--help", "--query", ""] ;; or nothing

;; invalid gets ->
;; sorry your input ["there" "input"] wasn't valid. Please try ...

;; and you get


;; INTRO DOC if one exists
;; ------
;; query grammer
;; explain its a short cut for datomic
;; function to call datomic is datomic-query or dq
;; query or q just takes
;;     :name -> all functions by name TODO consider calling function or fn-name or something
;;     :name name -> return for that function

;;     TODO tag is a 2.0 feature
;;     :tag -> all tags
;;     :tag tag -> all functions for that tag
;;
;; tag
;; -> sort, console, web, 
;; 
;; ------
;; function call grammer
;; fn args?
;; & means any number
;;
;; --------

;; Default intro
"Welcome. Here are a list of functions you can call to learn more about how this program works"
;; query ([] [key fn])
;; query :doc fn

;; TODO have short alias for query q

(str/join "\n"
          ["collection types"
           "[] = vector"
           "{} = map {k1 v1 ...}"
           "() = list"])



;; fn
;; query | query things
;; query :name fn-name | query by function name

;; help | 
;; query


;; help (top level)
;; help :short or :long with default short
;; short is just name doc argslist long is everything
;; list all top level functions

;; list
;; alias for search()

;; search
;; () -> all functions
;; (*) -> some sort of smarter search
;; quick text search

;; help(fn) -> return just info for that function

;; output-sytle
;; default -> what ever i think is best

;; output-format
;; text
;; html


;; pass help








