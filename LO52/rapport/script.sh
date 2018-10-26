git clone https://android.googlesource.com/kernel/hikey-linaro
git checkout -b android-hikey-linaro-4.1 origin/android-hikey-linaro-4.1
export ARCH=arm64
export CROSS_COMPILE=/usr/bin/aarch64-linux-gnu-
make hikey_defconfig
make -j8
