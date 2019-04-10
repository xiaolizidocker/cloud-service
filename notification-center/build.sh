#!/bin/sh
echo "开始构建镜像"
echo "**************************************"
cd /root/.jenkins/workspace/notification-center/
pwd

SERVER_NAME=notification-center

#容器id
CID=$(docker ps | grep "$SERVER_NAME" | awk '{print $1}')

#镜像id
IID=$(docker images | grep "$SERVER_NAME" | awk '{print $3}')

#仓库地址
RESPOSITORY=192.168.0.10:5000/

#构建镜像
function build(){
	if [ -n "$IID" ]; then
		echo "存在$SERVER_NAME镜像，IID=$IID"
	else
		echo "不存在$SERVER_NAME镜像，开始构建镜像"
	fi

	docker build -t $SERVER_NAME .
        IID=$(docker images | grep "$SERVER_NAME" | awk '{print $3}')
        echo "新的镜像IID为$IID"
        docker tag "$IID"  "${RESPOSITORY}${SERVER_NAME}"
        docker push ${RESPOSITORY}${SERVER_NAME}
}


function stopcontainer(){
	if [ -n "$CID" ]; then
		echo "存在$SERVER_NAME容器，CID=$CID"
                docker stop $CID
                docker rm ${SERVER_NAME}
	else
		echo "不存在$SERVER_NAME容器，开始创建容器"
	fi
}
#停止容器，删除容器
function runcontainer(){
	docker run --name=$SERVER_NAME -p 9002:9002 -d $SERVER_NAME
	CID=$(docker ps | grep "$SERVER_NAME" | awk '{print $1}')
	echo "镜像已经运行起来了，容器CID:$CID"
}

stopcontainer

build

runcontainer