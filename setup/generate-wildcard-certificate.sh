#!/usr/bin/env bash

# Add wildcard
DOMAIN=$1

# Set our CSR variables
SUBJ="
C=DE
ST=NRW
O=Viadee Unternehmensberatung AG
localityName=Local Developement
commonName=$DOMAIN
organizationalUnitName=Local Developement
emailAddress=
"

# Generate our Private Key, CSR and Certificate
openssl genrsa -out "$DOMAIN.key" 3072
openssl req -new -subj "$(echo -n "$SUBJ" | tr "\n" "/")" -key "$DOMAIN.key" -out "$DOMAIN.csr"
openssl x509 -req -days 3650 -in "$DOMAIN.csr" -signkey "$DOMAIN.key" -out "$DOMAIN.crt"
rm "$DOMAIN.csr"

echo ""
echo "Next manual steps:"
echo "- Use $DOMAIN.crt and $DOMAIN.key to configure Apache/nginx"
echo "- Import $DOMAIN.crt into Chrome settings: chrome://settings/certificates > tab 'Authorities'"
