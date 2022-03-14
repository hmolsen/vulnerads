sudo apt update
sudo apt full-upgrade -y
sudo apt install snap openjdk-14-jre apache2 postgresql python3 python3-pip bleachbit netdiscover nikto -y
sudo snap install --classic eclipse
sudo snap install --classic zaproxy
pip install sqlmap
sudo update-rc.d postgresql enable
sudo update-rc.d apache2 enable

# install hydra with ssl
cd ~
sudo apt-get install libssl-dev libssh-dev 
git clone https://github.com/vanhauser-thc/thc-hydra
cd thc-hydra
./configure
make
sudo make install
cd ~
rm -rf thc-hydra
