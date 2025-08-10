## **로컬 실행 방법 (Quickstart)**

> 사전 요구:
>
> **Docker & Docker Compose**

1. **공유 네트워크 생성**

```
docker network create msa-net || true
```

1. **메시징 스택 기동 (kafka/)**

```
docker compose -f ./kafka/docker-compose.yml up -d
```

1. **도메인 서비스 기동 (각 폴더)**

```
docker compose -f ./msa-order/docker-compose.yml up -d --build
docker compose -f ./msa-product/docker-compose.yml up -d --build
docker compose -f ./msa-payment/docker-compose.yml up -d --build
```

1. **모니터링 스택 기동 (prometheus/)**

```
docker compose -f ./prometheus/docker-compose.yml up -d
```

1. **헬스 체크**

- Order: http://localhost:8083/actuator/health
- Product: http://localhost:8082/actuator/health
- Payment: http://localhost:8084/actuator/health
- Prometheus: http://localhost:9090
- Grafana: http://localhost:3000
