export ARCH=arm64
export CROSS_COMPILE=aarch64-linux-android-
cd hikey-linaro
git checkout -b android-hikey-linaro-4.1 origin/android-hikey-linaro-4.1
make hikey_defconfig
make -j 4
