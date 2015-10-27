#!/bin/sh
#created by czw

shell_path=$(cd `dirname $0`;pwd)

DIR=`dirname ${shell_path}`

echo ${DIR}
CLASSPATH=${DIR}/conf/:${DIR}/conf/*:${DIR}/lib/:${DIR}/lib/*

#-Xms2048m -Xmx2048m -Xmn384m
java_server_mem="-Xms2048m -Xmx2048m -Xmn384m"

java -server ${java_server_mem} -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+UseCMSCompactAtFullCollection -XX:CMSFullGCsBeforeCompaction=0 -XX:-CMSParallelRemarkEnabled -XX:CMSInitiatingOccupancyFraction=70 -classpath ${CLASSPATH} -Dfile.encoding=utf-8 com.twocater.diamond.core.bootstrap.StartupMain &
