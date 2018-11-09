export ARCH=arm64
export CROSS_COMPILE=~/Bureau/LO52/hikey/aarch64-linux-android-4.9/bin/aarch64-linux-android-
make hikey-defconfig
make -j8