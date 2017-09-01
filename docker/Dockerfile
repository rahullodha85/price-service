FROM sd1pgo01lx.saksdirect.com/os-alpine:latest

RUN  apk update \
  && apk upgrade \
  && apk add --no-cache --update openjdk8-jre-base \
  && bash -c "curl -s 'https://download.newrelic.com/newrelic/java-agent/newrelic-agent/3.37.0/newrelic-java-3.37.0.zip' -o /opt/newrelic.zip; unzip /opt/newrelic.zip -d /opt; rm -f {/opt/newrelic.zip,/opt/newrelic/newrelic.yml}" \
  && rm -rf /var/cache/apk/*

COPY ["./price-service-0.1.zip", "/opt/price-service-0.1.zip"]
COPY ["./containerpilot/", "/opt/containerpilot"]
COPY ["./newrelic.yml", "./newrelic.ctmpl", "/opt/newrelic/"]


RUN unzip /opt/price-service-0.1.zip -d /opt \
  && chmod 755 /opt/price-service-0.1/bin/price-service \
  && chmod 775 /opt/containerpilot/*

# Play port to expose
EXPOSE 9480

CMD nohup /opt/price-service-0.1/bin/price-service -Dhttp.port=9480 -Dpidfile.path=/dev/null -Dconfig.file=${CONFIG_FILE} -Dlogger.file=/opt/price-service-0.1/conf/docker-logger.xml
