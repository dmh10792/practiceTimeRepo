## docker run

Run container without compose. Notice that instead of `localhost`, this uses `host.docker.internal` to escape container isolation to access services on the desktop. This is only necessary if you're not taking advantage of the built in networking that `docker compose` offers.

```shell
docker run \
  --name ntk \
  --rm \
  -p 127.0.0.1:8080:8080/tcp \
  --env SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/experiment-db \
  --env SPRING_DATASOURCE_USERNAME=experiment \
  --env SPRING_DATASOURCE_PASSWORD=experiment \
  need-to-know:latest
```
