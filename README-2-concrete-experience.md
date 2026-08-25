# What PEs Need to Know About SE

## Part 2

### Set up.

Apply `manifest-2.yaml`.

```
kubectl apply -f infra/manifest-2.yaml
```

### Troubleshoot

Get the app to run.

- Time box - 5 minutes.
- Don't look at app source. Everything in the `lofi-app` directory is off limits!

### Clean up.

```
kubectl delete -f infra/manifest-2.yaml
kubectl delete -f infra/namespace.yaml
```
