#!/bin/sh

openssl pkcs12 -export -in ../sosec-local.de.crt -inkey ../sosec-local.de.key -out ../sosec-local.de.p12 -name sosec -CAfile ../sosec-local.de.chain -chain -passout pass:sosec
