# EShop demo application

## Source

This project is a try to learn Manning live project about distributed tracing <https://liveproject.manning.com/>

## How to build source code?

```bash
mvn install
```

## How to run this demo?


```bash
docker-compose build
docker-compose up -d
```


## Test the demo

Use curl command or open url ```localhost/checkout``` in the browser. You should be able to see output as the followings.

```bash
➜  milestone1 git:(master) ✗ curl 127.0.0.1/checkout
You have successfully checked out your shopping cart.
```

## Check the logs

```bash
docker-compose logs -f
```

## Check the generated trace and span at the Jaeger UI

Open URL ```http://localhost:16686/``` in the browser. You should be able to see the generated traces in the Jaeger UI.

![](screenshot/trace.png)

Like this diagram shows, the trace seems pretty simple, with only one span in it. However, it already gives us some useful information such as
how long does the checkout operation took. In the next project, we are going to expand the trace to multiple spans.
