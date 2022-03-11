sudo apt update
sudo apt full-upgrade -y
sudo apt install snap openjdk-14-jre apache2 postgresql python3 python3-pip bleachbit hydra netdiscover nikto -y
sudo snap install --classic eclipse
sudo snap install --classic zaproxy
pip install sqlmap
sudo update-rc.d postgresql enable
sudo update-rc.d apache2 enable
