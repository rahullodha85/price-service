#!/bin/bash
HOST=$(hostname -i)
PORT=9480

echo "::. STARTING  - Consul-Template! .::"
echo "::. template [price-service] .::"
consul-template -consul-addr $CONSUL_HOST -template "/opt/price-service-0.1/conf/price-application.ctmpl:/opt/price-service-0.1/conf/price-application.conf" -once;
sleep 0.5
echo "::. template [newrelic] .::"
consul-template -consul-addr $CONSUL_HOST -template "/opt/newrelic/newrelic.ctmpl:/opt/newrelic/newrelic.yml" -once
sleep 0.5
echo "::. COMPLETED - Consul-Template! .::"