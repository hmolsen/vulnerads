#!/bin/bash

# https://askubuntu.com/questions/1168293/redirect-an-external-ip-to-localhost

sudo sysctl -w net.ipv4.conf.enp0s3.route_localnet=1 # to enable redirecting to localhost
EXTERNAL_IP=169.254.169.254 #change this line to reflect external ipaddress
sudo iptables -t nat -A OUTPUT -d ${EXTERNAL_IP} -j DNAT --to-destination 127.0.0.1
