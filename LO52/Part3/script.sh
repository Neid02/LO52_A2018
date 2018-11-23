#!/bin/sh
export ARCH=arm64
export CROSS_COMPILE=/home/florian/Documents/LO52/aarch64-linux-android-4.9/bin/aarch64-linux-android-
cd hikey-linaro
git checkout -b android-hikey-linaro-4.1 origin/android-hikey-linaro-4.1
make hikey_defconfig
make -j 8
