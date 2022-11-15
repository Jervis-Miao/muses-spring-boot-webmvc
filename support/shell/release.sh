#!/usr/bin/env bash
function git_oper() {
    cd ${SOURCE_PATH}
    gitok=false
    isRemoteBranch=false
    if [  -d "./${repository}" ];then
        cd ./${repository}
        #(1)删除git状态零时文件
        if [ -f "gitstatus.tmp" ];then
            rm -rf gitstatus.tmp
        fi

        #(2) 判断是否存在.git目录
        if [ -d "./.git" ];then
            #(3) 判断git是否可用
            git status &> gitstatus.tmp
            grep -iwq 'not a git repository' gitstatus.tmp && gitok=false || gitok=true
        fi

        #返回到主目录
        cd ${SOURCE_PATH}
    fi

    if ${gitok};then
        cd ./${repository}
        git reset --hard
        git clean -ffdx
        git fetch
        echo "git fetch ${repository} remote repository 到本地成功"
    else
        #删除所有内容,便于重新进行git clone
        rm -rf ${repository}
        git clone --no-checkout ssh://git@git.muses.cn:10022/xyzdev/${repository}.git
        echo "git clone ${repository} remote repository 到本地成功"
        cd ./${repository}
    fi

    echo "检出 ${repository} ${version} 对应代码"
    gitRemoteBranch=`git branch -r`
    echo -e  "$gitRemoteBranch"|grep -iwq ${version} && isRemoteBranch=true || isRemoteBranch=false
    if ${isRemoteBranch};then
        echo "找到${version}对应的远端分支"
        git checkout -f 'origin/'${version}
    else
        git checkout -f ${version}
    fi
    #git pull
    #更新子模块代码
    git submodule update --init --recursive --force
    echo "代码检出完成！"
}

##############################__MAIN__########################################
export LANG="en_US.UTF-8"
repository="musesApplication"
version=`echo $1`
if [ "${version}" ==  "" ];then
    echo "用法：release.sh version"
    echo "version: git版本号:'master'|'feature/...'"
    exit 0
fi
SOURCE_PATH=/home/INS/source
SCRIPT_PATH=/home/INS/script
${SCRIPT_PATH}/${repository}/run.sh stop


if [ ! -d "${SOURCE_PATH}" ];then
    mkdir ${SOURCE_PATH}
fi
git_oper

echo "编译..."
mvn clean install -Dmaven.test.skip=true > /home/${repository}.log
echo "project has been built ... "
${SCRIPT_PATH}/${repository}/run.sh start
echo "now musesApplication has been started..."


