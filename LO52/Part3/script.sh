#!/bin/sh
git clone https://android.googlesource.com/kernel/hikey-linaro
cd hikey-linaro
git log --max-count=1 kernel

export ARCH=arm64
export CROSS_COMPILE=aarch64-linux-android-
cd hikey-linaro
git checkout -b android-hikey-linaro-4.1 origin/android-hikey-linaro-4.1
make hikey_defconfig
make -j 4

cd ..
git clone https://android.googlesource.com/platform/prebuilts/gcc/linux-x86/aarch64/aarch64-linux-android-4.9/

PATH=/source depuis la racine/aarch64-linux-android-4.9/bin:$PATH

make -j 4


