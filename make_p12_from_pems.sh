#!/bin/sh

openssl pkcs12 -export -in ../attacat.de.crt -inkey ../attacat.de.key -out ../attacat-8666/conf/attacat.de.p12 -name sosec -CAfile ../attacat.de.chain -chain -passout pass:sosec
openssl pkcs12 -export -in ../vulnerads.de.crt -inkey ../vulnerads.de.key -out ./src/main/resources/vulnerads.de.p12 -name sosec -CAfile ../vulnerads.de.chain -chain -passout pass:sosec
