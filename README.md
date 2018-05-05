# tictactoe

This is the tictactoe project. A running example can be found at
http://tictactoe.jcodev.eu.

## Development Mode

To start the Figwheel compiler, navigate to the project folder and run the
following command in the terminal:
```
lein figwheel
```
Figwheel will automatically push cljs changes to the browser. The server will be
available at [http://localhost:3449](http://localhost:3449) once Figwheel starts
up.

Figwheel also starts `nREPL` using the value of the `:nrepl-port` in the
`:figwheel` config found in `project.clj`. By default the port is set to `7002`.

The figwheel server can have unexpected behaviors in some situations such as
when using websockets. In this case it's recommended to run a standalone
instance of a web server as follows:
```
lein do clean, run
```

The application will now be available at
[http://localhost:3000](http://localhost:3000).

### Style Compilation
To compile [sass](https://github.com/tuhlmann/lein-sass) sources and then watch
for changes and recompile until interrupted, run:
```
lein sass auto
```

### Optional Development Tools

Start the browser REPL:

```
$ lein repl
```
The Jetty server can be started by running:

```clojure
(start-server)
```
and stopped by running:
```clojure
(stop-server)
```

## Building for Release

```
lein do clean, uberjar
```
