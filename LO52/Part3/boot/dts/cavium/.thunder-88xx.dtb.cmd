cmd_arch/arm64/boot/dts/cavium/thunder-88xx.dtb := mkdir -p arch/arm64/boot/dts/cavium/ ; /home/cardoso/Bureau/LO52/hikey/aarch64-linux-android-4.9/bin/aarch64-linux-android-gcc -E -Wp,-MD,arch/arm64/boot/dts/cavium/.thunder-88xx.dtb.d.pre.tmp -nostdinc -I./arch/arm64/boot/dts -I./arch/arm64/boot/dts/include -I./drivers/of/testcase-data -I./include -undef -D__DTS__ -x assembler-with-cpp -o arch/arm64/boot/dts/cavium/.thunder-88xx.dtb.dts.tmp arch/arm64/boot/dts/cavium/thunder-88xx.dts ; ./scripts/dtc/dtc -O dtb -o arch/arm64/boot/dts/cavium/thunder-88xx.dtb -b 0 -i arch/arm64/boot/dts/cavium/  -d arch/arm64/boot/dts/cavium/.thunder-88xx.dtb.d.dtc.tmp arch/arm64/boot/dts/cavium/.thunder-88xx.dtb.dts.tmp ; cat arch/arm64/boot/dts/cavium/.thunder-88xx.dtb.d.pre.tmp arch/arm64/boot/dts/cavium/.thunder-88xx.dtb.d.dtc.tmp > arch/arm64/boot/dts/cavium/.thunder-88xx.dtb.d

source_arch/arm64/boot/dts/cavium/thunder-88xx.dtb := arch/arm64/boot/dts/cavium/thunder-88xx.dts

deps_arch/arm64/boot/dts/cavium/thunder-88xx.dtb := \
  arch/arm64/boot/dts/cavium/thunder-88xx.dtsi \

arch/arm64/boot/dts/cavium/thunder-88xx.dtb: $(deps_arch/arm64/boot/dts/cavium/thunder-88xx.dtb)

$(deps_arch/arm64/boot/dts/cavium/thunder-88xx.dtb):
