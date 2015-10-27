#!/bin/sh
#created by czw
#关闭脚本目前为了方便直接kill进程，后续还要修改

current_path=`pwd`

shell_path=$(cd `dirname $0`;pwd)
cd ${current_path}

DIR=`dirname ${shell_path}`

kill -9 `ps axu|grep java|grep ${DIR}|awk '{print $2}'`

