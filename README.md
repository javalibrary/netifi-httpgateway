# Start Consul 1
consul agent -dev -advertise 127.0.0.1

# Start Consul 2
sudo ifconfig lo0 alias 127.0.0.2 up
consul agent  -http-port=18500  \
-server-port=18300 \
-serf-lan-port=18301 \
-serf-wan-port=18302 \
-dns-port=18600 \
-grpc-port=18502 \
-dev -advertise 127.0.0.2 \
-client 127.0.0.2 \
-bind 127.0.0.2 \
-serf-wan-bind 127.0.0.2 \
-serf-lan-bind 127.0.0.2