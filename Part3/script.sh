git clone https://android.googlesource.com/kernel/hikey-linaro
cd hikey-linaro
git checkout -b android-hikey-linaro-4.1 origin/android-hikey-linaro-4.1
export ARCH=arm64
export CROSS_COMPILE=/vm/aarch64-linux-android-4.9/bin/aarch64-linux-android-
make hikey_defconfig
make -j4
