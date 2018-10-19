cmd_arch/arm64/boot/Image-dtb := (cat arch/arm64/boot/Image arch/arm64/boot/dts/hisilicon/hi6220-hikey.dtb > arch/arm64/boot/Image-dtb) || (rm -f arch/arm64/boot/Image-dtb; false)
