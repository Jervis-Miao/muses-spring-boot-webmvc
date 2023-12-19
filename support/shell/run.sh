#!/usr/bin/env bash
#################################################################################
# 启动脚本
#################################################################################

#项目名称
API_NAME=musesApplication

#JAR
JAR_HOME=/home/muses/source/musesApplication/target
JAR_NAME=$API_NAME\.jar


#设置日志文件
STDOUT_FILE=/home/INS/logs/startup/musesApplication.log

if [ -f $STDOUT_FILE ];then
   rm -rf $STDOUT_FILE
fi

#PID  代表是PID文件
PID=$API_NAME\.pid

#使用说明，用来提示输入参数
usage() {
	echo "请输入脚本参数：OPER"
    echo "    OPER: 执行的操作（必填）。可选值：start|stop|restart"
    echo "例：./run.sh start"
    exit 1
}

#检查程序是否在运行
is_exist(){
  pid=`ps -ef|grep $JAR_NAME|grep -v grep|awk '{print $2}' `
  #如果不存在返回1，存在返回0
  if [ -z "${pid}" ]; then
   return 1
  else
    return 0
  fi
}

#启动方法
start(){
  is_exist
  if [ $? -eq "0" ]; then
    echo ">>> ${JAR_NAME} is already running PID=${pid} <<<"
  else
    nohup java -Denv=DEV -Xms256m -Xmx512m -jar $JAR_HOME\/$JAR_NAME >$STDOUT_FILE 2>&1 &
    echo $! > $PID
    echo ">>> start $JAR_NAME successed PID=$! <<<"
   fi
}

#停止方法
stop(){
  #is_exist
  pidf=$(cat $PID)
  #echo "$pidf"
  echo ">>> api PID = $pidf begin kill $pidf <<<"
  kill $pidf
  rm -rf $PID
  sleep 2
  is_exist
  if [ $? -eq "0" ]; then
    echo ">>> api 2 PID = $pid begin kill -9 $pid  <<<"
    kill -9  $pid
    sleep 2
    echo ">>> $JAR_NAME process stopped <<<"
  else
    echo ">>> ${JAR_NAME} is not running <<<"
  fi
}

#输出运行状态
status(){
  is_exist
  if [ $? -eq "0" ]; then
    echo ">>> ${JAR_NAME} is running PID is ${pid} <<<"
  else
    echo ">>> ${JAR_NAME} is not running <<<"
  fi
}

#重启
restart(){
  stop
  start
}

######################################## MAIN ########################################
# 设置全局常量
OPER=$1

#根据输入参数，选择执行对应方法，不输入则执行使用说明
case $OPER in
  "start")
    start
    ;;
  "stop")
    stop
    ;;
  "status")
    status
    ;;
  "restart")
    restart
    ;;
  *)
    usage
    ;;
esac
exit 0
