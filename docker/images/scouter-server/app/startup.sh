#!/bin/bash

BASE_DIR=$( cd $( dirname $0 ) && pwd -P )

if [[ `uname -s` == "MINGW64_NT-"* ]]; then
  export JAVA_HOME='/c/develop/tools/java/corretto-1.8.0_382'
  export PATH="$JAVA_HOME/bin:$PATH"
  cd $BASE_DIR
fi

java -Xmx1024m -classpath $BASE_DIR:$BASE_DIR/scouter-server-boot.jar -Dscouter.config=conf/scouter-local.conf scouter.boot.Boot

