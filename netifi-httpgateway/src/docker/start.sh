#!/usr/bin/env bash
mkdir -p /opt/netifi/netifi-httpgateway-tmp
exec /opt/netifi/netifi-httpgateway-docker/bin/netifi-httpgateway-docker -fg
