## Restauraunt
### A small test project representing the restaurant API.
#### Data base schema
![Alt text](./DBSchema.png?raw=true "Data base schema")
#### Build
*Java version 8 or higher is required to build the application*

To build execute

    >mvnw clean install

#### Run
This application is launched using docker compose

For windows use docker desktop or follow docker installation guide

1. Set your database name and pass in ```docker-compose.yml``` instead __{ user }__ and __{ password }__

2. For start application execute

        >docker-compose up
    
3. For stop application execute

        >docker-compose down

    * For stop application and remove containers execute

            >docker-compose down --rmi all

4. After start you can use 
[http://localhost:8080/swagger-ui/index.html#/](http://localhost:8080/swagger-ui/index.html#/)
to open swagger api

#### Docker installation guide

First you need to [Install WSL2](https://docs.microsoft.com/en-us/windows/wsl/install) and
the [Linux kernel update package](https://docs.microsoft.com/windows/wsl/wsl2-kernel).

Then open Command Prompt or PowerShell and execute the following commands:

    >wsl --update
    >wsl --set-default-version 2
    >wsl --install --distribution Ubuntu
    

Open the Ubuntu application (can be found in the installed applications list) and execute the following commands:

    >sudo apt --yes update
    >sudo apt --yes upgrade
    >sudo apt --yes autoclean
    >sudo apt --yes autoremove
    >sudo apt --yes install docker docker-compose

    >sudo usermod -aG docker $USER
    >echo "$USER ALL=(ALL) NOPASSWD: /usr/bin/dockerd" > /tmp/docker-sudoers
    >sudo bash -c 'cat /tmp/docker-sudoers >> /etc/sudoers'
    >rm /tmp/docker-sudoers
    >sudo bash -c 'echo "{ \"hosts\": [\"unix:///var/run/docker.sock\", \"tcp://0.0.0.0:2375\"] }" > /etc/docker/daemon.json'

To start Docker Daemon execute the following command:

    >sudo dockerd --dns 8.8.8.8

This command should be executed once you open Ubuntu for the first time.

Next you need to configure WSL2 Docker on the Windows side. Create ``bin`` directory in your home directory
and create the following files there:

```docker.bat``` with the following content

    @echo off
    wsl docker %*

```docker-compose.bat``` with the following content

    @echo off
    wsl docker-compose %*

Add this ``bin`` directory to the ``PATH`` environment variable. This can be done via the
'System Properties' dialog (Start -> type 'Environment variables' -> select the option displayed -> click the
'Environment Variables...' button in the dialog)

Open Command Prompt or PowerShell and check the following:

    >docker --version
    Docker version 20.10.7, build 20.10.7-0ubuntu5~20.04.2

    >docker-compose --version
    docker-compose version 1.25.0, build unknown

Sometimes host machine (Windows) connection to services running in WSL (dockerd itself, components in docker) may be lost. 
In this case we need to restart WLS:

    wsl --shutdown

And then run Ubuntu and start Docker Daemon again.

