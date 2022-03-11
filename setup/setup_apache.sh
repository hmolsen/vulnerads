sudo cp vulnerads.conf /etc/apache2/sites-available/
sudo a2enmod ssl proxy proxy_http rewrite
sh generate-wildcard-certificate.sh cookielogger.local
sh generate-wildcard-certificate.sh mycoolnewblog.local
sh generate-wildcard-certificate.sh cors-test.local
sh generate-wildcard-certificate.sh vulnerads.local

