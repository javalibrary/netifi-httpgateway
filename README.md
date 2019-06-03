# Start Consul 1
consul agent -dev -advertise 127.0.0.1

# Start Consul 2
consul agent  -http-port=18500 -server-port=18300 -serf-lan-port=18301 -serf-wan-port=18302 -dns-port=18600 -grpc-port=18502 -dev -advertise 127.0.0.1 
