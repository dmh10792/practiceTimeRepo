# What PEs Need to Know About SE

## Part 1

### Set Up

Run the build.

```
./lofi-app/build.bash
```

Set your kube context to `docker-desktop`.

```
kubectx docker-desktop
```

Apply the namespace.

```
kubectl apply -f infra/namespace.yaml
```

Apply `manifest-1.yaml`.

```
kubectl apply -f infra/manifest-1.yaml
```

### Troubleshoot

Get the app to run.

- Time box - 5 minutes.
- Don't look at app source. Everything in the `lofi-app` directory is off limits!

### Clean Up

Delete `manifest-1.yaml`.

```
kubectl delete -f infra/manifest-1.yaml
```
